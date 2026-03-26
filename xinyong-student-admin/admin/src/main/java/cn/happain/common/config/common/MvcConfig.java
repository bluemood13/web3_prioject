package cn.happain.common.config.common;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.happain.common.consts.GlobalConst;
import cn.happain.common.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.nio.file.Paths;

/**
 * MVC 配置
 *
 * @author happain
 * @date 2023/2/7
 * @desc web相关配置
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Resource
    TokenInterceptor tokenInterceptor;

    /**
     * 添加拦截器  拦截器在控制器的前面一层 是基于动态代理实现的，过滤器是需要依赖servlet
     *
     * @param registry 注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*token拦截器*/
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(GlobalConst.PATHS);
        /*sa-token拦截器*/
        registry.addInterceptor(new SaInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(GlobalConst.PATHS);


    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 判断操作系统类型（通过系统属性 "os.name" 判断）
        String osName = System.getProperty("os.name").toLowerCase();
        String projectRoot = Paths.get("").toAbsolutePath().toString(); // 项目根目录（所有系统通用）
        String resourceLocation;

        if (osName.contains("windows")) {
            // Windows 系统：拼接路径并替换 \ 为 /（适配 Spring 资源路径格式）
            String windowsPath = projectRoot + "\\file\\";
            resourceLocation = "file:" + windowsPath.replace("\\", "/"); // 转为 file:D:/xxx/file/ 格式
        } else {
            // macOS/Linux 系统：直接用 / 拼接路径（天然兼容 Spring）
            String unixPath = projectRoot + "/file/";
            resourceLocation = "file:" + unixPath; // 转为 file:/xxx/file/ 格式
        }

        // 注册资源映射：访问 /static/** 映射到对应系统的 file 目录
        registry.addResourceHandler("/static/**")
                .addResourceLocations(resourceLocation)
                .setCachePeriod(0); // 开发环境禁用缓存（可选，生产环境可删除或改为 3600）

        // 调试用：打印当前系统和映射路径（部署时可注释）
        System.err.println("当前操作系统：" + osName);
        System.err.println("静态资源映射路径：" + resourceLocation);
    }

}
