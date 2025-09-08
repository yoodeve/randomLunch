package app.service;

import app.domain.Room;
import app.domain.User;
import app.repository.RoomRepository;
import app.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

public class MyPageService {
    private UserRepository userRepository;
    private RoomRepository roomRepository;
    
    public MyPageService() {
        this.userRepository = new UserRepository();
        this.roomRepository = new RoomRepository();
    }
    
    /**
     * 마이페이지 메인 메뉴를 표시합니다.
     */
    public void showMyPageMenu(Scanner sc, String currentUserId) {
        while (true) {
            System.out.println("\n========== 마이페이지 ==========");
            System.out.println("1. 방 개설 내역");
            System.out.println("2. 내 정보");
            System.out.println("3. 메인메뉴로 돌아가기");
            System.out.print("메뉴를 선택하세요 ===> ");
            
            String choice = sc.nextLine();
            
            switch (choice) {
                case "1":
                    showRoomHistory(sc, currentUserId);
                    break;
                case "2":
                    showUserInfo(sc, currentUserId);
                    break;
                case "3":
                    System.out.println("메인메뉴로 돌아갑니다.");
                    return;
                default:
                    System.out.println("올바른 번호를 입력해주세요.");
            }
        }
    }
    
    /**
     * 방 개설 내역을 보여줍니다.
     */
    private void showRoomHistory(Scanner sc, String currentUserId) {
        while (true) {
            System.out.println("\n========== 방 개설 내역 ==========");
            System.out.println("1. 방 목록 조회");
            System.out.println("2. 방 상세내용 조회");
            System.out.println("3. 뒤로가기");
            System.out.print("메뉴를 선택하세요 ===> ");
            
            String choice = sc.nextLine();
            
            switch (choice) {
                case "1":
                    showRoomList(currentUserId);
                    break;
                case "2":
                    showRoomDetail(sc, currentUserId);
                    break;
                case "3":
                    return;
                default:
                    System.out.println("올바른 번호를 입력해주세요.");
            }
        }
    }
    
