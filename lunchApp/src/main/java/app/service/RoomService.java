package app.service;
// Domain → Repository → Service
import app.domain.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RoomService {
    public List<Room> makeRoomList(Room room) {
        List<Room> roomList = new ArrayList<>();
        roomList.add(room);
        for (int i = 0; i < roomList.size(); i++) {
            System.out.println(roomList.get(i));
        }
        return roomList;
    }

    public Room askRoomInfo(Scanner sc) {
        System.out.println("방이름");
        String roomName = sc.nextLine();
        System.out.println("인원수");
        String participantCount = sc.nextLine();
        System.out.println("방장이름");
        String ownerName = sc.nextLine();
        return new Room(roomName, participantCount, ownerName, 0, null);
    }
}

