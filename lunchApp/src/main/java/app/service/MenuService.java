package app.service;

import app.domain.Menu;
import app.domain.Room;

import java.util.List;
import java.util.Scanner;

public class MenuService {
    String menuName;
    List<String> menuList;

    public void makeMenu(int i, Room room, Scanner sc) {
       System.out.print((i+1) +"번째 친구가 먹고싶은 메뉴를 입력해주세요.==>");
       Menu menu = new Menu(room.getRoomName(),  sc.nextLine());
       menuList.add(menuName);
   }

   public void election() {

   }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public List<String> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<String> menuList) {
        this.menuList = menuList;
    }
}
