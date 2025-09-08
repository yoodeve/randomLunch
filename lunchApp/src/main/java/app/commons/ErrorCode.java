package app.commons;

public enum ErrorCode {
    PAYMENT_AMOUNT_EXCEEDED("결제 금액은 천만 원을 넘을 수 없습니다."),
    INVALID_SELECTION("잘못된 선택입니다. 허용되는 번호: %s"),
    PERSON_NUMBER_OUT_OF_RANGE("참여자 번호가 허용 범위를 벗어났습니다."),
    EXTRA_AMOUNT_EXCEEDED("추가 결제 금액이 허용 범위를 초과햤습니다.");

    private String message;
    private ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public String getMessage(String allowed) {
        return String.format(message, allowed);
    }

}