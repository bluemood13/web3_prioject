package cn.happain.common.config.ganache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;


@Configuration
@Slf4j
public class GanacheConfig {


    @Value("${ganache.host}")
    String ganacheHost;


    // TODO: 根据配置文件中的属性值决定是否注册。
    @Bean
    @ConditionalOnProperty(name = "qkl.value", havingValue = "ganache")
    public Web3j web3j() {
        return Web3j.build(new HttpService(ganacheHost));
    }

}
