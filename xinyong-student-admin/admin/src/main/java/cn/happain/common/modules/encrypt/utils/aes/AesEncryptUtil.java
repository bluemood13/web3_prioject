package cn.happain.common.modules.encrypt.utils.aes;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.crypto.symmetric.AES;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * AES 对称加密（支持自定义任意长度密钥，适配所有Hutool版本）
 *
 * @author happain
 * @date 2025/11/06
 */
public class AesEncryptUtil {
    // 默认密钥（兼容原有逻辑）
    private static final String DEFAULT_KEY = "mySecretEncryptionKey123";

    // AES密钥长度枚举（仅选128/256位，避免192位需JCE扩展的问题）
    public enum AesKeyLength {
        KEY_128(16), // 128位（无需JCE扩展，通用）
        KEY_256(32); // 256位（JDK8u151+默认支持）

        private final int bytes;

        AesKeyLength(int bytes) {
            this.bytes = bytes;
        }

        public int getBytes() {
            return bytes;
        }
    }

    // ==================== 兼容原有无参方法 ====================
    public static String encryptData(String data) {
        return encryptData(data, DEFAULT_KEY);
    }

    public static String decryptData(String encryptedData) {
        return decryptData(encryptedData, DEFAULT_KEY);
    }

    // ==================== 自定义任意长度密钥（默认128位） ====================
    public static String encryptData(String data, String customKey) {
        return encryptData(data, customKey, AesKeyLength.KEY_128);
    }

    public static String decryptData(String encryptedData, String customKey) {
        return decryptData(encryptedData, customKey, AesKeyLength.KEY_128);
    }

    // ==================== 自定义密钥+指定AES长度（核心方法） ====================
    public static String encryptData(String data, String customKey, AesKeyLength keyLength) {
        if (data == null || data.isEmpty()) {
            return "";
        }
        // 生成符合AES规范的固定长度密钥
        byte[] aesKey = generateAesKey(customKey, keyLength);
        // 初始化AES并加密
        AES aes = SecureUtil.aes(aesKey);
        byte[] encryptBytes = aes.encrypt(data.getBytes(StandardCharsets.UTF_8));
        // Base64编码返回
        return Base64.getEncoder().encodeToString(encryptBytes);
    }

    public static String decryptData(String encryptedData, String customKey, AesKeyLength keyLength) {
        if (encryptedData == null || encryptedData.isEmpty()) {
            return "";
        }
        try {
            // 生成和加密时一致的AES密钥
            byte[] aesKey = generateAesKey(customKey, keyLength);
            // Base64解码后解密
            byte[] encryptBytes = Base64.getDecoder().decode(encryptedData);
            AES aes = SecureUtil.aes(aesKey);
            byte[] decryptBytes = aes.decrypt(encryptBytes);
            // UTF-8解码返回原始字符串
            return new String(decryptBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("AES解密失败：密钥错误或加密串无效", e);
        }
    }

    // ==================== 核心：任意密钥 → AES合规密钥（全版本兼容） ====================
    /**
     * 将任意长度的自定义密钥转换为AES要求的16/32字节密钥
     * 原理：通过SHA256哈希生成32字节固定摘要，再按长度截取，适配所有Hutool版本
     */
    private static byte[] generateAesKey(String customKey, AesKeyLength keyLength) {
        if (customKey == null || customKey.isBlank()) {
            throw new IllegalArgumentException("自定义密钥不能为空！");
        }

        // 1. 创建SHA256摘要器（Hutool全版本兼容，无API差异）
        Digester sha256Digester = new Digester(DigestAlgorithm.SHA256);
        // 2. 对自定义密钥做SHA256哈希，生成32字节固定长度的摘要
        byte[] hashBytes = sha256Digester.digest(customKey.getBytes(StandardCharsets.UTF_8));
        // 3. 按AES长度截取（128位取前16字节，256位取全部32字节）
        byte[] aesKey = new byte[keyLength.getBytes()];
        System.arraycopy(hashBytes, 0, aesKey, 0, keyLength.getBytes());

        return aesKey;
    }

    // ==================== 测试用例（可直接运行验证） ====================
    public static void main(String[] args) {
        // 测试1：默认密钥加密解密
        String testData = "测试数据123456";
        String customShortKey = "1234567844你输什么什么事描述s是啊大苏打大师大师大师大师的阿水大叔大婶打撒大叔大婶打算";
        String encrypt2 = encryptData(testData, customShortKey);
        String decrypt2 = decryptData(encrypt2, customShortKey);
        System.out.println("\n自定义短密钥加密：" + encrypt2);
        System.out.println("自定义短密钥解密：" + decrypt2);


    }
}