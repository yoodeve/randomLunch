package app;

import app.domain.Room;
import app.domain.User;
import app.service.MenuService;
import app.service.MyPageService;
import app.service.RoomService;
import app.service.UserService;

import java.util.*;
import java.util.Scanner;

public class App {
    private static String currentUserId = null; // 현재 로그인한 사용자 ID

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UserService userService = new UserService();
        RoomService roomService = new RoomService();
        MenuService menuService = new MenuService();
        MyPageService myPageService = new MyPageService();

        System.out.println("=== 메뉴 선택 및 정산 프로그램에 오신 것을 환영합니다! ===");

        try {
            while (true) {
                if (currentUserId == null) {
                    // 로그인 전 메뉴
                    showLoginMenu(sc, userService);
                } else {
                    // 로그인 후 메인 메뉴
                    showMainMenu(sc, roomService, menuService, myPageService);
                }
            }
        } catch (Exception e) {
            System.err.println("프로그램 실행 중 오류가 발생했습니다: " + e.getMessage());
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }

    /**
     * 로그인 전 메뉴를 표시합니다.
     */
    private static void showLoginMenu(Scanner sc, UserService userService) {
        System.out.println("\n========== 시작 메뉴 ==========");
        System.out.println("1. 회원가입");
        System.out.println("2. 로그인");
        System.out.println("3. 프로그램 종료");
        System.out.print("메뉴를 선택하세요 ===> ");

        String choice = sc.nextLine();

        switch (choice) {
            case "1":
                handleSignUp(sc, userService);
                break;
            case "2":
                handleLogin(sc, userService);
                break;
            case "3":
                System.out.println("프로그램을 종료합니다. 감사합니다!");
                System.exit(0);
                break;
            default:
                System.out.println("올바른 번호를 입력해주세요.");
        }
    }

    /**
     * 로그인 후 메인 메뉴를 표시합니다.
     */
    private static void showMainMenu(Scanner sc, RoomService roomService, MenuService menuService, MyPageService myPageService) {
        System.out.println("\n========== 메인 메뉴 ==========");
        System.out.println("현재 사용자: " + currentUserId);
        System.out.println("1. 방 개설");
        System.out.println("2. 마이페이지");
        System.out.println("3. 로그아웃");
        System.out.print("메뉴를 선택하세요 ===> ");

        String choice = sc.nextLine();

        switch (choice) {
            case "1":
                handleRoomCreation(sc, roomService, menuService);
                break;
            case "2":
                myPageService.showMyPageMenu(sc, currentUserId);
                break;
            case "3":
                handleLogout();
                break;
            default:
                System.out.println("올바른 번호를 입력해주세요.");
        }
    }

    /**
     * 회원가입을 처리합니다.
     */
    private static void handleSignUp(Scanner sc, UserService userService) {
        System.out.println("\n========== 회원가입 ==========");
        
        try {
            System.out.print("아이디를 입력하세요 (영문+숫자 조합, 3자리 이상): ");
            String id = sc.nextLine();
            
            // 아이디 중복 확인
            if (userService.isIdExists(id)) {
                System.out.println("이미 존재하는 아이디입니다. 다른 아이디를 입력해주세요.");
                return;
            }
            
            System.out.print("비밀번호를 입력하세요 (영문+숫자+특수문자, 6-12자): ");
            String password = sc.nextLine();
            
            System.out.print("이름을 입력하세요 (한글 2-17자): ");
            String name = sc.nextLine();
            
            System.out.print("은행명을 입력하세요: ");
            String bankName = sc.nextLine();
            
            System.out.print("계좌번호를 입력하세요 (10-14자리): ");
            String accountNumber = sc.nextLine();
            
            // 회원가입 처리
            if (userService.signUp(id, password, name, bankName, accountNumber)) {
                System.out.println("회원가입이 성공적으로 완료되었습니다!");
            } else {
                System.out.println("회원가입에 실패했습니다. 다시 시도해주세요.");
            }
            
        } catch (IllegalArgumentException e) {
            System.out.println("회원가입 실패: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("회원가입 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    /**
     * 로그인을 처리합니다.
     */
    private static void handleLogin(Scanner sc, UserService userService) {
        System.out.println("\n========== 로그인 ==========");
        
        try {
            System.out.print("아이디: ");
            String id = sc.nextLine();
            
            System.out.print("비밀번호: ");
            String password = sc.nextLine();
            
            User user = userService.login(id, password);
            if (user != null) {
                currentUserId = user.getId();
                System.out.println("로그인 성공! " + user.getName() + "님 환영합니다.");
            } else {
                System.out.println("아이디 또는 비밀번호가 일치하지 않습니다.");
            }
            
        } catch (Exception e) {
            System.out.println("로그인 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    /**
     * 로그아웃을 처리합니다.
     */
    private static void handleLogout() {
        System.out.println(currentUserId + "님이 로그아웃되었습니다.");
        currentUserId = null;
    }

    /**
     * 방 개설을 처리합니다.
     */
    private static void handleRoomCreation(Scanner sc, RoomService roomService, MenuService menuService) {
        System.out.println("\n========== 방 개설 ==========");
        
        try {
            Room room = roomService.askRoomInfo(sc, currentUserId);
            
            if (room != null) {
                // 방 개설 방식 선택
                System.out.println("\n방 개설 방식을 선택하세요:");
                System.out.println("1. 직접 투표로 메뉴 선택");
                System.out.println("2. 랜덤으로 메뉴 선택");
                System.out.println("3. 인기 메뉴에서 랜덤 선택");
                System.out.print("선택 ===> ");
                
                String menuChoice = sc.nextLine();
                Map<String, Integer> voteResult = new HashMap<>();
                
                switch (menuChoice) {
                    case "1":
                        voteResult = menuService.electMenu(sc, room);
                        break;
                    case "2":
                        voteResult = menuService.randomMenu(menuService.getMenuList());
                        break;
                    case "3":
                        voteResult = menuService.randomFromPopularMenus();
                        break;
                    default:
                        System.out.println("기본값으로 직접 투표를 선택합니다.");
                        voteResult = menuService.electMenu(sc, room);
                }
                
                // 최다 득표 메뉴 선택
                if (!voteResult.isEmpty()) {
                    String selectedMenu = getWinningMenu(voteResult);
                    room.setSelectedMenu(selectedMenu);
                    
                    // 총 금액 입력
                    System.out.print("총 식사 금액을 입력하세요 (원): ");
                    try {
                        int totalPrice = Integer.parseInt(sc.nextLine());
                        room.setTotalPrice(totalPrice);
                    } catch (NumberFormatException e) {
                        System.out.println("올바른 금액을 입력해주세요. 기본값 0원으로 설정합니다.");
                        room.setTotalPrice(0);
                    }
                    
                    // 방 저장
                    if (roomService.saveRoom(room)) {
                        menuService.updateWinner(selectedMenu);
                        System.out.println("방이 성공적으로 개설되었습니다!");
                        System.out.println("선택된 메뉴: " + selectedMenu);
                        System.out.println("총 금액: " + String.format("%,d", room.getTotalPrice()) + "원");
                        System.out.println("1인당 금액: " + String.format("%,d", room.getPricePerPerson()) + "원");
                    } else {
                        System.out.println("방 개설에 실패했습니다.");
                    }
                } else {
                    System.out.println("메뉴 선택에 실패했습니다.");
                }
            }
            
        } catch (Exception e) {
            System.out.println("방 개설 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    /**
     * 투표 결과에서 최다 득표 메뉴를 찾습니다.
     */
    private static String getWinningMenu(Map<String, Integer> voteResult) {
        return voteResult.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("미선택");
    }
}