package com.ohohchoo.domain.review.exception;

public class RecommendNotFoundException extends RuntimeException {
    public RecommendNotFoundException() {
        super();
    }

    public RecommendNotFoundException(String message) {
        super(message);
    }

    public RecommendNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecommendNotFoundException(Throwable cause) {
        super(cause);
    }
}
