package cn.happain.common.consts;

import cn.hutool.core.collection.ListUtil;

import java.util.List;

/**
 * 全局 const
 *
 * @author
 * @date 2024/09/26
 */
public class GlobalConst {

    /**
     * 每单位 gas 的价格为 1 以太坊币,这意味着每单位 gas 的价格是 20000000000 wei。
     */
    public static final Long GAS_PRICE = 20000000000L;

    /**
     * 管理员用户名
     */
    public static final String ADMIN_USERNAME = "admin";
    /**
     * 角色白名单
     */
    public static final List<String> ROLE_WHITE = ListUtil.of("admin","role","default");


    /**
     * 这意味着这笔交易或者智能合约执行时，最多可以消耗 1000000 单位的 gas。
     */
    public static final Long GAS_LIMIT = 6721975L;

    /**
     * 拦截器的过滤路径
     */
    public static final String[] PATHS = {
            "/checkToken",
            "/login",
            "/register",
            "/static/**",
            "/sys/role/**"
    };
    /**
     * TOKEN相关
     *
     * @author happain
     * @date 2023/02/18
     */
    public static class Token {
        /**
         * 在请求头中的token标识
         */
        public final static String HEADER_TOKEN = "Authorization";

    }


    public static class Qkl {
        /**
         * ganache自转账模式
         */
        public static final String GANACHE = "ganache";
        /**
         * ganache合约模式
         */
        public static final String GANACHE_CONTRACT = "ganache_contract";
        /**
         * FISCO 本地模式
         */
        public static final String FISCO_LOCAL = "fisco-local";
        /**
         * FISCO 远程模式
         */
        public static final String FISCO_REMOTE = "fisco-remote";
    }


}

