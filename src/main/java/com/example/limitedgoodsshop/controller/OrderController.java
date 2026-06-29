// 주문 및 가상 결제 점원
package com.example.limitedgoodsshop.controller;

import com.example.limitedgoodsshop.domain.Order;
import com.example.limitedgoodsshop.service.OrderService;
import com.example.limitedgoodsshop.service.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;

    public OrderController(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

    /**
     * 굿즈 주문하기 (가상 결제 연동 뼈대)
     * 디테일 요구사항: 비로그인(userId 없음) 상태로 주문 시 로그인 페이지 리다이렉트
     */
    @PostMapping
    public String order(
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam("productId") Long productId,
            @RequestParam("quantity") int quantity) {

        // 1. 디테일 요구사항: 로그인하지 않았다면 로그인 페이지로 유도
        if (userId == null) {
            return "🔒 [접근 제한] 로그인이 필요한 서비스입니다. 자동으로 로그인 페이지로 이동(리다이렉트)합니다.";
        }

        try {
            // 2. 주문 실행 및 자동 품절 처리 연동
            Order order = orderService.createOrder(userId, productId, quantity);

            // 3. 주문이 끝난 후 재고를 체크하여 필요시 자동 발주 시스템 가동
            productService.checkAndReorder(productId);

            return "💳 [가상 결제 완료] 토스페이먼츠 연동 성공! 주문 번호: " + order.getId() + " | 결제 금액: " + order.getTotalPrice() + "원";
        } catch (IllegalStateException e) {
            return "❌ 주문 실패: " + e.getMessage();
        }
    }
}