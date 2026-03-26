package cn.happain.common.config.fisco.config;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.config.ConfigOption;
import org.fisco.bcos.sdk.config.model.ConfigProperty;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.utils.Hex;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class FiscoConfig {


    @Resource
    FiscoRemoteConfig fiscoRemoteConfig;
    @Resource
    FiscoLocalConfig fiscoLocalConfig;



    // TODO: 加载fisco本地的bean
    @Bean
    @ConditionalOnProperty(name = "qkl.value", havingValue = "fisco-local")
    public Client fiscoLocalClient() {
        String certPaths = fiscoLocalConfig.getCertPath();
        try{
            ConfigProperty property = new ConfigProperty();
            configNetwork(fiscoLocalConfig,property);
            configCryptoMaterial(property,certPaths);
            ConfigOption configOption = new ConfigOption(property);
            Client client = new BcosSDK(configOption).getClient(fiscoLocalConfig.getGroupId());
            // TODO: 设置账号认证
            loadKeyPairFromP12(client,certPaths+"/acount.p12","");
            return client;
        }
        catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }


    // TODO: 加载fisco远程的bean
    @Bean
    @ConditionalOnProperty(name = "qkl.value", havingValue = "fisco-remote")
    public Client fiscoRemoteClient() throws Exception {
        String certPaths = fiscoRemoteConfig.getCertPath();
        try{
            ConfigProperty property = new ConfigProperty();
            configNetwork(fiscoRemoteConfig,property);
            configCryptoMaterial(property,certPaths);
            ConfigOption configOption = new ConfigOption(property);
            Client client = new BcosSDK(configOption).getClient(fiscoRemoteConfig.getGroupId());
            // TODO: 设置账号认证
            loadKeyPairFromP12(client,certPaths+"/acount.p12","");
            return client;
        }
        catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }



    public void configNetwork(FiscoBaseConfig fiscoBaseConfig,ConfigProperty configProperty) {
        String peerStr = fiscoBaseConfig.getPeers();
        List<String> peers = Arrays.stream(peerStr.split(",")).collect(Collectors.toList());
        Map<String, Object> networkConfig = new HashMap<>();
        networkConfig.put("peers", peers);

        configProperty.setNetwork(networkConfig);
    }

    public void configCryptoMaterial(ConfigProperty configProperty,String certPath) {
        Map<String, Object> cryptoMaterials = new HashMap<>();
        cryptoMaterials.put("certPath", certPath);
        configProperty.setCryptoMaterial(cryptoMaterials);
    }

    public void loadKeyPairFromP12(Client client, String p12Path, String password) throws Exception {
        // 加载p12文件
        KeyStore ks = KeyStore.getInstance("PKCS12");
        try (InputStream fis = getClass().getClassLoader().getResourceAsStream(p12Path)) {
            ks.load(fis, password.toCharArray());
        }
        // 获取私钥别名并提取私钥
        String alias = ks.aliases().nextElement();
        PrivateKey privateKey = (PrivateKey) ks.getKey(alias, password.toCharArray());

        // 解析私钥，提取32字节纯私钥
        byte[] encodedKey = privateKey.getEncoded();
        PrivateKeyInfo pkInfo = PrivateKeyInfo.getInstance(encodedKey);
        byte[] rawPrivateKey = pkInfo.parsePrivateKey().toASN1Primitive().getEncoded();

        // 截取32字节（64字符）的私钥（针对secp256k1曲线）
        if (rawPrivateKey.length > 32) {
            // 跳过ASN.1头信息，取最后32字节
            byte[] purePrivateKey = new byte[32];
            System.arraycopy(rawPrivateKey, rawPrivateKey.length - 32, purePrivateKey, 0, 32);
            rawPrivateKey = purePrivateKey;
        }
        // 转换为16进制字符串
        String hexPrivateKey = new String(Hex.encode(rawPrivateKey));
        System.err.println("提取的纯私钥（64字符）：" + hexPrivateKey);
        // 创建密钥对并设置
        CryptoKeyPair keyPair = client.getCryptoSuite().createKeyPair(hexPrivateKey);
        client.getCryptoSuite().setCryptoKeyPair(keyPair);
    }
}
