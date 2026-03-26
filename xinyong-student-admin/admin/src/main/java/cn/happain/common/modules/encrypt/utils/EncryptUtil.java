package cn.happain.common.modules.encrypt.utils;


import cn.happain.common.modules.encrypt.utils.aes.AesEncryptUtil;
import cn.happain.common.modules.encrypt.utils.des.DesEncryptUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EncryptUtil {

    public static final String AES = "aes";
    public static final String DES = "des";

    /**
     * 加密数据
     *
     * @param data 数据
     * @param key  加密的方式
     * @return {@link String }
     */
    public static String encryptData(String data,String key) {
        System.err.println("==============");
        System.err.println("加密前数据："+data);
        if (AES.equals(key)) {
            System.err.println("正在执行AES加密...");
            data = AesEncryptUtil.encryptData(data);
        }
        else if (DES.equals(key)) {
            System.err.println("正在执行DES加密...");
            data = DesEncryptUtil.encryptData(data);
        }
        System.err.println("加密后数据："+data);
        System.err.println("==============");
        return data;
    }


    /**
     * 解密数据
     *
     * @param data 数据
     * @param key  钥匙
     * @return {@link String }
     */
    public static String decryptData(String data,String key) {
        System.err.println("==============");
        System.err.println("解密前数据："+data);
        if (AES.equals(key)) {
            System.err.println("正在执行AES解密...");
            data = AesEncryptUtil.decryptData(data);
        }
        else if (DES.equals(key)) {
            System.err.println("正在执行DES解密...");
            data = DesEncryptUtil.decryptData(data);
        }
        System.err.println("解密后数据："+data);
        System.err.println("==============");
        return data;
    }
}
