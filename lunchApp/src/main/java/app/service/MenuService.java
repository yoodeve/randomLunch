package app.service;

import app.domain.Menu;

import java.util.Scanner;

public class MenuService {
    String menuName;

   public Menu makeMenu(String roomName, Scanner sc) {
        Menu menu = new Menu(roomName,  sc.nextLine());
       return menu;
   }
}
