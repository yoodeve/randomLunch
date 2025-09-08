package app.service;

import app.domain.Room;
import app.repository.RoomRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RoomService {
    private static final RoomRepository roomRepository = new RoomRepository();
    public List<Room> makeRoomList(Room room) {
        List<Room> roomList = new ArrayList<>();
        roomList.add(room);
        return roomList;
    }

    public Room askRoomInfo(Scanner sc) {
        System.out.print("방이름: ");
        String roomName = sc.nextLine();
        System.out.print("인원수: ");
        String participantCount = sc.nextLine();
        System.out.print("방장이름: ");
        String ownerName = sc.nextLine();
        Room room = new Room(roomName, Integer.parseInt(participantCount), ownerName, 0, null);
        roomRepository.saveRoom(room);
        return new Room(roomName, Integer.parseInt(participantCount), ownerName, 0, null);
    }
}

