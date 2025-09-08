package app.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Room implements Serializable {
    private String roomName;           // 방 이름
    private int participantCount;      // 참가 인원 수
    private String ownerName;          // 방장 이름 (사용자 ID)
    private int totalPrice;            // 총 금액
    private String selectedMenu;       // 선택된 메뉴
    private LocalDateTime createdAt;   // 생성 시간
    private boolean isCompleted;       // 식사 완료 여부

    // 생성자
    public Room(String roomName, int participantCount, String ownerName, int totalPrice, String selectedMenu) {
        this.roomName = roomName;
        this.participantCount = participantCount;
        this.ownerName = ownerName;
        this.totalPrice = totalPrice;
        this.selectedMenu = selectedMenu;
        this.createdAt = LocalDateTime.now();
        this.isCompleted = false;
    }

    // 기본 생성자
    public Room() {
        this.createdAt = LocalDateTime.now();
        this.isCompleted = false;
    }

    /**
     * 1인당 금액을 계산합니다.
     */
    public int getPricePerPerson() {
        if (participantCount == 0) {
            return 0;
        }
        return totalPrice / participantCount;
    }

    /**
     * 식사 완료 처리
     */
    public void completeRoom() {
        this.isCompleted = true;
    }

    /**
     * 방 상태 확인 (진행중/완료)
     */
    public String getStatus() {
        return isCompleted ? "완료" : "진행중";
    }

    /**
     * 방 생성 후 경과 시간을 반환합니다.
     */
    public String getElapsedTime() {
        LocalDateTime now = LocalDateTime.now();
        long hours = java.time.Duration.between(createdAt, now).toHours();
        long minutes = java.time.Duration.between(createdAt, now).toMinutes() % 60;
        
        if (hours > 0) {
            return hours + "시간 " + minutes + "분 전";
        } else {
            return minutes + "분 전";
        }
    }

    // toString 메소드
    @Override
    public String toString() {
        return "Room{" +
                "roomName='" + roomName + '\'' +
                ", participantCount=" + participantCount +
                ", ownerName='" + ownerName + '\'' +
                ", totalPrice=" + totalPrice +
                ", selectedMenu='" + selectedMenu + '\'' +
                ", status='" + getStatus() + '\'' +
                '}';
    }

    // Getter 메소드들
    public String getRoomName() {
        return roomName;
    }

    public int getParticipantCount() {
        return participantCount;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public String getSelectedMenu() {
        return selectedMenu;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    // Setter 메소드들
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setParticipantCount(int participantCount) {
        this.participantCount = participantCount;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setSelectedMenu(String selectedMenu) {
        System.out.println("선택된 메뉴는 '" + selectedMenu + "' 입니다");
        this.selectedMenu = selectedMenu;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}