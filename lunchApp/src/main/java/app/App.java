package app;

import app.domain.Room;
import app.service.MenuService;
import app.service.RoomService;
import app.service.UserManagementSystem;

import java.util.Scanner;

public class App {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        UserManagementSystem ums = new UserManagementSystem(sc);
        while(true){
           ums.showMainMenu(sc);
        }
    }
}
