package com.venky.tinet.model;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * 成功时返回的
 * @Auther: 侯法超
 * @Date: 2018/8/27
 */
public class ApiResponseEntity extends ResponseEntity<Map> {

    private final Map<String, Object> result = new HashMap<>();

    protected ApiResponseEntity(@NotNull Map body, @Nullable MultiValueMap<String, String> headers, HttpStatus status) {
        super(body, headers, status);
    }

    public static Builder error(ErrorCode errorCode) {
        return build(errorCode.getHttpStatus()).
                addResult("error", new ErrorResponse(errorCode, errorCode.getMessage()));
    }

    public static Builder error(ErrorCode errorCode, String message) {
        return build(errorCode.getHttpStatus()).
                addResult("error", new ErrorResponse(errorCode, message));
    }

    public static Builder error(ErrorCode errorCode, Object value) {
        return build(errorCode.getHttpStatus()).
                addResult("error", value);
    }

    public static Builder success() {
        return build(HttpStatus.OK);
    }

    public static Builder ok(String key, Object value) {
        return build(HttpStatus.OK).addResult(key, value);
    }

    public static Builder build(HttpStatus status) {
        Assert.notNull(status, "HttpStatus must not be null");
        return new Builder(status);
    }

    public static Builder page(String name, ApiPageData page) {
        return build(HttpStatus.OK)
                .addResult(name, page.getData())
                .addResult("pageSize", page.getPageSize())
                .addResult("totalCount", page.getTotalCount())
                .addResult("pageNumber", page.getPageNumber());
    }

    public static class Builder {

        private ErrorCode errorCode;

        private HttpStatus status;

        private final HttpHeaders headers = new HttpHeaders();

        private final Map<String, Object> map = new HashMap<>();

        public Builder(HttpStatus status) {
            this.status = status;
        }

        public ApiResponseEntity body() {
            return new ApiResponseEntity(this.map, this.headers, this.status);
        }

        public Builder addResult(String key, Object value) {
            this.map.put(key, value);
            return this;
        }

        public ApiResponseEntity build() {
            return body();
        }

        public ResponseEntity toResponseEntity() {
            return build();
        }

    }

}