    /**
     * 사용자가 개설한 방 목록을 조회합니다.
     */
    private void showRoomList(String currentUserId) {
        try {
            List<Room> userRooms = roomRepository.getRoomsByOwner(currentUserId);
            
            if (userRooms.isEmpty()) {
                System.out.println("개설한 방이 없습니다.");
                return;
            }
            
            System.out.println("\n========== 내가 개설한 방 목록 ==========");
            System.out.printf("%-5s %-15s %-10s %-8s %-15s%n", "번호", "방이름", "개설자", "인원수", "선택된메뉴");
            System.out.println("--------------------------------------------------------");
            
            for (int i = 0; i < userRooms.size(); i++) {
                Room room = userRooms.get(i);
                String selectedMenu = room.getSelectedMenu() != null ? room.getSelectedMenu() : "미선택";
                System.out.printf("%-5d %-15s %-10s %-8d %-15s%n", 
                    (i + 1), room.getRoomName(), room.getOwnerName(), 
                    room.getParticipantCount(), selectedMenu);
            }
            System.out.println("--------------------------------------------------------");
            System.out.println("총 " + userRooms.size() + "개의 방을 개설하셨습니다.");
            
        } catch (Exception e) {
            System.out.println("방 목록을 불러오는 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 선택한 방의 상세 정보를 조회합니다.
     */
    private void showRoomDetail(Scanner sc, String currentUserId) {
        try {
            List<Room> userRooms = roomRepository.getRoomsByOwner(currentUserId);
            
            if (userRooms.isEmpty()) {
                System.out.println("개설한 방이 없습니다.");
                return;
            }
            
            showRoomList(currentUserId);
            System.out.print("상세정보를 볼 방 번호를 입력하세요 ===> ");
            
            String input = sc.nextLine();
            try {
                int roomIndex = Integer.parseInt(input) - 1;
                
                if (roomIndex < 0 || roomIndex >= userRooms.size()) {
                    System.out.println("올바른 방 번호를 입력해주세요.");
                    return;
                }
                
                Room selectedRoom = userRooms.get(roomIndex);
                displayRoomDetailInfo(selectedRoom);
                
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
            
        } catch (Exception e) {
            System.out.println("방 상세정보를 불러오는 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 방의 상세 정보를 화면에 표시합니다.
     */
    private void displayRoomDetailInfo(Room room) {
        System.out.println("\n========== 방 상세 정보 ==========");
        System.out.println("방 이름: " + room.getRoomName());
        System.out.println("개설자: " + room.getOwnerName());
        System.out.println("참가 인원: " + room.getParticipantCount() + "명");
        System.out.println("총 금액: " + String.format("%,d", room.getTotalPrice()) + "원");
        System.out.println("선택된 메뉴: " + (room.getSelectedMenu() != null ? room.getSelectedMenu() : "미선택"));
        
        if (room.getTotalPrice() > 0 && room.getParticipantCount() > 0) {
            int pricePerPerson = room.getTotalPrice() / room.getParticipantCount();
            System.out.println("1인당 금액: " + String.format("%,d", pricePerPerson) + "원");
        }
        System.out.println("=====================================");
    }
    
    /**
     * 내 정보 메뉴를 표시합니다.
     */
    private void showUserInfo(Scanner sc, String currentUserId) {
        while (true) {
            System.out.println("\n========== 내 정보 ==========");
            System.out.println("1. 회원 정보 조회");
            System.out.println("2. 회원 정보 수정");
            System.out.println("3. 회원 탈퇴");
            System.out.println("4. 뒤로가기");
            System.out.print("메뉴를 선택하세요 ===> ");
            
            String choice = sc.nextLine();
            
            switch (choice) {
                case "1":
                    showUserProfile(currentUserId);
                    break;
                case "2":
                    updateUserInfo(sc, currentUserId);
                    break;
                case "3":
                    if (deleteUser(sc, currentUserId)) {
                        System.out.println("회원탈퇴가 완료되었습니다. 프로그램을 종료합니다.");
                        System.exit(0);
                    }
                    break;
                case "4":
                    return;
                default:
                    System.out.println("올바른 번호를 입력해주세요.");
            }
        }
    }
    
    /**
     * 사용자 정보를 조회합니다.
     */
    private void showUserProfile(String currentUserId) {
        try {
            User user = userRepository.findById(currentUserId);
            
            if (user == null) {
                System.out.println("사용자 정보를 찾을 수 없습니다.");
                return;
            }
            
            System.out.println("\n========== 회원 정보 ==========");
            System.out.println("아이디: " + user.getId());
            System.out.println("이름: " + user.getName());
            System.out.println("은행명: " + user.getBankName());
            System.out.println("계좌번호: " + maskAccountNumber(user.getBankNum()));
            System.out.println("===========================");
            
        } catch (Exception e) {
            System.out.println("회원 정보를 불러오는 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 계좌번호를 마스킹 처리합니다.
     */
    private String maskAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.length() < 4) {
            return "****";
        }
        
        String lastFour = accountNumber.substring(accountNumber.length() - 4);
        return "*".repeat(accountNumber.length() - 4) + lastFour;
    }
    
    /**
     * 사용자 정보를 수정합니다.
     */
    private void updateUserInfo(Scanner sc, String currentUserId) {
        try {
            User user = userRepository.findById(currentUserId);
            
            if (user == null) {
                System.out.println("사용자 정보를 찾을 수 없습니다.");
                return;
            }
            
            System.out.println("\n========== 회원 정보 수정 ==========");
            System.out.println("수정할 항목을 선택하세요:");
            System.out.println("1. 비밀번호 변경");
            System.out.println("2. 계좌번호 변경");
            System.out.println("3. 취소");
            System.out.print("선택 ===> ");
            
            String choice = sc.nextLine();
            
            switch (choice) {
                case "1":
                    updatePassword(sc, user);
                    break;
                case "2":
                    updateAccountNumber(sc, user);
                    break;
                case "3":
                    return;
                default:
                    System.out.println("올바른 번호를 입력해주세요.");
                    return;
            }
            
            // 변경사항 저장
            userRepository.updateUser(user);
            System.out.println("정보가 성공적으로 수정되었습니다.");
            
        } catch (Exception e) {
            System.out.println("정보 수정 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 비밀번호를 변경합니다.
     */
    private void updatePassword(Scanner sc, User user) {
        System.out.print("현재 비밀번호를 입력하세요 ===> ");
        String currentPassword = sc.nextLine();
        
        if (!user.checkPassword(currentPassword)) {
            System.out.println("현재 비밀번호가 일치하지 않습니다.");
            return;
        }
        
        System.out.print("새 비밀번호를 입력하세요 ===> ");
        String newPassword = sc.nextLine();
        
        try {
            user.updatePassword(newPassword);
            System.out.println("비밀번호가 성공적으로 변경되었습니다.");
        } catch (IllegalArgumentException e) {
            System.out.println("비밀번호 변경 실패: " + e.getMessage());
        }
    }
    
    /**
     * 계좌번호를 변경합니다.
     */
    private void updateAccountNumber(Scanner sc, User user) {
        System.out.print("새 계좌번호를 입력하세요 ===> ");
        String newAccountNumber = sc.nextLine();
        
        try {
            user.updateAccountNumber(newAccountNumber);
            System.out.println("계좌번호가 성공적으로 변경되었습니다.");
        } catch (IllegalArgumentException e) {
            System.out.println("계좌번호 변경 실패: " + e.getMessage());
        }
    }
    
    /**
     * 회원 탈퇴를 처리합니다.
     */
    private boolean deleteUser(Scanner sc, String currentUserId) {
        try {
            User user = userRepository.findById(currentUserId);
            
            if (user == null) {
                System.out.println("사용자 정보를 찾을 수 없습니다.");
                return false;
            }
            
            System.out.println("\n========== 회원 탈퇴 ==========");
            System.out.println("정말로 탈퇴하시겠습니까? (y/n)");
            System.out.print("선택 ===> ");
            String confirm = sc.nextLine();
            
            if (!confirm.equalsIgnoreCase("y")) {
                System.out.println("탈퇴를 취소했습니다.");
                return false;
            }
            
            System.out.print("탈퇴를 위해 비밀번호를 입력하세요 ===> ");
            String password = sc.nextLine();
            
            if (!user.checkPassword(password)) {
                System.out.println("비밀번호가 일치하지 않습니다. 탈퇴를 취소합니다.");
                return false;
            }
            
            // 사용자 삭제 (실제로는 삭제 플래그 설정)
            userRepository.deleteUser(currentUserId);
            return true;
            
        } catch (Exception e) {
            System.out.println("회원 탈퇴 중 오류가 발생했습니다: " + e.getMessage());
            return false;
        }
    }
}