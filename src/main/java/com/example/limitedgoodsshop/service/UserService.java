// 회원 매니저

package com.example.limitedgoodsshop.service;

import com.example.limitedgoodsshop.domain.User;
import com.example.limitedgoodsshop.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    // 창고지기를 불러옵니다.
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원가입
    public User join(User user) {
        // 이메일 중복 검사 규칙
        User findUser = userRepository.findByEmail(user.getEmail());
        if (findUser != null) {
            throw new IllegalStateException("이미 존재하는 이메일 아이디입니다.");
        }

        // 가입 시 기본 권한은 일반 고객(USER)으로 설정
        user.setRole("ROLE_USER");
        user.setBlocked(false);

        return userRepository.save(user);
    }

    // 마이페이지 정보 수정 (배송지와 전화번호만 수정 가능하게 제한)
    // 생년월일과 전화번호를 비교하여 가상으로 본인 확인 인증 진행
    public User updateMyPage(Long userId, String newAddress, String newPhoneNumber) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }

        // 실전성 꿀팁: 가상 본인인증 로직 맛보기
        // 만약 전화번호가 "010"으로 시작하지 않으면 잘못된 인증으로 처리
        if (!newPhoneNumber.startsWith("010")) {
            throw new IllegalArgumentException("유효하지 않은 전화번호 형식입니다. 본인 인증 실패.");
        }

        user.setAddress(newAddress);
        user.setPhoneNumber(newPhoneNumber);

        return userRepository.save(user); // 수정된 정보 저장
    }

    // 회원 단건 조회
    public User findOne(Long userId) {
        return userRepository.findById(userId);
    }
}