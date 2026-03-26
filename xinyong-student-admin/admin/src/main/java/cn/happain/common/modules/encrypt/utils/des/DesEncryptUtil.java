package cn.happain.common.modules.encrypt.utils.des;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.crypto.symmetric.DES;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * DES 对称加密（支持自定义任意长度密钥，适配所有Hutool版本）
 *
 * @author happain
 * @date 2025/11/06
 */
public class DesEncryptUtil {
    // 默认密钥（兼容原有逻辑，无需固定8字节）
    private static final String DEFAULT_KEY = "desKey88";

    // ==================== 兼容原有无参方法（使用默认密钥） ====================
    /**
     * 加密（使用默认密钥，兼容原有逻辑）
     */
    public static String encryptData(String data) {
        return encryptData(data, DEFAULT_KEY);
    }

    /**
     * 解密（使用默认密钥，兼容原有逻辑）
     */
    public static String decryptData(String encryptedData) {
        return decryptData(encryptedData, DEFAULT_KEY);
    }

    // ==================== 自定义任意长度密钥（核心方法） ====================
    /**
     * 加密（自定义任意长度密钥）
     * @param data 待加密字符串
     * @param customKey 自定义密钥（任意长度，无需固定8字节）
     * @return Base64编码的加密结果
     */
    public static String encryptData(String data, String customKey) {
        if (data == null || data.isEmpty()) {
            return "";
        }
        // 生成符合DES规范的8字节密钥
        byte[] desKey = generateDesKey(customKey);
        DES des = SecureUtil.des(desKey);
        // 明确指定UTF-8字符集，避免乱码
        byte[] encryptedBytes = des.encrypt(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * 解密（自定义任意长度密钥）
     * @param encryptedData Base64编码的加密字符串
     * @param customKey 自定义密钥（与加密时一致，任意长度）
     * @return 解密后的原始字符串
     */
    public static String decryptData(String encryptedData, String customKey) {
        if (encryptedData == null || encryptedData.isEmpty()) {
            return "";
        }
        try {
            // 生成和加密时一致的8字节DES密钥
            byte[] desKey = generateDesKey(customKey);
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
            DES des = SecureUtil.des(desKey);
            byte[] decryptedBytes = des.decrypt(encryptedBytes);
            // 明确指定UTF-8字符集解码
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("DES解密失败，请检查密钥或加密字符串是否正确", e);
        }
    }

    // ==================== 核心：任意密钥 → DES合规8字节密钥 ====================
    /**
     * 将任意长度的自定义密钥转换为DES要求的8字节密钥
     * 原理：通过MD5哈希生成16字节摘要，截取前8字节作为DES密钥（MD5更适配8字节长度）
     */
    private static byte[] generateDesKey(String customKey) {
        if (customKey == null || customKey.isBlank()) {
            throw new IllegalArgumentException("自定义密钥不能为空！");
        }

        // 1. 创建MD5摘要器（Hutool全版本兼容，生成16字节固定摘要）
        Digester md5Digester = new Digester(DigestAlgorithm.MD5);
        // 2. 对自定义密钥做MD5哈希，生成16字节固定长度的摘要
        byte[] hashBytes = md5Digester.digest(customKey.getBytes(StandardCharsets.UTF_8));
        // 3. 截取前8字节作为DES密钥（DES强制要求8字节）
        byte[] desKey = new byte[8];
        System.arraycopy(hashBytes, 0, desKey, 0, 8);

        return desKey;
    }

    // ==================== 测试用例（可直接运行验证） ====================
    public static void main(String[] args) {
        // 测试1：默认密钥加密解密（兼容原有逻辑）
        String testData = "测试DES加密123456";
        String encrypt1 = encryptData(testData);
        String decrypt1 = decryptData(encrypt1);
        System.out.println("默认密钥加密结果：" + encrypt1);
        System.out.println("默认密钥解密结果：" + decrypt1);

        // 测试2：自定义短密钥（3位）
        String customShortKey = "123";
        String encrypt2 = encryptData(testData, customShortKey);
        String decrypt2 = decryptData(encrypt2, customShortKey);
        System.out.println("\n自定义短密钥加密：" + encrypt2);
        System.out.println("自定义短密钥解密：" + decrypt2);

        // 测试3：自定义长密钥（20位）
        String customLongKey = "desCustomKey1234567890abc";
        String encrypt3 = encryptData(testData, customLongKey);
        String decrypt3 = decryptData(encrypt3, customLongKey);
        System.out.println("\n自定义长密钥加密：" + encrypt3);
        System.out.println("自定义长密钥解密：" + decrypt3);
    }
}