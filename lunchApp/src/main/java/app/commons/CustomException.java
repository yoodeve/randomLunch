package app.commons;

public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    public CustomException(String message){
        super(message);
        errorCode = null;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
