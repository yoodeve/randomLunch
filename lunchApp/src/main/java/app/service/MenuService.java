package app.service;

import app.domain.Menu;
import app.repository.MenuRepository;

import java.util.*;

public class MenuService {
    int numOfUsers;
    Map<String, Integer> menuList = new HashMap<>();
    MenuRepository menuRepository = new MenuRepository();

    public void proceedVote(Scanner sc) {
        for (int i = 0; i < numOfUsers; i++) {
            voteMenu(i, sc);
        }
    }

    private void voteMenu(int i, Scanner sc) {
        System.out.print((i + 1) + "번째 친구가 먹고싶은 메뉴를 투표해주세요.==>");
        String wantedMenu = sc.nextLine();
        if (!menuList.containsKey(wantedMenu)) {
            menuList.put(wantedMenu, 1);
        }
        else {
            menuList.put(wantedMenu, menuList.get(wantedMenu) + 1);
        }
   }

   private int highestVote() {
        int highestVote = 0;
        for (String key : menuList.keySet()) {
            if (menuList.get(key) > highestVote) {
                highestVote = menuList.get(key);
            }
        }
        return highestVote;
   }

   private List<String> getAllWinners() {
        List<String> Winners = new ArrayList<>();
        for (String key : menuList.keySet()) {
            if (menuList.get(key) == highestVote()) {
                Winners.add(key);
            }
        }
        return Winners;
   }

   public String getWinner(Scanner sc) {
        List<String> Winners = getAllWinners();
        if (Winners.size() > 1) {
            menuList.clear();
            proceedVote(sc);
            return getWinner(sc);
        }
        return Winners.get(0);
   }

   public void updateWinner(String winner) {
        List<Menu> loadedMenuList = this.menuRepository.loadMenus();
        for (int i = 0; i < loadedMenuList.size(); i++) {
            Menu menu = loadedMenuList.get(i);
            if (winner.equals(menu.getName())) {
                menu.addWins();
            }
        }

        this.menuRepository.saveMenus(loadedMenuList);
   }

   /*
    public Map<String, Integer> randomMenu () {
//        if(menuList.size() < 5) {
//            System.out.println("당신네는 아직 때가 아닙니다");
//        }
        int randomMenuIndex = (int)(Math.random() * this.menuList.size()) + 1;
        System.out.println("randomMenuIndex"+ randomMenuIndex);
//        return menuList.get(randomMenuIndex);
        return null;
    }
    */

    @Override
    public String toString() {
        return "MenuService{" +
                "menuList=" + menuList +
                '}';
    }

    public MenuService(int numOfUsers) {
        this.numOfUsers = numOfUsers;
    }
}
