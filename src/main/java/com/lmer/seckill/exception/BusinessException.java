/**
 * @program: seckill
 * @description:
 * @author: Lmer
 * @create: 2022-03-22 08:54
 **/
package com.lmer.seckill.exception;

public class BusinessException extends RuntimeException{

    public BusinessException() {
        super();
    }

    public BusinessException(final String message) {
        super(message);
    }


    public BusinessException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public BusinessException(final Throwable cause) {
        super(cause);
    }


    protected BusinessException(final String message, final Throwable cause, boolean enableSuppression,
                                boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
