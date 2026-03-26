package cn.happain.common.interceptor;


import cn.dev33.satoken.stp.StpUtil;
import cn.happain.common.consts.GlobalConst;
import cn.happain.common.domain.Response;
import cn.happain.common.utils.HappainGlobalUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;



/**
 * 令牌拦截器
 *
 * @author admin
 * @date 2023/05/26
 */
@Component
public class TokenInterceptor  implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(GlobalConst.Token.HEADER_TOKEN);
        Object loginId = StpUtil.getLoginIdByToken(token);
        if(StrUtil.isEmpty(token) || loginId==null) {
            HappainGlobalUtil.interceptorResponse(response, JSONUtil.parseObj(Response.failAuth()).toStringPretty());
            return false;
        }
        // TODO: 2023/5/26 切换登录
        StpUtil.login(loginId);
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
