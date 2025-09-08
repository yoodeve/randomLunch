package app.service;


import java.util.Scanner;

// 회원가입, 로그인 테스트
class UserManagementMain {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UserManagementSystem system = new UserManagementSystem(sc);
        try {
            system.showMainMenu(sc);
        } finally {
            system.closeScanner();
        }
    }
}