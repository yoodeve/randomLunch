package app.service;

import app.domain.Room;
import app.repository.RoomRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RoomService {
    private RoomRepository roomRepository;
    
    public RoomService() {
        this.roomRepository = new RoomRepository();
    }
    
    /**
     * 방 정보를 입력받습니다.
     */
    public Room askRoomInfo(Scanner sc, String currentUserId) {
        try {
            System.out.print("방 이름을 입력하세요: ");
            String roomName = sc.nextLine().trim();
            
            if (roomName.isEmpty()) {
                System.out.println("방 이름을 입력해주세요.");
                return null;
            }
            
            // 방 이름 중복 확인
            if (roomRepository.findByRoomName(roomName) != null) {
                System.out.println("이미 존재하는 방 이름입니다. 다른 이름을 입력해주세요.");
                return null;
            }
            
            System.out.print("참가 인원 수를 입력하세요: ");
            String participantCountStr = sc.nextLine().trim();
            
            int participantCount;
            try {
                participantCount = Integer.parseInt(participantCountStr);
                if (participantCount <= 0) {
                    System.out.println("참가 인원은 1명 이상이어야 합니다.");
                    return null;
                }
                if (participantCount > 20) {
                    System.out.println("참가 인원은 20명을 초과할 수 없습니다.");
                    return null;
                }
            } catch (NumberFormatException e) {
                System.out.println("올바른 숫자를 입력해주세요.");
                return null;
            }
            
            return new Room(roomName, participantCount, currentUserId, 0, null);
            
        } catch (Exception e) {
            System.out.println("방 정보 입력 중 오류가 발생했습니다: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * 방 목록을 생성합니다. (기존 코드 호환성 유지)
     */
    public List<Room> makeRoomList(Room room) {
        List<Room> roomList = new ArrayList<>();
        roomList.add(room);
        return roomList;
    }
    
    /**
     * 방을 저장합니다.
     */
    public boolean saveRoom(Room room) {
        return roomRepository.saveRoom(room);
    }
    
    /**
     * 특정 사용자의 방 목록을 조회합니다.
     */
    public List<Room> getUserRooms(String userId) {
        return roomRepository.getRoomsByOwner(userId);
    }
    
    /**
     * 방 이름으로 방을 찾습니다.
     */
    public Room findRoomByName(String roomName) {
        return roomRepository.findByRoomName(roomName);
    }
    
    /**
     * 방 정보를 업데이트합니다.
     */
    public boolean updateRoom(Room room) {
        return roomRepository.updateRoom(room);
    }
    
    /**
     * 방을 삭제합니다.
     */
    public boolean deleteRoom(String roomName) {
        return roomRepository.deleteRoom(roomName);
    }
}
