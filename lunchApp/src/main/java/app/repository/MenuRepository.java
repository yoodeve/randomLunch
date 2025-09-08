package app.repository;

import app.domain.Menu;
import app.domain.MenuWinsComparator;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

// MenuRepository class는 MenuService class 메서드와 'Menu.txt' 파일을 연결해주는 클래스이다.
// MenuService 객체는 MenuRepository 객체(의 주소)를 instance variable로 가지고 있으며,
// MenuRepository class의 메서드들을 사용하여 "Menu.txt" 파일에 저장되어 있는 데이터를
// MenuRepository 객체가 instance variable로 가지고 있는 InputStream들과 OutputStream들을 통해 읽고, 덮어쓴다.
public class MenuRepository {
    private final String fileName = "src/main/java/data/Menu.txt"; // Menu 객체들을 ArrayList로 저장하고 있는 .txt
    private static final List<Menu> defaultMenus = new ArrayList<>(Arrays.asList(new Menu("짜장면"), new Menu("짬뽕"), new Menu("탕수육"))); // default Menu 객체 ArrayList
    private FileInputStream fis;
    private BufferedInputStream bis;
    private ObjectInputStream ois;
    private FileOutputStream fos;
    private BufferedOutputStream bos;
    private ObjectOutputStream oos;

    //"Menu.txt"에 저장되어 있는 ArrayList<Menu>를 불러와 반환하는 메서드 
    public List<Menu> loadMenus() {
        List<Menu> menus = new ArrayList<>();

        if (isEmpty()) {
            //"Menu.txt"이 비어있다면 defaultMenus의 복사본을 만들어 InputStream을 통해 해당 파일에 저장한다.
            saveMenus(new ArrayList<>(defaultMenus));
        }
        try {
            fis = new FileInputStream(fileName);
            bis = new BufferedInputStream(fis);
            ois = new ObjectInputStream(bis);
            //객체 역직렬화
            menus = (List<Menu>) ois.readObject();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally{
            try {
                ois.close();
                bis.close();
                fis.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return menus;
    }
    //"Menu.txt"에 parameter로 받은 ArrayList<Menu> menus를 저장한다.
    // 저장하기전에 .reorderMenus() 메소드를 활용하여 우승 횟수가 lowest to highest 순으로 재배열 된다.
    public void saveMenus(List<Menu> menus) {
        try {
            fos = new FileOutputStream(fileName);
            bos = new BufferedOutputStream(fos);
            oos = new ObjectOutputStream(bos);

            // lowest to highest 순서로 재배열
            List<Menu> reorderedMenus = reorderMenus(menus);
            oos.writeObject(reorderedMenus);


        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally{
            try {
                oos.close();
                bos.close();
                fos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //.txt파일의 내용이 비어있는지 확인하는 helper 메서드
    private boolean isEmpty() {
        File f = new File(fileName);
        return (f.length() == 0);
    }

    //Menu 객체의 우승 횟수가 lowest to highest 순으로 재배열 된다.
    private List<Menu> reorderMenus(List<Menu> menus) {
        Collections.sort(menus, new MenuWinsComparator());
        return menus;
    }
    // 기본 생성자만 사용할 것이기 때문에, compiler가 기본 생성자를 자동으로 생성하므로 생성자 메소드를 작성하지 않는다.

}
