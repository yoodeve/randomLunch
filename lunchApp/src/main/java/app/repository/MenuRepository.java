package app.repository;

import app.domain.Menu;
import app.domain.MenuWinsComparator;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class MenuRepository {
    // RoomRepository class 주석 참고
    private final String fileName = "Menu.txt";
    private static final List<Menu> defaultMenus = new ArrayList<>(Arrays.asList(new Menu("짜장면"), new Menu("짬뽕"), new Menu("탕수육")));
    private FileInputStream fis;
    private BufferedInputStream bis;
    private ObjectInputStream ois;
    private FileOutputStream fos;
    private BufferedOutputStream bos;
    private ObjectOutputStream oos;

    public List<Menu> loadMenus() {

        List<Menu> menus = new ArrayList<>();

        if (isEmpty()) {

            saveMenus(new ArrayList<>(defaultMenus));
        }
        try {
            fis = new FileInputStream(fileName);
            bis = new BufferedInputStream(fis);
            ois = new ObjectInputStream(bis);

            menus = (List<Menu>) ois.readObject();
            ois.close();
            bis.close();
            fis.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return menus;
    }

    public void saveMenus(List<Menu> menus) {
        try {
            fos = new FileOutputStream(fileName);
            bos = new BufferedOutputStream(fos);
            oos = new ObjectOutputStream(bos);

            List<Menu> reorderedMenus = reorderMenus(menus);
            oos.writeObject(reorderedMenus);

            oos.close();
            bos.close();
            fos.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private boolean isEmpty() {
        File f = new File(fileName);
        return (f.length() == 0);
    }

    private List<Menu> reorderMenus(List<Menu> menus) {
        Collections.sort(menus, new MenuWinsComparator());
        return menus;
    }


    // 기본 생성자만 사용할 것이기 때문에, compiler가 기본 생성자를 자동으로 생성하므로 생성자 메소드를 작성하지 않는다.

}
