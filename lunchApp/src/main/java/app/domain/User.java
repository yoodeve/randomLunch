package app.domain;

/**
 * 사용자 정보를 저장하는 클래스
 */
public class User {
    private String name;
    private String id;
    private String password;
    private String bankName;
    private String accountNumber;

    /**
     * User 생성자
     * @param name 사용자 이름
     * @param id 사용자 아이디
     * @param password 사용자 비밀번호
     * @param bankName 은행명
     * @param accountNumber 계좌번호
     */
    public User(String name, String id, String password, String bankName, String accountNumber) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
    }

    // Getters
    public String getName() { return name; }
    public String getId() { return id; }
    public String getPassword() { return password; }
    public String getBankName() { return bankName; }
    public String getAccountNumber() { return accountNumber; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setId(String id) { this.id = id; }
    public void setPassword(String password) { this.password = password; }
    public void setBankName(String bankName) { this.bankName = bankName; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", bankName='" + bankName + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                '}';
    }
}