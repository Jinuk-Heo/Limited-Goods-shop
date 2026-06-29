// repository - 창고지기
// 유저 창고 설계도

package com.example.limitedgoodsshop.repository;

import com.example.limitedgoodsshop.domain.User;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {
    // 임시로 데이터를 저장할 가상의 DB 공간 (메모리 지도)
    private static final Map<Long, User> store = new HashMap<>();
    private static long sequence = 0L; // 회원 번호를 1씩 자동으로 올려주는 변수

    // 회원 정보 저장하기
    public User save(User user) {
        if (user.getId() == null) {
            user.setId(++sequence);
        }
        store.put(user.getId(), user);
        return user;
    }

    // 회원 고유 번호로 찾기
    public User findById(Long id) {
        return store.get(id);
    }

    // 이메일(아이디)로 회원 찾기 (로그인할 때 사용)
    public User findByEmail(String email) {
        return store.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    // 전체 회원 목록 보기 (관리자 페이지용)
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }
}