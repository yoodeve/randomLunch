package app.service;

import app.domain.Menu;
import app.domain.Room;
import app.repository.MenuRepository;

import java.util.*;
import java.util.stream.Collectors;

public class MenuService {
    private Map<String, Integer> menuList = new HashMap<>();
    private MenuRepository menuRepository = new MenuRepository();

    public MenuService() {
        // 기본 생성자
    }

    /**
     * 사용자가 직접 메뉴를 선택하는 메소드
     */
    public Map<String, Integer> electMenu(Scanner sc, Room room) {
        Map<String, Integer> voteMap = new HashMap<>();
        
        System.out.println("\n=== 메뉴 투표를 시작합니다 ===");
        for (int i = 0; i < room.getParticipantCount(); i++) {
            System.out.print((i + 1) + "번째 참가자가 먹고싶은 메뉴를 입력해주세요 ===> ");
            String wantedMenu = sc.nextLine().trim();
            
            if (wantedMenu.isEmpty()) {
                System.out.println("메뉴를 입력해주세요.");
                i--; // 다시 입력받기
                continue;
            }
            
            voteMap.put(wantedMenu, voteMap.getOrDefault(wantedMenu, 0) + 1);
        }
        
        System.out.println("\n투표 결과:");
        System.out.println("------------------------");
        voteMap.entrySet().stream()
               .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
               .forEach(entry -> 
                   System.out.println(entry.getKey() + ": " + entry.getValue() + "표")
               );
        System.out.println("------------------------");
        
        return voteMap;
    }

    /**
     * 기본 메뉴에서 랜덤으로 메뉴를 선택하는 메소드
     */
    public Map<String, Integer> randomMenu(Map<String, Integer> availableMenus) {
        Map<String, Integer> result = new HashMap<>();
        
        // 기본 메뉴 리스트가 비어있으면 저장된 메뉴에서 가져오기
        if (availableMenus.isEmpty()) {
            List<Menu> defaultMenus = menuRepository.loadMenus();
            for (Menu menu : defaultMenus) {
                availableMenus.put(menu.getName(), 1);
            }
        }
        
        // 랜덤으로 메뉴 선택
        if (!availableMenus.isEmpty()) {
            List<String> menuNames = new ArrayList<>(availableMenus.keySet());
            Random random = new Random();
            String selectedMenu = menuNames.get(random.nextInt(menuNames.size()));
            result.put(selectedMenu, 1);
            System.out.println("🎲 랜덤으로 선택된 메뉴: " + selectedMenu);
        } else {
            System.out.println("선택할 수 있는 메뉴가 없습니다.");
        }
        
        return result;
    }

    /**
     * 인기 메뉴 10개 중에서 랜덤으로 선택 (5회차부터 노출)
     */
    public Map<String, Integer> randomFromPopularMenus() {
        Map<String, Integer> result = new HashMap<>();
        
        try {
            List<Menu> allMenus = menuRepository.loadMenus();
            
            // 승리 횟수가 5회 이상인 메뉴만 필터링
            List<Menu> popularMenus = allMenus.stream()
                    .filter(menu -> menu.getWins() >= 5)
                    .sorted((m1, m2) -> Integer.compare(m2.getWins(), m1.getWins()))
                    .limit(10)
                    .collect(Collectors.toList());
            
            if (popularMenus.isEmpty()) {
                System.out.println("인기 메뉴가 아직 충분하지 않습니다. 기본 메뉴에서 선택합니다.");
                return randomMenu(getMenuList());
            }
            
            Random random = new Random();
            Menu selectedMenu = popularMenus.get(random.nextInt(popularMenus.size()));
            result.put(selectedMenu.getName(), 1);
            
            System.out.println("🏆 인기 메뉴에서 랜덤 선택: " + selectedMenu.getName() + 
                             " (승리횟수: " + selectedMenu.getWins() + "회)");
            
        } catch (Exception e) {
            System.out.println("인기 메뉴 선택 중 오류가 발생했습니다: " + e.getMessage());
            return randomMenu(getMenuList());
        }
        
        return result;
    }

