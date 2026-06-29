// 메인 대문 점원
package com.example.limitedgoodsshop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    /**
     * 메인 화면
     * 우측 상단에 로그인 상태에 따라 다른 버튼이 보이도록 상태값을 넘겨줍니다.
     */
    @GetMapping("/")
    public String home(@RequestParam(value = "userId", required = false) Long userId) {
        if (userId == null) {
            return "🏠 [한정판 굿즈 쇼핑몰 메인 화면] 현재 비로그인 상태입니다. 우측 상단: [로그인 버튼]";
        }
        return "🏠 [한정판 굿즈 쇼핑몰 메인 화면] 회원 번호 " + userId + "님 환영합니다! 우측 상단: [마이페이지] [로그아웃]";
    }
}