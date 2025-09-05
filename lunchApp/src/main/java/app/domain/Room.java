package app.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Room {
    private static String roomName;
    private static int participantCount;
    private static String ownerName;
    private static Date createdAt;

    Room (String roomName, int participantCount,String ownerName,String electedMenu) {
        this.roomName = roomName;
        this.participantCount = participantCount;
        this.ownerName = ownerName;
        this.createdAt = new Date();
    }
}