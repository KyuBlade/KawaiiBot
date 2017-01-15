package com.omega.dofus.bot.logic.connection.managers;

import com.omega.dofus.bot.BuildInfos;
import com.omega.dofus.bot.config.bot.BotConfig;
import com.omega.dofus.bot.config.global.GlobalConfig;
import com.omega.dofus.bot.network.io.CustomIoBuffer;
import com.omega.dofus.bot.network.messages.connection.IdentificationMessage;
import com.omega.dofus.bot.network.types.secure.TrustCertificate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URL;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class AuthentificationManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthentificationManager.class);

    private static final int AES_KEY_LENGTH = 32;

    private final BotConfig config;

    private byte[] publicKey;
    private String salt;
    private TrustCertificate certificate;
    private SecretKey AESKey;
    private String gameServerTicket;
    private String ankamaPortalKey;
    private String nextToken;
    private boolean tokenMode;

    public AuthentificationManager(BotConfig config) {
        this.config = config;
    }

    public void initAESKey() throws NoSuchAlgorithmException {
        this.AESKey = generateRandomAESKey();
    }

    private SecretKey generateRandomAESKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(AES_KEY_LENGTH * 8);

        return keyGenerator.generateKey();
    }

    public void setGameServerTicket(byte[] ticket) throws Exception {
        this.gameServerTicket = new String(decodeWithAES(ticket));
    }

    private byte[] decodeWithAES(byte[] data) throws Exception {
        try {
            Field field = Class.forName("javax.crypto.JceSecurity").
                    getDeclaredField("isRestricted");
            field.setAccessible(true);
            field.set(null, java.lang.Boolean.FALSE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        byte[] iv = Arrays.copyOf(AESKey.getEncoded(), 16);
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, AESKey, new IvParameterSpec(iv));

        return cipher.doFinal(data);
    }

    private String cipherMd5String(String data)
            throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException,
            BadPaddingException, IllegalBlockSizeException {
        MessageDigest cipher = MessageDigest.getInstance("MD5");

        data += this.salt;
        byte[] encodedBytes = cipher.digest(data.getBytes("UTF-8"));

        return new String(encodedBytes, "UTF-8");
    }

    private byte[] cypherRSA(String username, String password, TrustCertificate certificate)
            throws Exception {
        CustomIoBuffer in = CustomIoBuffer.allocate(128);
        in.writeUTFBytes(this.salt);
        in.writeBytes(this.AESKey.getEncoded());

        if (certificate != null) {
            in.writeUnsignedInt(certificate.getId());
            in.writeUTFBytes(certificate.getHash());
        }

        in.writeByte(username.length());
        in.writeUTFBytes(username);
        in.writeUTFBytes(password);
        in.flip();

        byte[] inArray = in.readAllBytes();
        in.free();

        KeyFactory kf = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec x509 = new X509EncodedKeySpec(this.publicKey);
        PublicKey publicKey = kf.generatePublic(x509);

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(inArray);
    }

    public void setPublicKey(byte[] signedKey) throws Exception {
        URL keyUrl = getClass().getResource("/AuthentificationManager__verifyKey.bin");
        if (keyUrl == null) {
            throw new NullPointerException(
                    "Unable to find AuthentificationManager__verifyKey.bin resource");
        }

        try (FileInputStream in = new FileInputStream(new File(keyUrl.toURI()))) {
            // Transform stream to string
            byte[] keyBytes = new byte[in.available()];
            in.read(keyBytes);
            String publicKeyStr = new String(keyBytes, "UTF-8");

            // Remove header and footer
            publicKeyStr = publicKeyStr
                    .replaceAll("(-+BEGIN PUBLIC KEY-+\\r?\\n|-+END PUBLIC KEY-+\\r?\\n?|\\n)",
                                "");

            // decode base64
            keyBytes = Base64.getDecoder().decode(publicKeyStr);

            // Get the public key
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey clientKey = keyFactory.generatePublic(spec);

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, clientKey);
            this.publicKey = cipher.doFinal(signedKey);
        } catch (Exception e) {
            throw new Error("Unable to get the public key", e);
        }
    }

    public void setSalt(String salt) {
        this.salt = salt;
        if (salt.length() < 32) {
            LOGGER.warn("Authentification salt size is lower than 32 ");
            while (this.salt.length() < 32) {
                this.salt += " ";
            }
        }
    }

    public IdentificationMessage getIdentificationMessage() throws Exception {
        this.ankamaPortalKey = cipherMd5String(this.config.getPassword());
        IdentificationMessage message = new IdentificationMessage();
        message.setLang(GlobalConfig.getInstance().getLang());
        message.setAutoConnect(false);
        message.setUseCertificate(this.certificate != null);
        message.setUseLoginToken(false);

        byte[] credentials = cypherRSA(this.config.getUsername(), this.config.getPassword(),
                                       this.certificate);
        message.setCredentials(credentials);

        message.setVersion(BuildInfos.VERSION_EXTENDED);

        return message;
    }

    public String getGameServerTicket() {
        return gameServerTicket;
    }
}
