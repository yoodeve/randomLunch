package app.service;

import app.domain.Room;

import java.util.HashMap;
import java.util.Map;
import app.domain.Menu;

import java.util.List;
import java.util.Scanner;

public class MenuService {
    String menuName;
    Map<String, Integer> menuList = new HashMap<>();

    @Override
    public String toString() {
        return "MenuService{" +
                "menuList=" + menuList +
                '}';
    }

    public String makeMenu(int i, Scanner sc) {
        System.out.print((i + 1) + "번째 친구가 먹고싶은 메뉴를 입력해주세요.==>");
        String wantedMenu = sc.nextLine();
        menuList.put(wantedMenu, 0);
        menuList.forEach((m, integer) -> {
            if(m.equals(menuName)) {
                System.out.println("이미 존재하는 메뉴");
            }
        });
        return wantedMenu;
   }

   public Map<String, Integer> electMenu(Scanner sc, Room r) {
       String selectedMenu = "";
       for (int i = 0; i < r.getParticipantCount(); i++) {
           System.out.println("i"+i);
           System.out.println("투표해주세요");
           selectedMenu = sc.nextLine();
       }
       menuList.put(selectedMenu, menuList.get(selectedMenu) + 1);
       return menuList;
   }
   
}