    /**
     * 기존의 proceedVote 메소드 (기존 코드와의 호환성 유지)
     */
    public void proceedVote(Scanner sc, int numOfUsers) {
        this.menuList.clear();
        for (int i = 0; i < numOfUsers; i++) {
            voteMenu(i, sc);
        }
    }

    private void voteMenu(int i, Scanner sc) {
        System.out.print((i + 1) + "번째 친구가 먹고싶은 메뉴를 투표해주세요 ===> ");
        String wantedMenu = sc.nextLine();
        if (!menuList.containsKey(wantedMenu)) {
            menuList.put(wantedMenu, 1);
        } else {
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
        List<String> winners = new ArrayList<>();
        int highest = highestVote();
        for (String key : menuList.keySet()) {
            if (menuList.get(key) == highest) {
                winners.add(key);
            }
        }
        return winners;
    }

    public String getWinner(Scanner sc, int numOfUsers) {
        List<String> winners = getAllWinners();
        if (winners.size() > 1) {
            System.out.println("동점이 발생했습니다. 재투표를 진행합니다.");
            menuList.clear();
            proceedVote(sc, numOfUsers);
            return getWinner(sc, numOfUsers);
        }
        return winners.isEmpty() ? "미선택" : winners.get(0);
    }

    /**
     * 승리한 메뉴의 승리 횟수를 업데이트합니다.
     */
    public void updateWinner(String winner) {
        try {
            List<Menu> loadedMenuList = this.menuRepository.loadMenus();
            boolean found = false;
            
            for (Menu menu : loadedMenuList) {
                if (winner.equals(menu.getName())) {
                    menu.addWins();
                    found = true;
                    break;
                }
            }
            
            // 새로운 메뉴라면 추가
            if (!found) {
                Menu newMenu = new Menu(winner);
                newMenu.addWins();
                loadedMenuList.add(newMenu);
            }

            this.menuRepository.saveMenus(loadedMenuList);
            System.out.println("✅ 메뉴 '" + winner + "'의 승리 횟수가 업데이트되었습니다.");
            
        } catch (Exception e) {
            System.out.println("메뉴 승리 횟수 업데이트 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    /**
     * 인기 메뉴 순위를 조회합니다.
     */
    public void showPopularMenus() {
        try {
            List<Menu> allMenus = menuRepository.loadMenus();
            
            if (allMenus.isEmpty()) {
                System.out.println("등록된 메뉴가 없습니다.");
                return;
            }
            
            System.out.println("\n========== 인기 메뉴 순위 ==========");
            allMenus.stream()
                   .sorted((m1, m2) -> Integer.compare(m2.getWins(), m1.getWins()))
                   .limit(10)
                   .forEach(menu -> 
                       System.out.println(menu.getName() + ": " + menu.getWins() + "승")
                   );
            System.out.println("==================================");
            
        } catch (Exception e) {
            System.out.println("인기 메뉴 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    /**
     * makeMenu 메소드 (App.java에서 호출되는 메소드 - 호환성 유지)
     */
    public void makeMenu(int index, Scanner sc) {
        System.out.println("메뉴 생성 중... (참가자 " + (index + 1) + ")");
    }

    /**
     * 메뉴 리스트를 반환합니다.
     */
    public Map<String, Integer> getMenuList() {
        if (menuList.isEmpty()) {
            // 저장된 메뉴들을 불러와서 Map으로 변환
            List<Menu> savedMenus = menuRepository.loadMenus();
            for (Menu menu : savedMenus) {
                menuList.put(menu.getName(), 0); // 초기값 0으로 설정
            }
        }
        return menuList;
    }

    @Override
    public String toString() {
        return "MenuService{" +
                "menuList=" + menuList +
                '}';
    }
}
