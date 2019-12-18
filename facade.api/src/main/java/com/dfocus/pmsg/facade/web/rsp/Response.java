package com.dfocus.pmsg.facade.web.rsp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.MDC;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Response<T> {

    private final String traceId = MDC.get("X-B3-TraceId");

    protected int code = 200;

    protected String message;

    private T data;

    protected Response(int code, String message) {
        this.code = code;
        this.message = message;
    }

    protected Response(T data) {
        this.data = data;
    }

    public boolean success() {
        return code == 200;
    }

    public static Response<Void> error(int errorCode, String errorMessage) {
        return new Response<>(errorCode, errorMessage);
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(data);
    }

}
