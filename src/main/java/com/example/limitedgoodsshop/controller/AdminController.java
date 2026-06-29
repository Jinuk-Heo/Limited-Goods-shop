// 관리자 전용 대시보드 점원
package com.example.limitedgoodsshop.controller;

import com.example.limitedgoodsshop.domain.Product;
import com.example.limitedgoodsshop.domain.User;
import com.example.limitedgoodsshop.service.ProductService;
import com.example.limitedgoodsshop.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final ProductService productService;

    public AdminController(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    // 관리자 메인 대시보드 화면
    @GetMapping("/dashboard")
    public String dashboard() {
        return "🛠️ [관리자 전용 대시보드 시스템] 회원 관리, 상품 등록 및 발주 현황을 모니터링하는 전용 화면입니다.";
    }

    // 관리자 기능: 새로운 한정판 굿즈 등록
    @PostMapping("/products")
    public String registerProduct(@RequestBody Product product) {
        Product registered = productService.registerProduct(product);
        return "📦 [관리자] 새 한정판 굿즈 등록 완료! 상품 ID: " + registered.getId() + ", 이름: " + registered.getName();
    }

    // 관리자 기능: 회원 목록 조회 및 차단 관리 (블랙리스트)
    @PutMapping("/users/{userId}/block")
    public String blockUser(@PathVariable("userId") Long userId, @RequestParam("block") boolean block) {
        User user = userService.findOne(userId);
        if (user == null) return "❌ 존재하지 않는 회원입니다.";

        user.setBlocked(block);
        return "👤 [관리자] 회원 " + user.getName() + "님을 " + (block ? "차단(블랙리스트 등록)" : "차단 해제") + " 하였습니다.";
    }
}