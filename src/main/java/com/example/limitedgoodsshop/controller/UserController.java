// 고객 회원가입 및 마이페이지 점원
package com.example.limitedgoodsshop.controller;

import com.example.limitedgoodsshop.domain.User;
import com.example.limitedgoodsshop.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 요청 처리
    @PostMapping("/join")
    public String join(@RequestBody User user) {
        try {
            User savedUser = userService.join(user);
            return "🎉 회원가입 성공! 가입된 이메일: " + savedUser.getEmail();
        } catch (IllegalStateException e) {
            return "❌ 회원가입 실패: " + e.getMessage();
        }
    }

    // 마이페이지 조회
    @GetMapping("/mypage")
    public User myPage(@RequestParam("userId") Long userId) {
        return userService.findOne(userId);
    }

    // 마이페이지 회원정보 수정 (전화번호, 배송지)
    // 날카롭게 지적해주신 가상 본인확인인증 에러 처리 포함
    @PutMapping("/mypage/update")
    public String updateMyPage(
            @RequestParam("userId") Long userId,
            @RequestParam("address") String address,
            @RequestParam("phoneNumber") String phoneNumber) {
        try {
            userService.updateMyPage(userId, address, phoneNumber);
            return "✏️ 마이페이지 정보가 성공적으로 변경되었습니다.";
        } catch (IllegalArgumentException e) {
            return "❌ 변경 실패 (본인 인증 오류): " + e.getMessage();
        }
    }
}