// 회원 정보
package com.example.limitedgoodsshop.domain;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class User {
    private Long id;            // 데이터베이스에서 구별할 회원 고유 번호 (1, 2, 3...)
    private String email;       // 로그인할 때 아이디로 쓸 이메일
    private String password;    // 비밀번호 (나중에 가상으로 암호화할 예정)
    private String name;        // 사용자의 실제 이름
    private String phoneNumber; // 전화번호 (마이페이지에서 수정 가능, 가상 인증용)
    private String address;     // 배송지 주소 (마이페이지에서 수정 가능)
    private LocalDate birthDate;// 생년월일 (가입 시 고정, 가상 인증 비교용)
    private String role;        // 권한 등급 (ROLE_USER = 일반고객, ROLE_ADMIN = 관리자)
    private boolean isBlocked;  // 관리자가 차단했는지 여부 (true면 로그인 막힘)
}