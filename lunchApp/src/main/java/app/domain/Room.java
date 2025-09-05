package app.domain;

public class Room {
    private String roomName;
    private String participantCount;
    private String ownerName;
    private int totalPrice;
    private String selectedMenu;

    public Room(String roomName, String participantCount, String ownerName, int totalPrice, String selectedMenu) {
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

    public String getParticipantCount() {
        return participantCount;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setParticipantCount(String participantCount) {
        this.participantCount = participantCount;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setSelectedMenu(String selectedMenu) {
        this.selectedMenu = selectedMenu;
    }
}