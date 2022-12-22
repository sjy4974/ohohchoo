package com.ohohchoo.domain.review.exception;

public class DuplicationRecommendException extends RuntimeException {
    public DuplicationRecommendException() {
        super();
    }

    public DuplicationRecommendException(String message) {
        super(message);
    }

    public DuplicationRecommendException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicationRecommendException(Throwable cause) {
        super(cause);
    }
}
