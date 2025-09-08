package app.service;

import app.commons.Regex;
import app.domain.Room;
import app.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 회원 관리 시스템의 핵심 기능을 담당하는 클래스
 */
public class UserManagementSystem {
    private List<User> users;
    private SecurePasswordFileStorage passwordStorage = new SecurePasswordFileStorage();;
    private Regex regex = new Regex();
    private Scanner scanner;

    /**
     * UserManagementSystem 생성자
     */
    public UserManagementSystem(Scanner sc) {
        this.users = new ArrayList<>(); // 먼저 초기화
        this.scanner = sc;
        if (!users.isEmpty()) {
            User user = users.get(users.size() - 1);
//            this.passwordStorage = new SecurePasswordFileStorage(user.getId(), user.getPassword());
        }
//        else {
//            // 비어있을 때 처리
//            this.passwordStorage = new SecurePasswordFileStorage(null, null);
//        }
//        User user = users.get(users.size() - 1);
//        this.users = new ArrayList<>();
//        this.passwordStorage = new SecurePasswordFileStorage(user.getId(), user.getPassword());
//        this.scanner = new Scanner(Systemtem.in);
    }

    //계좌번호 유효성 검사
    private boolean isValidAccountNumber(String accountNumber) {
        return accountNumber != null &&
                accountNumber.matches("^\\d{11,13}$"); // 11~13자리 숫자만 허용
    }

    //아이디 중복체크
    private boolean isDuplicateId(String id) {
        return users.stream().anyMatch(user -> user.getId().equals(id));
    }

    //회원가입 기능
    public User registerUser() {
        System.out.println("=== 회원가입 ===");

        // 이름 입력 및 검증
        String name = getValidName();

        // 아이디 입력 및 검증
        String id = getValidId();

        // 비밀번호 입력 및 검증
        String password = getValidPassword();

        // 은행명 입력 및 검증
        String bankName = getValidBankName();

        // 계좌번호 입력 및 검증
        String accountNumber = getValidAccountNumber();

        // 사용자 생성 및 저장
        User newUser = new User(name, id, password, bankName, accountNumber);
        users.add(newUser);

        // 비밀번호 암호화 저장
        passwordStorage.savePassword(id, password);

        System.out.println("회원가입이 완료되었습니다!");
        System.out.println("등록된 회원 정보: " + newUser);
        return newUser;
    }

