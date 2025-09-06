package app.repository;

import app.domain.Menu;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MenuRepository {
    // RoomRepository class 주석 참고
    private final String fileName = "Menu.txt";
    private static final List<Menu> defaultMenus = List.of(new Menu("짜장면"), new Menu("짬뽕"), new Menu("탕수육"));
    private FileInputStream fis;
    private BufferedInputStream bis;
    private ObjectInputStream in;

    public List<Menu> loadMenus() {

        List<Menu> menus = new ArrayList<>();
        try {
            fis = new FileInputStream(fileName);
            bis = new BufferedInputStream(fis);
            in = new ObjectInputStream(bis);

            menus = (List<Menu>) in.readObject();
            in.close();
            bis.close();
            fis.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return menus;
    }


    // 기본 생성자만 사용할 것이기 때문에, compiler가 기본 생성자를 자동으로 생성하므로 생성자 메소드를 작성하지 않는다.

}
