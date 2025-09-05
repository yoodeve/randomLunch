package app.domain;

import java.time.LocalDateTime;

public class Menu {
    // 주석 테스트
    private String roomName;
    private String menuName;
    private LocalDateTime createdAt;
    private int votes;

    public Menu(String roomName, String menuName) {
        this.roomName = roomName;
        this.menuName = menuName;
        this.createdAt = LocalDateTime.now();
        this.votes = 0;
    }

    public void addVote() {
        this.votes++;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getMenuName() {
        return menuName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getVotes() {
        return votes;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "roomName='" + roomName + '\'' +
                ", menuName='" + menuName + '\'' +
                ", createdAt=" + createdAt +
                ", votes=" + votes +
                '}';
    }
}