    /**
     * 유효한 이름을 입력받는 메서드
     * @return 유효한 이름
     */
    private String getValidName() {
        String name;
        while (true) {
            System.out.print("이름을 입력하세요 (완전한 한글만): ");
            name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("이름은 필수 입력사항입니다.");
                continue;
            }

            if (!name.equalsIgnoreCase(name)) {
                System.out.println("이름은 완전한 한글만 입력 가능합니다. (예: 홍길동, 김철수)");
                System.out.println("자음이나 모음이 단독으로 포함된 이름은 사용할 수 없습니다.");
                continue;
            }
            break;
        }
        return name;
    }

   //유효한 아이디 입력받는 메서드
    private String getValidId() {
        String id = "";
        while (true) {
            System.out.print("아이디를 입력하세요 (영문, 숫자 조합 3자리 이상): ");
            id = scanner.nextLine().trim();

            if (id.isEmpty()) {
                System.out.println("아이디는 필수 입력사항입니다.");
                continue;
            }

            if(!regex.isValidId(id)) {
                System.out.println("올바른 아이디를 입력해주세요");
                continue;
            }

            if (id.length() < 2) {
                System.out.println("아이디는 영문과 숫자 조합으로 3자리 이상이어야 합니다.");
                continue;
            }

            if (isDuplicateId(id)) {
                System.out.println("이미 사용 중인 아이디입니다. 다른 아이디를 입력해주세요.");
                continue;
            }
            break;
        }
        return id;
    }

    //유효한 비밀번호
    private String getValidPassword() {
        String password;
        while (true) {
            System.out.print("비밀번호를 입력하세요 (6~12자리, 영문 대소문자, 숫자, 특수문자 포함): ");
            password = scanner.nextLine();

            if (password.isEmpty()) {
                System.out.println("비밀번호는 필수 입력사항입니다.");
                continue;
            }

            // 상세한 피드백과 함께 비밀번호 검증
            if (!regex.isValidPasswordWithFeedbackPw(password)) {
                System.out.println("비밀번호가 요구사항을 만족하지 않습니다:");
                continue;
            }

            System.out.println("✓ 비밀번호가 유효합니다!");
            break;
        }
        return password;
    }

    //유효한 은행명
    private String getValidBankName() {
        String bankName;
        while (true) {
            System.out.print("은행명을 입력하세요: ");
            bankName = scanner.nextLine().trim();

            if (bankName.isEmpty()) {
                System.out.println("은행명은 필수 입력사항입니다.");
                continue;
            }
            break;
        }
        return bankName;
    }

    //유효한 계봐번호
    private String getValidAccountNumber() {
        String accountNumber;
        while (true) {
            System.out.print("계좌번호를 입력하세요 (11~13자리 숫자): ");
            accountNumber = scanner.nextLine().trim();

            if (!isValidAccountNumber(accountNumber)) {
                System.out.println("계좌번호는 11자리 이상 13자리 이하의 숫자여야 합니다.");
                continue;
            }
            break;
        }
        return accountNumber;
    }

    /**
     * 로그인 기능
     * @return 로그인 성공 시 User 객체, 실패 시 null
     */
    public User login() {
        System.out.println("=== 로그인 ===");

        String id = getLoginId();
        String password = getLoginPassword();

        // 사용자 찾기 및 비밀번호 검증
        for (User user : users) {
            if (user.getId().equals(id) && passwordStorage.verifyPassword(id, password)) {
                System.out.println("로그인 성공!");
                System.out.println("환영합니다, " + user.getName() + "님!");
                return user;
            }
        }

        System.out.println("아이디 또는 비밀번호가 일치하지 않습니다.");
        return null;
    }

    /**
     * 로그인용 아이디를 입력받는 메서드
     * @return 입력받은 아이디
     */
    private String getLoginId() {
        String id;
        while (true) {
            System.out.print("아이디를 입력하세요: ");
            id = scanner.nextLine().trim();

            if (id.isEmpty()) {
                System.out.println("아이디를 입력해주세요.");
            }
            if (!regex.isValidId(id)) {
                System.out.println("올바른 형식의 아이디를 입력해주세요.");
                continue;
            }

            break;
        }
        return id;
    }

    /**
     * 로그인용 비밀번호를 입력받는 메서드
     * @return 입력받은 비밀번호
     */
    private String getLoginPassword() {
        String password;
        while (true) {
            System.out.print("비밀번호를 입력하세요: ");
            password = scanner.nextLine();

            if (password.isEmpty()) {
                System.out.println("비밀번호를 입력해주세요.");
                continue;
            }

            // 로그인시에는 간단한 검증만 (저장된 비밀번호와 일치하는지가 중요)
            if (!Regex.isValidPasswordRegex_PW(password)) {
                System.out.println("올바른 형식의 비밀번호를 입력해주세요.");
                continue;
            }
            break;
        }
        return password;
    }

    /**
     * 등록된 모든 사용자 조회
     */
    public void showAllUsers() {
        System.out.println("=== 등록된 회원 목록 ===");
        if (users.isEmpty()) {
            System.out.println("등록된 회원이 없습니다.");
            return;
        }

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            System.out.println((i + 1) + ". " + user.toString());
        }


    }
    public void makeRoom(Scanner sc, User loggedInUser, RoomService rs) {
        System.out.println("1. 방만들기  2. 마이페이지");
        String subMenuNumber = sc.nextLine().trim();
        switch (subMenuNumber) {
            case "1":
                if (loggedInUser != null) {
                    Room room = rs.askRoomInfo(sc, loggedInUser);
                    MenuService menuService = new MenuService(room.getParticipantCount(), loggedInUser);
                    menuService.proceedVote(sc);
                    String winner = menuService.getWinner(sc);
                    menuService.updateWinner(winner);
                    room.setSelectedMenu(winner);
                }
                break;
            case "2":
                System.out.println("마이페이지");
        }
    }
    
    public void mainMenuSelect(Scanner sc, User user, RoomService roomService) {
        String subNumber = sc.nextLine();
        switch(subNumber) {
            case "1":
                makeRoom(sc, user, roomService);
                break;
            case "2":
                System.out.println("마이페이지");
            default:
                System.out.println("잘못된 번호");
        }
    }
    /**
     * 메인 메뉴 표시 및 처리
     */
    public void showMainMenu(Scanner sc) {
        RoomService roomService = new RoomService();
        while (true) {
            System.out.println("=== 회원 관리 시스템 ===");
            System.out.println("1. 회원가입");
            System.out.println("2. 로그인");
            System.out.println("3. 등록된 회원 목록 조회");
            System.out.println("4. 종료");
            System.out.print("메뉴를 선택하세요: ");

            String choice = sc.nextLine().trim();
            User user = null;
            switch (choice) {
                case "1":
                    user = registerUser();
                    makeRoom(sc, user, roomService);
                    mainMenuSelect(sc, user, roomService);
                    break;
                case "2":
                    System.out.println("1. 방만들기    2. 마이페이지");
                    String subNumber1 = sc.nextLine();
                    mainMenuSelect(sc, user, roomService);
                    break;
                case "3":
                    showAllUsers();
                    return;
                case "4":
                    System.out.println("프로그램을 종료합니다.");
                    System.exit(0);
                    return;
                default:
                    System.out.println("올바른 메뉴를 선택해주세요.");
            }
        }
    }

    /**
     * Scanner 리소스 해제
     */
    public void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
}