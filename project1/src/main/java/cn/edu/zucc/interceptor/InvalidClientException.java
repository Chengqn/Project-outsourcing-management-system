package cn.edu.zucc.interceptor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class InvalidClientException extends RuntimeException {

    public InvalidClientException(String msg) {
        super(msg);
    }
}
