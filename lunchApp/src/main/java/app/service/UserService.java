package app.service;

import app.domain.User;
import app.repository.UserRepository;

public class UserService {
    private UserRepository userRepository;
    
    public UserService() {
        this.userRepository = new UserRepository();
    }
    
    /**
     * 회원가입을 처리합니다.
     */
    public boolean signUp(String id, String password, String name, String bankName, String accountNumber) {
        try {
            // 아이디 중복 확인
            if (userRepository.isIdExists(id)) {
                throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
            }
            
            // 새 사용자 생성 (생성자에서 유효성 검사 수행)
            User newUser = new User(id, password, name, bankName, accountNumber);
            
            // 사용자 저장
            return userRepository.saveUser(newUser);
            
        } catch (Exception e) {
            System.out.println("회원가입 처리 중 오류 발생: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * 로그인을 처리합니다.
     */
    public User login(String id, String password) {
        try {
            return userRepository.authenticate(id, password);
        } catch (Exception e) {
            System.out.println("로그인 처리 중 오류 발생: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * 아이디 중복을 확인합니다.
     */
    public boolean isIdExists(String id) {
        return userRepository.isIdExists(id);
    }
    
    /**
     * 사용자 정보를 조회합니다.
     */
    public User getUserById(String id) {
        return userRepository.findById(id);
    }
}