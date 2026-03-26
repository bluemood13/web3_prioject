package cn.happain.common.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.happain.common.domain.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author happain
 * @创建人 happain
 * @创建时间 2023/1/25
 * @描述 统一异常处理类
 */
@RestControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {

    /**字段验证异常*/
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class })
    public Response validExceptionHandler(HttpServletRequest req, BindException e)  {
        e.printStackTrace();
        StringBuffer stringBuffer = new StringBuffer();
        List<FieldError> fieldErrors = e.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            stringBuffer.append(fieldError.getDefaultMessage()+"\n");
        }
        return Response.failDefault(stringBuffer.toString());
    }

    /**
     * 外键关联数据异常
     *
     * @param req 要求
     * @param e   e
     * @return {@link Response }
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Response dataIntegrityViolationExceptionHandler(HttpServletRequest req, DataIntegrityViolationException e) {
        log.error("DataIntegrityViolationException:{}", e.getMessage());
        return Response.failDefault("存在关联数据，无法删除");
    }
    /**
     * 认证错误
     *
     * @param e e
     * @return {@link Response}
     */
    @ExceptionHandler(NotLoginException.class)
    public Response noLoginException(NotLoginException e) {
        e.printStackTrace();
        log.error("NotLoginException:{}",e.getMessage());
        return Response.failAuth();
    }


    /**
     * 无权限异常
     *
     * @param e e
     * @return {@link Response}
     */
    @ExceptionHandler(NotPermissionException.class)
    public Response noPermissionException(NotPermissionException e) {
        e.printStackTrace();
        log.error("缺少权限:{}",e.getMessage());
        return Response.failPermission(e.getMessage());
    }


    /**
     * 无角色异常
     *
     * @param e e
     * @return {@link Response}
     */
    @ExceptionHandler(NotRoleException.class)
    public Response noRoleException(NotRoleException e) {
        e.printStackTrace();
        log.error("缺少角色:{}",e.getMessage());
        return Response.failRole(e.getMessage());
    }
    /** 默认异常*/
    @ExceptionHandler(value = {Exception.class})
    public Response exceptionHandler(HttpServletRequest req, Exception e) {
        e.printStackTrace();
        log.error("Exception:{}", e.getMessage());
        return Response.failDefault(e.getMessage());
    }
}
