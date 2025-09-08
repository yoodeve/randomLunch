// UserRepository.java - 사용자 데이터 관리
package app.repository;

import app.domain.User;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class UserRepository {
    private final String fileName = "User.txt";
    
    /**
     * 모든 사용자 목록을 불러옵니다.
     */
    public List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        File file = new File(fileName);
        
        if (!file.exists() || file.length() == 0) {
            return users;
        }
        
        try (FileInputStream fis = new FileInputStream(fileName);
             BufferedInputStream bis = new BufferedInputStream(fis);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            
            users = (List<User>) ois.readObject();
            
        } catch (Exception e) {
            System.out.println("사용자 데이터를 불러오는 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return users;
    }
    
    /**
     * 사용자 목록을 저장합니다.
     */
    public void saveUsers(List<User> users) {
        try (FileOutputStream fos = new FileOutputStream(fileName);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            
            oos.writeObject(users);
            
        } catch (Exception e) {
            System.out.println("사용자 데이터를 저장하는 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    /**
     * ID로 사용자를 찾습니다.
     */
    public User findById(String id) {
        List<User> users = loadUsers();
        return users.stream()
                   .filter(user -> user.getId().equals(id) && !user.isDeleted())
                   .findFirst()
                   .orElse(null);
    }
    
    /**
     * 아이디 중복을 확인합니다.
     */
    public boolean isIdExists(String id) {
        return findById(id) != null;
    }
    
    /**
     * 새 사용자를 저장합니다.
     */
    public boolean saveUser(User user) {
        try {
            List<User> users = loadUsers();
            users.add(user);
            saveUsers(users);
            return true;
        } catch (Exception e) {
            System.out.println("사용자 저장 중 오류가 발생했습니다: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * 사용자 정보를 업데이트합니다.
     */
    public boolean updateUser(User updatedUser) {
        try {
            List<User> users = loadUsers();
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getId().equals(updatedUser.getId())) {
                    users.set(i, updatedUser);
                    saveUsers(users);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("사용자 정보 업데이트 중 오류가 발생했습니다: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * 사용자를 삭제합니다 (실제로는 삭제 플래그 설정).
     */
    public boolean deleteUser(String id) {
        try {
            List<User> users = loadUsers();
            for (User user : users) {
                if (user.getId().equals(id)) {
                    user.markAsDeleted();
                    saveUsers(users);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("사용자 삭제 중 오류가 발생했습니다: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * 로그인을 확인합니다.
     */
    public User authenticate(String id, String password) {
        User user = findById(id);
        if (user != null && user.checkPassword(password)) {
            return user;
        }
        return null;
    }
}

//========================================

// RoomRepository.java - 방 데이터 관리  
package app.repository;

import app.domain.Room;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class RoomRepository {
    private final String fileName = "Room.txt";
    
    /**
     * 모든 방 목록을 불러옵니다.
     */
    public List<Room> loadRooms() {
        List<Room> rooms = new ArrayList<>();
        File file = new File(fileName);
        
        if (!file.exists() || file.length() == 0) {
            return rooms;
        }
        
        try (FileInputStream fis = new FileInputStream(fileName);
             BufferedInputStream bis = new BufferedInputStream(fis);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            
            rooms = (List<Room>) ois.readObject();
            
        } catch (Exception e) {
            System.out.println("방 데이터를 불러오는 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return rooms;
    }
    
    /**
     * 방 목록을 저장합니다.
     */
    public void saveRooms(List<Room> rooms) {
        try (FileOutputStream fos = new FileOutputStream(fileName);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            
            oos.writeObject(rooms);
            
        } catch (Exception e) {
            System.out.println("방 데이터를 저장하는 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 특정 사용자가 개설한 방들을 조회합니다.
     */
    public List<Room> getRoomsByOwner(String ownerId) {
        List<Room> allRooms = loadRooms();
        return allRooms.stream()
                      .filter(room -> room.getOwnerName().equals(ownerId))
                      .collect(Collectors.toList());
    }
    
    /**
     * 방을 저장합니다.
     */
    public boolean saveRoom(Room room) {
        try {
            List<Room> rooms = loadRooms();
            rooms.add(room);
            saveRooms(rooms);
            return true;
        } catch (Exception e) {
            System.out.println("방 저장 중 오류가 발생했습니다: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * 방 이름으로 방을 찾습니다.
     */
    public Room findByRoomName(String roomName) {
        List<Room> rooms = loadRooms();
        return rooms.stream()
                   .filter(room -> room.getRoomName().equals(roomName))
                   .findFirst()
                   .orElse(null);
    }
    
    /**
     * 방 정보를 업데이트합니다.
     */
    public boolean updateRoom(Room updatedRoom) {
        try {
            List<Room> rooms = loadRooms();
            for (int i = 0; i < rooms.size(); i++) {
                if (rooms.get(i).getRoomName().equals(updatedRoom.getRoomName())) {
                    rooms.set(i, updatedRoom);
                    saveRooms(rooms);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("방 정보 업데이트 중 오류가 발생했습니다: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * 방을 삭제합니다.
     */
    public boolean deleteRoom(String roomName) {
        try {
            List<Room> rooms = loadRooms();
            rooms.removeIf(room -> room.getRoomName().equals(roomName));
            saveRooms(rooms);
            return true;
        } catch (Exception e) {
            System.out.println("방 삭제 중 오류가 발생했습니다: " + e.getMessage());
            return false;
        }
    }
}