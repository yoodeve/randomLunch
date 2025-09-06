package app.repository;

import java.nio.file.Path;
import java.nio.file.Paths;

public class RoomRepository {
    // 다른 텍스트 파일에 연결될 필요가 없으므로 Default 생성자만 존재하며,
    // 생성자 호출을 할때 연결하지 않고, Room.txt을 Path로 가지고 있다 ^^
    private final Path path = Paths.get("Room.txt");
}
