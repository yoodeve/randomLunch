package app.service;

import app.domain.Menu;
import app.repository.MenuRepository;

import java.util.*;

// MenuService class는 사용자가 실행할 수 있는 메뉴 관련 기능들(메뉴 투표 후 결과 반환 & 랜덤 추천 메뉴 받기)을 수행하는 클래스이다.
// 각 Room 객체는 instance variable로 MenuService 객체(의 주소)를 가지고 있다.
public class MenuService {
    int numOfUsers; // 방 인원수
    Map<String, Integer> menuList = new HashMap<>(); // 해당 방의 투표들을 메뉴이름(String) 별로 저장하기 위한 HashMap
    MenuRepository menuRepository = new MenuRepository(); // "Menu.txt" 파일 사용하기 위해

    /*
    @todo
    중요한코드는 위로... 멤버필드-no생성자-all생성자-함수-getter/setter순으로 정렬하는게 일반적인 컨벤션
    https://nooblette.tistory.com/entry/%EB%A9%94%EC%84%9C%EB%93%9C-%EB%B0%8F-%ED%95%84%EB%93%9C-%EC%84%A0%EC%96%B8-%EC%88%9C%EC%84%9C-%EC%BB%A8%EB%B2%A4%EC%85%98-%EC%9E%90%EB%B0%94
    참조
     */
    // default 생성자 호출을 제어하고 해당 클래스의 객체를 생성할 때,
    // 방의 인원 수를 parameter로 넣는 것을 강제시키기 위해 해당 생성자를 정의한다.
    public MenuService(int numOfUsers) {
        this.numOfUsers = numOfUsers;
    }

    // 방 인원 수 만큼 voteMenu() 함수를 진행시킨다.
    /*
    @todo
    굳이 함수로 뺄 이유가.. 걍 voteMenu에서 for문 돌리는게 더 깔끔할거같은데
     */
    public void proceedVote(Scanner sc) {
        for (int i = 0; i < numOfUsers; i++) {
            voteMenu(i, sc);
        }
    }

    // 투표 입력을 받아 HashMap인 menuList에 저장
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

   // 제일 높은 투표 수를 받은 menuList의 key의 투표 수를 반환한다.
   private int highestVote() {
        int highestVote = 0;
        for (String key : menuList.keySet()) {
            if (menuList.get(key) > highestVote) {
                highestVote = menuList.get(key);
            }
        }
        return highestVote;
   }

   // highestVote() 함수를 통해 얻은 가장 높은 투표 수를 바탕으로
   // menuList에서 value가 가장 높은 투표 수와 같은 key(들)를
   // 새로 생성한 ArrayList<String> Winners에 담고 반환한다.
   private List<String> getAllWinners() {
        List<String> Winners = new ArrayList<>();
        for (String key : menuList.keySet()) {
            if (menuList.get(key) == highestVote()) {
                Winners.add(key);
            }
        }
        return Winners;
   }

   // 재귀함수
   // getAllWinners() 함수를 통해 얻은 Winners의 크기가 >1 일 때,
   // 공동 1위가 존재하므로 재투표 진행한다.
   // 재투표를 하기 전에 투표한 음식들의 총 투표수를 HashMap으로 저장한 menuList를 초기화 하고
   // 재투표를 진행하고, 해당 함수를 재귀적으로 호출한다.
   public String getWinner(Scanner sc) {
        List<String> Winners = getAllWinners();
        if (Winners.size() > 1) {
            menuList.clear();
            proceedVote(sc);
            return getWinner(sc);
        }
        return Winners.get(0);
   }

   // 해당 메서드는 instance variable로 가지고 있는 menuRepository 객체를 활용하여
   // 해당 객체의 메서드인 loadMenus()를 사용하여 "Menu.txt"에 있는 객체 데이터(ArrayList<Menu>)를 읽어온다.
   // 그 다음에 불러온 ArrayList<Menu> 객체에서 순회하며 각 음식 객체의 이름(.getName())을 parameter로 받은 우승한 음식(String winner)과
   // .equals()를 사용하여 비교하여 true이면 해당 메뉴 객체의 우승 횟수를 1 추가한다.
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

   private int getInitiatedTimes() {
        int count = 0;
        List<Menu> loadedMenuList = this.menuRepository.loadMenus();
        for (int i = 0; i < loadedMenuList.size(); i++) {
            Menu menu = loadedMenuList.get(i);
            count += menu.getWins();
        }
        return count;
   }

   public String randomFromTopList() {
        if (getInitiatedTimes() < 5) {
            System.out.println("아직 충분히 데이터가 모이지 않았습니다.");
            return null;
        }
        List<Menu> loadedMenuList = this.menuRepository.loadMenus();
        int topListedMenuIndex = loadedMenuList.size() - 1;
        List<String> topList = new ArrayList<>(2);
        topList.add(loadedMenuList.get(topListedMenuIndex--).getName());
        topList.add(loadedMenuList.get(topListedMenuIndex).getName()); // 현재는 2개지만 추후에 10개로 업데이트 예정

        Random rand = new Random();

        return topList.get(rand.nextInt(topList.size()));
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

}
