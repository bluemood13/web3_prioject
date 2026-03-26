package cn.happain.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author happain
 * @创建时间 2023/1/25
 * @描述 请求响应封装
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Response<T> {
    private Integer code;
    private String message;
    private T data;


    /**
     * 请求是否成功
     *
     * @return boolean
     */
    public boolean isSuccess() {
        return code == 200;
    }



    /**默认成功请求*/
    public static <T> Response<T> successDefault() {
        return new Response<T>().setCode(200).setMessage("操作成功");
    }

    /**自定义返回消息*/
    public static <T> Response<T> successMessage(String message) {
        return new Response<T>().setCode(200).setMessage(message);
    }

    /**自定义数据与消息*/
    public static <T> Response<T> successDataMessage(T data,String message) {
        return  new Response<T>().setCode(200).setMessage(message).setData(data);
    }
    /** 自定义数据*/
    public static <T> Response<T> successDataDefault(T data) {
        return  new Response<T>().setCode(200).setMessage("操作成功").setData(data);
    }
    /**
     * 身份验证失败
     *
     * @return {@link Response}<{@link T}>
     */
    public static <T> Response<T> failAuth() {
        return  new Response<T>().setCode(401).setMessage("认证错误，请联系管理员");
    }
    /** 自定义权限异常错误处理*/
    public static <T> Response<T> failPermission(String message) {
        return  new Response<T>().setCode(403).setMessage(message+"，请联系管理员");
    }
    /** 自定义权限异常错误处理*/
    public static <T> Response<T> failRole(String message) {
        return  new Response<T>().setCode(403).setMessage(message+"，请联系管理员");
    }

    /**
     * 未找到数据
     */
    public static <T> Response<T> failNoData() {
        return new Response<T>().setCode(404).setMessage("未找到数据");
    }

    /**
     * 已存在数据
     */
    public static <T> Response<T> failExitData() {
        return new Response<T>().setCode(404).setMessage("已存在数据");
    }

    /**
     * 存在关联数据无法删除
     */
    public static <T> Response<T> failExitAssoData() {
        return new Response<T>().setCode(404).setMessage("存在关联数据");
    }

    /**
     * 默认错误
     */
    public static <T> Response<T> failDefault() {
        return new Response<T>().setCode(404).setMessage("内部错误，请联系管理员");
    }


    /**
     * 自定义消息错误
     */
    public static <T> Response<T> failDefault(String message) {
        return new Response<T>().setCode(404).setMessage(message + ",请联系管理员处理！");
    }


}
