package app.repository;


import app.domain.Room;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository {
    // 다른 텍스트 파일에 연결될 필요가 없으므로 Default 생성자만 존재하며,
    // 생성자 호출을 할때 연결하지 않고, Room.txt을 Path로 가지고 있다 ^^
    private final File file = new File("src/main/java/data/Room.txt");
    private List<Room> rooms = new ArrayList<>();
    public RoomRepository() {
        this.rooms = getRooms();
    }
    public List<Room> getRooms(){
        if(!file.exists()) return rooms;
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){
            rooms = (List<Room>) ois.readObject();
        }catch(Exception e){
            e.printStackTrace();
        }
        return rooms;
    }

    public void saveRoom(Room room){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))){
            rooms.add(room);
            oos.writeObject(rooms);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}



