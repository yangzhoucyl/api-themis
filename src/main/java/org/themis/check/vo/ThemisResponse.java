package org.themis.check.vo;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author yangzhou
 */
public class ThemisResponse<E> {

    @JsonProperty("code")
    private int code;

    @JsonProperty("message")
    private String message;

    /**
     * 实际返回数据，用泛型自定义其返回结构.
     * <p>当返回错误或抛异常信息时，该属性为NULL,否则根据业务需要返回相应结构.
     */
    @JsonProperty("data")
    private E data;


    /**
     * 实际错误异常信息数据，用泛型自定义其返回结构.
     * <p>当返回错误或抛异常信息时，该属性为NULL,否则根据业务需要返回相应结构.
     */
    @JsonProperty("errorData")
    private Object errorData;

    /**
     * 异常时调用
     *
     * @param code
     * @param message
     * @return
     */
    public static <E> ThemisResponse FAIL(int code, String message) {
        return new ThemisResponse(code, message, null);
    }

    /**
     * 异常时调用
     *
     * @param data
     * @return
     */
    public static <E> ThemisResponse FAIL(String message, E data) {
        return new ThemisResponse(message, null, data);
    }

    /**
     * 异常时调用
     *
     * @param data
     * @return
     */
    public static <E> ThemisResponse FAIL(int code, String message, E data) {
        return new ThemisResponse(code, message, null, data);
    }
    /**
     * 异常时调用
     *
     * @param data
     * @return
     */
    public static <E> ThemisResponse success(int code, String message, E data) {
        return new ThemisResponse(code, message, data);
    }

    /**
     * 异常时调用
     *
     * @param data
     * @return
     */
    public static <E> ThemisResponse success(E data) {
        return new ThemisResponse(200, "请求成功", data);
    }

    /**
     * 构建
     *
     * @param code
     * @param message
     * @param <E>
     * @return
     */
    public static <E> ThemisResponse build(int code, String message) {
        return new ThemisResponse(code, message);
    }


    /**
     * 构建
     *
     * @param code
     * @param message
     * @param data
     * @param errorData
     * @param <E>
     * @return
     */
    public static <E> ThemisResponse build(int code, String message, E data, Object errorData) {
        return new ThemisResponse(code, message, data, errorData);
    }

    public ThemisResponse() {
    }

    public ThemisResponse(int code, String message) {
        this.code = code;
        this.message = message;
        this.setData(null);
        this.setErrorData(null);
    }

    public ThemisResponse(int code, String message, E data) {
        this.code = code;
        this.message = message;
        this.setData(data);
    }

    public ThemisResponse(String message, E data, Object errorData) {
        this.message = message;
        this.setData(data);
        this.setErrorData(errorData);
    }

    public ThemisResponse(int code, String message, E data, Object errorData) {
        this.code = code;
        this.message = message;
        this.setData(data);
        this.setErrorData(errorData);
    }

    /**
     * 获取信息编码.
     *
     * @return int
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code 信息编码
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return String
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message 信息详情
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return E 泛型实体
     */
    public E getData() {
        return data;
    }

    /**
     * @param data 数据结构
     */
    public void setData(E data) {
        this.data = data == null ? (E) new JSONObject() : data;
    }


    public Object getErrorData() {
        return errorData;
    }

    public void setErrorData(Object errorData) {
        this.errorData = errorData;
    }

}
