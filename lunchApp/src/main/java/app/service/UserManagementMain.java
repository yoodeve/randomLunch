package app.service;


// 회원가입, 로그인 테스트
class UserManagementMain {

    public static void main(String[] args) {
        UserManagementSystem system = new UserManagementSystem();

        try {
            system.showMainMenu();
        } finally {
            system.closeScanner();
        }
    }
}