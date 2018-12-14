
package com.venky.tinet.model;

import org.springframework.http.HttpStatus;

/**
 * 所有的错误码全部定义在这里，使用方法参见 {@link ErrorResponse} 。<br>
 * 枚举本身做为错误码，基中Test_NotFound 形式会解析为Test.NotFound返回给前端，例如：ResourceNotFound(HttpStatus.NOT_FOUND, "木有了")  会返回<br>
 * <pre>
 * {
 * "requestId": "ba078ace-717c-4dea-af41-d4dd5035edd6",
 * "error": {
 * "message": "错误信息",
 * "code": "Test.NotFound"
 * }
 * }</pre>
 * 相关类： {@link ErrorResponse}, {@link ErrorCode}。
 * </pre>
 *
 * @Auther: 侯法超
 * @Date: 2018/8/23
 */
public enum ErrorCode {

    // ************************** 公共错误码 **************************

    AuthFailure(HttpStatus.UNAUTHORIZED, "未授权的"),
    InternalError(HttpStatus.INTERNAL_SERVER_ERROR, "服务内部错误，请稍后再试，或联系技术支持"),
    MissingRequestBody(HttpStatus.BAD_REQUEST, "缺少请求体"),
    UnsupportedMediaType(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "不支持的HTTP媒体类型"),
    HttpRequestMethodNotSupported(HttpStatus.METHOD_NOT_ALLOWED, "请求方法不支持"),
    MissingParameter(HttpStatus.BAD_REQUEST, "缺少参数"),
    MissingRequestHeader(HttpStatus.BAD_REQUEST, "缺少请求header"),
    ResourceNotFound(HttpStatus.NOT_FOUND, "指定的资源不存在"),
    MissingPathVariable(HttpStatus.BAD_REQUEST, "缺少路径变量"),
    InvalidParameter(HttpStatus.BAD_REQUEST, "请求参数不合法"),
    InvalidAction(HttpStatus.BAD_REQUEST, "无效的 Action");

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

    /**
     * @param status  HTTP状态码
     * @param message 错误信息
     */
    ErrorCode(HttpStatus status, String message) {
        this.httpStatus = status;
        this.message = message;
    }

    // 错误码
    private String code;
    // HTTP 状态码
    private HttpStatus httpStatus;
    // message 信息
    private String message;

    /**
     * @return
     */
    @Override
    public String toString() {
        return super.toString().replaceAll("_", ".");
    }
}
