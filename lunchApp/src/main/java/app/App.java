package app;

import app.domain.Room;
import app.service.MenuService;
import app.service.RoomService;

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
                    rs.makeRoomList(r);
                    for(int i = 0 ; i < r.getParticipantCount(); i ++) {
                        ms.makeMenu(i, r, sc);
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
