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
}