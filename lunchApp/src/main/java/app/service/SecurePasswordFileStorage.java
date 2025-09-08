package app.service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/*
 * 비밀번호 암호화 및 파일 저장을 담당하는 클래스
 */
public class SecurePasswordFileStorage {
    private static final String PASSWORD_FILE = "passwords.dat";
    Map<String, String> passwordMap;

    /**
     * SecurePasswordFileStorage 생성자
     * 기존 파일에서 비밀번호 정보를 로드합니다.
     */
    public SecurePasswordFileStorage() {
        this.passwordMap = new HashMap<>();
    }

    /**
     * 비밀번호를 SHA-512로 암호화
     * @param password 암호화할 비밀번호
     * @return 암호화된 비밀번호 (16진수 문자열)
     */
    private String encryptPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("암호화 알고리즘을 찾을 수 없습니다.", e);
        }
    }

    /**
     * 사용자의 비밀번호를 암호화하여 저장
     * @param userId 사용자 아이디
     * @param password 저장할 비밀번호
     */
    public void savePassword(String userId, String password) {
        String encryptedPassword = encryptPassword(password);
        passwordMap.put(userId, encryptedPassword);
        savePasswordsToFile();
    }

    /**
     * 입력된 비밀번호가 저장된 비밀번호와 일치하는지 검증
     * @param userId 사용자 아이디
     * @param password 검증할 비밀번호
     * @return 일치하면 true, 그렇지 않으면 false
     */
    public boolean verifyPassword(String userId, String password) {
        String storedPassword = passwordMap.get(userId);
        if (storedPassword == null) {
            return false;
        }
        String encryptedInputPassword = encryptPassword(password);
        return storedPassword.equals(encryptedInputPassword);
    }

    /**
     * 파일에서 암호화된 비밀번호 정보를 로드
//     */
//    private void loadPasswordsFromFile() {
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PASSWORD_FILE))) {
//            passwordMap = Collections.unmodifiableMap((Map<String, String>) ois.readObject());
//        } catch (FileNotFoundException e) {
//            // 파일이 없으면 빈 맵으로 시작
//            passwordMap = new HashMap<>();
//        } catch (IOException | ClassNotFoundException e) {
//            System.err.println("비밀번호 파일 로드 중 오류 발생: " + e.getMessage());
//            passwordMap = new HashMap<>();
//        }
//    }

//    public void loadPasswordsFromFile(String userId, String password) {
//        String encryptedPassword = encryptPassword(password);
//        Map<String, String> newMap = new HashMap<>(passwordMap);
//        newMap.put(userId, encryptedPassword);
////        passwordMap = Collections.unmodifiableMap(newMap);
//        passwordMap =  new HashMap<>(passwordMap);
//        savePasswordsToFile();
//    }
    public void loadPasswordsFromFile(String userId, String password) {
        if (password == null) {
            System.out.println("경고: password가 null입니다. 저장하지 않습니다.");
            return;
        }
        String encryptedPassword = encryptPassword(password);
        passwordMap.put(userId, encryptedPassword);
        savePasswordsToFile();
    }

    /**
     * 암호화된 비밀번호 정보를 파일에 저장
     */
    private void savePasswordsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PASSWORD_FILE))) {
            oos.writeObject(passwordMap);
        } catch (IOException e) {
            System.err.println("비밀번호 파일 저장 중 오류 발생: " + e.getMessage());
        }
    }
}