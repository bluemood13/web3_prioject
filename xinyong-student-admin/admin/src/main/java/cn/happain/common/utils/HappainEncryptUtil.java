package cn.happain.common.utils;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.DES;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author happain
 * @desc 全局工具类
 * @since 2023/2/18
 */

@Slf4j
public class HappainEncryptUtil {


    /**
     * AES加密
     *
     * @param content 内容
     * @param key     密钥
     * @return {@link String }
     */
    public static String aesEncode(String content, String key) {
        if (content == null || content.isEmpty()) {
            return "";
        }
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException("key不能为空");
        }

        byte[] aesKey = generateAesKey(key);
        AES aes = SecureUtil.aes(aesKey);
        byte[] encryptBytes = aes.encrypt(content.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptBytes);
    }


    /**
     * AES解码
     *
     * @param encryptedContent 加密内容
     * @param key              说明
     * @return {@link String }
     */
    public static String aesDecode(String encryptedContent, String key) {
        if (encryptedContent == null || encryptedContent.isEmpty()) {
            return "";
        }
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException("key不能为空");
        }

        try {
            byte[] aesKey = generateAesKey(key);
            byte[] encryptBytes = Base64.getDecoder().decode(encryptedContent);
            AES aes = SecureUtil.aes(aesKey);
            byte[] decryptBytes = aes.decrypt(encryptBytes);
            return new String(decryptBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("AES解密失败：密钥错误或加密串无效", e);
        }
    }





    /**
     * DES加密
     *
     * @param content 内容
     * @param key     密钥
     * @return {@link String }
     */
    public static String desEncode(String content, String key) {
        if (content == null || content.isEmpty()) {
            return "";
        }
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException("key不能为空");
        }

        byte[] desKey = generateDesKey(key);
        DES des = SecureUtil.des(desKey);
        byte[] encryptBytes = des.encrypt(content.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptBytes);
    }

    /**
     * DES解码
     *
     * @param encryptedContent 加密内容
     * @param key              密钥
     * @return {@link String }
     */
    public static String desDecode(String encryptedContent, String key) {
        if (encryptedContent == null || encryptedContent.isEmpty()) {
            return "";
        }
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException("key不能为空");
        }

        try {
            byte[] desKey = generateDesKey(key);
            byte[] encryptBytes = Base64.getDecoder().decode(encryptedContent);
            DES des = SecureUtil.des(desKey);
            byte[] decryptBytes = des.decrypt(encryptBytes);
            return new String(decryptBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("DES解密失败：密钥错误或加密串无效", e);
        }
    }


    /**
     * MD5加密
     *
     * @param content 内容
     * @return {@link String }
     */
    public static String md5Encode(String content) {
        if (content == null || content.isEmpty()) {
            return "";
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(content.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(digest.length * 2);
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5加密失败", e);
        }
    }

    /**
     * MD5验证
     *
     * @param content  原文
     * @param md5Value MD5值
     * @return {@link Boolean }
     */
    public static boolean md5Verify(String content, String md5Value) {
        if (md5Value == null || md5Value.isBlank()) {
            return false;
        }
        String encoded = md5Encode(content);
        return md5Value.equalsIgnoreCase(encoded);
    }


    /**
     * SHA-256加密
     *
     * @param content 内容
     * @return {@link String }
     */
    public static String sha256Encode(String content) {
        if (content == null || content.isEmpty()) {
            return "";
        }
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] digest = sha256.digest(content.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(digest.length * 2);
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256加密失败", e);
        }
    }

    /**
     * SHA-256验证
     *
     * @param content  原文
     * @param shaValue SHA-256值
     * @return {@link Boolean }
     */
    public static boolean sha256Verify(String content, String shaValue) {
        if (shaValue == null || shaValue.isBlank()) {
            return false;
        }
        String encoded = sha256Encode(content);
        return shaValue.equalsIgnoreCase(encoded);
    }


    private static byte[] generateAesKey(String customKey) {
        Digester sha256Digester = new Digester(DigestAlgorithm.SHA256);
        byte[] hashBytes = sha256Digester.digest(customKey.getBytes(StandardCharsets.UTF_8));
        byte[] aesKey = new byte[16];
        System.arraycopy(hashBytes, 0, aesKey, 0, aesKey.length);
        return aesKey;
    }

    private static byte[] generateDesKey(String customKey) {
        Digester sha256Digester = new Digester(DigestAlgorithm.SHA256);
        byte[] hashBytes = sha256Digester.digest(customKey.getBytes(StandardCharsets.UTF_8));
        byte[] desKey = new byte[8];
        System.arraycopy(hashBytes, 0, desKey, 0, desKey.length);
        return desKey;
    }


}
