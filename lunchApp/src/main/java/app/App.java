package app;

import app.domain.Menu;
import app.domain.Room;
import app.service.MenuService;
import app.service.RoomService;

import java.util.*;
import java.util.List;
import java.util.Scanner;
public class App {

    public static void main(String[] args) {
        RoomService rs = new RoomService();
        MenuService ms = new MenuService();

        try(Scanner sc = new Scanner(System.in)){
            System.out.print("1. 회원가입    2. 로그인    3. 나가기");
            System.out.print("번호 입력   ==>>>  ");
            String selectNum = sc.nextLine();
            switch(selectNum){
                case "1": break;
                case "2":
                    Room r = rs.askRoomInfo(sc);
                    Map<String, Integer> tempMap = new HashMap<>();

                    List<Room> roomList = rs.makeRoomList(r);
                    System.out.println("랜덤으로 뽑으려면 1번, 투표하시려면 2번");
                    String subMenuNum = sc.nextLine();
                    switch (subMenuNum) {
                        case "1" :
                        try {
                            if (roomList.size() < 5) {
                                System.out.println("당신네는 아직 때가 아닙니다");
                                break;
                            }
                        } catch (NoSuchElementException e) {
                            e.printStackTrace();
                        } finally {
//                            continue;
                        }
                            tempMap = ms.randomMenu(ms.getMenuList(), rs.makeRoomList(r));
                            break;
                        default:
                            for(int i = 0 ; i < r.getParticipantCount(); i ++) {
                                ms.makeMenu(i, sc);
                            }
                            tempMap = ms.electMenu(sc, r);
                    }
                    var iter = tempMap.entrySet().iterator();
                    var first = iter.next();
                    String maxKey = first.getKey();
                    int maxValue = first.getValue();

                    while (iter.hasNext()) {
                        var e = iter.next();
                        if (e.getValue() > maxValue) {
                            maxValue = e.getValue();
                            maxKey = e.getKey();
                        }
                    }
                    r.setSelectedMenu(maxKey);
                    for(int i = 0 ; i < r.getParticipantCount(); i ++) {
                        ms.makeMenu(i, sc);
                    }
                    break;
                case "3": break;
                default: break;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
