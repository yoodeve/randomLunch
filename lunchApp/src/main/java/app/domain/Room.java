package app.domain;

import app.service.MenuService;

public class Room {
    private String roomName;
    int participantCount;
    private String ownerName;
    private int totalPrice;
    private String selectedMenu;

    public Room(String roomName, int participantCount, String ownerName, int totalPrice, String selectedMenu) {
        this.roomName = roomName;
        this.participantCount = participantCount;
        this.ownerName = ownerName;
        this.totalPrice = totalPrice;
        this.selectedMenu = selectedMenu;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomName='" + roomName + '\'' +
                ", participantCount='" + participantCount + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", totalPrice=" + totalPrice +
                ", selectedMenu='" + selectedMenu + '\'' +
                '}';
    }

    public String getSelectedMenu() {
        return selectedMenu;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public int getParticipantCount() {
        return participantCount;
    }

    public String getRoomName() {
        return roomName;
    }

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
        System.out.println("선정된 메뉴는 '"+selectedMenu+"' 입니다");
        this.selectedMenu = selectedMenu;
    }
}