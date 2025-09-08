package app.repository;

import app.domain.User;
import java.io.*;
import java.util.*;

/**
 * 사용자 데이터의 파일 기반 영속성을 담당하는 Repository 클래스
 */
public class UserRepository {
    private final String fileName = "User.txt";

    /**
     * 파일에서 사용자 목록을 로드
     * @return 사용자 목록
     */
    public List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        File file = new File(fileName);

        if (!file.exists() || file.length() == 0) {
            return users; // 빈 리스트 반환
        }

        try (FileInputStream fis = new FileInputStream(fileName);
             BufferedInputStream bis = new BufferedInputStream(fis);
             ObjectInputStream ois = new ObjectInputStream(bis)) {

            users = (List<User>) ois.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("사용자 파일을 찾을 수 없습니다. 새로운 파일이 생성됩니다.");
        } catch (EOFException e) {
            // 파일이 비어있는 경우
            System.out.println("사용자 파일이 비어있습니다.");
        } catch (Exception e) {
            System.out.println("사용자 데이터 로드 중 오류: " + e.getMessage());
        }
        return users;
    }

    /**
     * 사용자 목록을 파일에 저장
     * @param users 저장할 사용자 목록
     */
    public void saveUsers(List<User> users) {
        try (FileOutputStream fos = new FileOutputStream(fileName);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {

            oos.writeObject(users);

        } catch (Exception e) {
            System.out.println("사용자 데이터 저장 중 오류: " + e.getMessage());
        }
    }

    /**
     * 새 사용자 추가
     * @param user 추가할 사용자
     * @return 성공 여부 (중복 ID 시 false)
     */
    public boolean addUser(User user) {
        List<User> users = loadUsers();

        // 중복 ID 체크
        boolean exists = users.stream()
                .anyMatch(u -> u.getId().equals(user.getId()));

        if (!exists) {
            users.add(user);
            saveUsers(users);
            return true;
        }
        return false;
    }

    /**
     * ID로 사용자 검색
     * @param id 검색할 사용자 ID
     * @return 사용자 객체 또는 null
     */
    public User findUserById(String id) {
        return loadUsers().stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * 사용자 정보 업데이트
     * @param updatedUser 업데이트할 사용자 정보
     * @return 성공 여부
     */
    public boolean updateUser(User updatedUser) {
        List<User> users = loadUsers();

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(updatedUser.getId())) {
                users.set(i, updatedUser);
                saveUsers(users);
                return true;
            }
        }
        return false;
    }

    /**
     * 사용자 삭제
     * @param userId 삭제할 사용자 ID
     * @return 성공 여부
     */
    public boolean deleteUser(String userId) {
        List<User> users = loadUsers();
        boolean removed = users.removeIf(user -> user.getId().equals(userId));

        if (removed) {
            saveUsers(users);
        }
        return removed;
    }
}