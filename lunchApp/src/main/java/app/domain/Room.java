package app.domain;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private static String roomName;
    private static int memberNumber;
    private static String candidateMenu;
    private static String electedMenu;

    Room (String roomName, int memberNumber,String candidateMenu,String electedMenu) {
        this.roomName = roomName;
        this.memberNumber = memberNumber;
        this.candidateMenu = candidateMenu;
        this.electedMenu = electedMenu;
    }
}