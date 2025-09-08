package app.commons;

import java.util.regex.Pattern;

//W정규식 유효성 검사
public class Regex {
    // 비밀번호 패턴
    private static final Pattern LENGTH_PATTERN = Pattern.compile(".{6,12}");
    private static final Pattern LOWERCASE_PATTERN = Pattern.compile(".*[a-z].*");
    private static final Pattern DIGIT_PATTERN = Pattern.compile(".*[0-9].*");
    private static final Pattern SPECIAL_PATTERN = Pattern.compile(".*[!@#$%^&*()_+\\-=\\[\\]{}|;:,.<>?].*");
    private static final Pattern NO_WHITESPACE_PATTERN = Pattern.compile("^\\S*$");

    // 계좌번호 패턴
    private static final Pattern LENGTH_PATTERN_B = Pattern.compile(".{10,15}");

    // 한글 이름 패턴
    private static final Pattern KOREAN_NAME_LENGTH_PATTERN = Pattern.compile(".{2,5}");
    private static final Pattern KOREAN_COMPLETE_PATTERN = Pattern.compile(".*[가-힣].*");
    private static final Pattern NO_CONSONANT_VOWEL_PATTERN = Pattern.compile("^[^ㄱ-ㅎㅏ-ㅣ]*$");

    // 아이디 패턴
    private static final Pattern ID_LENGTH_PATTERN = Pattern.compile(".{3,}");
    private static final Pattern ID_ALPHANUMERIC_PATTERN = Pattern.compile(".*[a-zA-Z].*");
    private static final Pattern ID_NUMERIC_PATTERN = Pattern.compile(".*[0-9].*");
    private static final Pattern ID_NO_SPECIAL_PATTERN = Pattern.compile("^[a-zA-Z0-9]*$");

    // 한 번에 모든 규칙을 검사
    private static final Pattern COMPLETE_PATTERN_PW = Pattern.compile(
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{}|;:,.<>?])(?!.*\\s).{6,12}$"
    );

    // 한글 이름 완전 검증 패턴
    private static final Pattern COMPLETE_PATTERN_KOREAN = Pattern.compile("^[가-힣]{2,5}$");

    // 아이디 완전 검증 패턴
    private static final Pattern COMPLETE_PATTERN_ID = Pattern.compile("^[a-zA-Z0-9]{3,}$");

    private static final Pattern COMPLETE_PATTERN_BANK = Pattern.compile(".{10,14}$");

    /**
     * 비밀번호 유효성 검사 (완전한 패턴 검사)
     * @param password 검사할 비밀번호
     * @return 유효하면 true, 그렇지 않으면 false
     */
    public static boolean isValidPasswordRegex_PW(String password) {
        return COMPLETE_PATTERN_PW.matcher(password).matches();
    }

    /**
     * 계좌번호 유효성 검사
     * @param password 검사할 계좌번호
     * @return 유효하면 true, 그렇지 않으면 false
     */
    public static boolean isValidPasswordRegex_BANK(String password) {
        return COMPLETE_PATTERN_BANK.matcher(password).matches();
    }

    /**
     * 상세한 피드백을 제공하는 비밀번호 검증
     * @param password 검사할 비밀번호
     * @return 유효하면 true, 그렇지 않으면 false (콘솔에 오류 메시지 출력)
     */
    public static boolean isValidPasswordWithFeedbackPw(String password) {
        boolean isValid = true;

        if (!LENGTH_PATTERN.matcher(password).matches()) {
            System.out.println("→ 6자 이상 12자 이하여야 합니다.");
            isValid = false;
        }

        if (!LOWERCASE_PATTERN.matcher(password).matches()) {
            System.out.println("→ 영문 소문자가 최소 1개 포함되어야 합니다.");
            isValid = false;
        }

        if (!DIGIT_PATTERN.matcher(password).matches()) {
            System.out.println("→ 숫자가 최소 1개 포함되어야 합니다.");
            isValid = false;
        }

        if (!SPECIAL_PATTERN.matcher(password).matches()) {
            System.out.println("→ 특수문자가 최소 1개 포함되어야 합니다.");
            isValid = false;
        }

        if (!NO_WHITESPACE_PATTERN.matcher(password).matches()) {
            System.out.println("→ 공백은 포함할 수 없습니다.");
            isValid = false;
        }

        return isValid;
    }

    /**
     * 한글 이름 유효성 검사
     * @param name 검사할 이름
     * @return 유효하면 true, 그렇지 않으면 false
     */
    public static boolean isValidKoreanName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return COMPLETE_PATTERN_KOREAN.matcher(name.trim()).matches();
    }

    /**
     * 아이디 유효성 검사
     * @param id 검사할 아이디
     * @return 유효하면 true, 그렇지 않으면 false
     */
    public static boolean isValidId(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }
        return COMPLETE_PATTERN_ID.matcher(id.trim()).matches();
    }
}