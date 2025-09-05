package app;

import app.domain.Room;
import app.service.RoomService;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        RoomService rs = new RoomService();

        try(Scanner sc = new Scanner(System.in)){
            System.out.print("1. 회원가입    2. 로그인    3. 나가기");
            System.out.print("번호 입력   ==>>>  ");
            String selectNum = sc.nextLine();
            switch(selectNum){
                case "1": break; // 각자
                case "2":
                    rs.makeRoomList(rs.askRoomInfo(sc));
                    break; // 각자
                case "3": break; // 각자
                default: break; // 각자
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
