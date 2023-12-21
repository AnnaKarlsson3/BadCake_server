package com.dkserver.danielServer.security;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * Possible KEY_SIZE values are 128, 192 and 256
 * Possible T_LEN values are 128, 120, 112, 104 and 96
 */

@Component
public class AES {
    private SecretKey key;
    private final int KEY_SIZE = 128;
    private final int T_LEN = 128;
    private Cipher encryptionCipher;

    @Value("${app.security.aesKey}")
    private String base64EncodedKey;

    @PostConstruct
    public void init() {
        byte[] decodedKey = Base64.getDecoder().decode(base64EncodedKey);
        key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }


    public String encrypt(String message) throws Exception {
        byte[] messageInBytes = message.getBytes();
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(messageInBytes);
        byte[] iv = cipher.getIV();
        return encode(iv) + ":" + encode(encryptedBytes); // Bifoga IV till krypterad data
    }

    public String decrypt(String encryptedMessage) throws Exception {
        String[] parts = encryptedMessage.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid encrypted message");
        }
        byte[] iv = decode(parts[0]); // Extrahera IV
        byte[] encryptedData = decode(parts[1]);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(T_LEN, iv);
        cipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] decryptedBytes = cipher.doFinal(encryptedData);
        return new String(decryptedBytes);
    }


    private String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    private byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }


}
