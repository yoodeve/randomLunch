package app.domain;

import app.service.RoomService;

public class User {
    private String name; //이름
    private String id; // 아이디
    private String password; // 비밀번호
    private String bankName; //은행
    private String bankNum;

    public User (String name, String id, String password, String bankName, String bankNum) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.bankName = bankName;
        this.bankNum = bankNum;
    }

}

