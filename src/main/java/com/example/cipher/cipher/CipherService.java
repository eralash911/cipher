package com.example.cipher.cipher;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

import static org.springframework.cache.interceptor.SimpleKeyGenerator.generateKey;

public class CipherService {
    private static final SecretKey KEY =  generateKey(256);
    private static final AlgorithmParameterSpec IV = generateIv();
    private static final String ALGORITHM = "ChaCha20-Poly1305";

    public static String encrypt(String plainText){
        try {
            return encrypt(ALGORITHM, plainText, KEY, IV); //cipherText;
        }catch (Exception e){
            throw new RuntimeException();
        }
    }
    public static String decrypt(String cipherText){
 try {
     return decrypt(ALGORITHM, cipherText, KEY, IV);//plainText;
 }catch (Exception e)
 {
     throw new RuntimeException();
 }
    }

    public static String encrypt(String algorithm,
                                 String plainText,
                                 SecretKey secretKey,
                                 AlgorithmParameterSpec iv) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        byte[]cipherText = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(cipherText);

    }

    public static String decrypt(String algorithm,
                                 String cipherText,
                                 SecretKey secretKey,
                                 AlgorithmParameterSpec iv) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(algorithm); //выбор алгоритма и достаем класс провайдер, который знает как работать с алгоритмом
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv); // инициализируем класс
        var decodedCypherText = Base64.getDecoder().decode(cipherText.getBytes(StandardCharsets.UTF_8));
        byte[]plainText = cipher.doFinal(decodedCypherText);
        return new String(plainText);

    }

    private static AlgorithmParameterSpec generateIv(){
        byte[]iv = new byte[12];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);//обертка инит вектора для алгоритмов шифрования
    }

    private static SecretKey generateKey(int n){  //size
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance("ChaCha20"); //algoritm
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        keyGenerator.init(n); //allocation memory
        return keyGenerator.generateKey();
    }
}
