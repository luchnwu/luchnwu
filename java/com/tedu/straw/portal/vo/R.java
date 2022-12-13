package com.tedu.straw.portal.vo;

import com.tedu.straw.portal.service.ServiceException;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class R<T> implements Serializable {

    public static final int OK = 200;

    public static final int CREATED = 201;

    public static final int ACCEPTED = 202;

    public static final int NO_CONTENT = 204;

    public static final int INVALID_REQUEST = 400;

    public static final int UNAUTHORIZED = 401;

    public static final int FORBIDDEN = 403;

    public static final int NOT_FOUND = 404;

    public static final int GONE = 410;

    public static final int UNPROCESSABLE_ENTITY = 422;

    public static final int INTERNAL_SERVER_ERROR = 500;

    private int code;
    private String message;
    private T data;

    public static R ok(String message) {
        return new R().setCode(OK).setMessage(message);
    }

    public static R ok(Object data) {
        return new R().setCode(OK).setMessage("OK").setData(data);
    }

    public static R created(String message) {
        return new R().setCode(CREATED).setMessage(message);
    }

    public static R accepted(String message) {
        return new R().setCode(ACCEPTED).setMessage(message);
    }

    public static R noContent(String message) {
        return new R().setCode(NO_CONTENT).setMessage(message);
    }

    public static R invalidRequest(String message) {
        return new R().setCode(INVALID_REQUEST).setMessage(message);
    }

    public static R unauthorized(String message) {
        return new R().setCode(UNAUTHORIZED).setMessage(message);
    }

    public static R forbidden() {
        return new R().setCode(FORBIDDEN).setMessage("權限不足");
    }

    public static R notFound(String message) {
        return new R().setCode(NOT_FOUND).setMessage(message);
    }

    public static R gone(String message) {
        return new R().setCode(GONE).setMessage(message);
    }

    public static R unprocessableEntity(String message) {
        return new R().setCode(UNPROCESSABLE_ENTITY).setMessage(message);
    }

    public static R failed(Throwable e) {
        return new R().setCode(INTERNAL_SERVER_ERROR).setMessage(e.getMessage());
    }

    public static R failed(ServiceException e) {
        return new R().setCode(e.getCode()).setMessage(e.getMessage());
    }

}
