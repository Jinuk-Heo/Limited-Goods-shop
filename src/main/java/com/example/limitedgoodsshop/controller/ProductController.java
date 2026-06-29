// 고객 상품 조회 점원
package com.example.limitedgoodsshop.controller;

import com.example.limitedgoodsshop.domain.Product;
import com.example.limitedgoodsshop.service.ProductService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 전체 상품 목록 보기
    @GetMapping
    public List<Product> list() {
        return productService.findProducts();
    }

    // 특정 상품 상세 보기 (한정판 타이머 상태 확인 포함)
    @GetMapping("/{productId}")
    public String detail(@PathVariable("productId") Long productId) {
        Product product = productService.findOne(productId);
        if (product == null) {
            return "❌ 존재하지 않는 상품입니다.";
        }

        String message = "🎁 상품명: " + product.getName() + " | 가격: " + product.getPrice() + "원\n";
        message += "📦 남은 재고: " + product.getStockQuantity() + "개\n";

        // 오픈 타이머 및 상태에 따른 화면 안내 분리
        if ("READY".equals(product.getStatus())) {
            message += "⏰ [상태: 오픈 준비 중] 설정된 오픈 시간(" + product.getOpenTime() + ") 전이므로 주문 버튼이 비활성화됩니다.";
        } else if ("SOLDOUT".equals(product.getStatus())) {
            message += "🚫 [상태: 품절] 이 상품은 매진되었습니다. 주문 버튼이 막힙니다.";
        } else {
            message += "⚡ [상태: 판매 중] 주문이 가능합니다! [주문하기 버튼 활성화]";
        }

        return message;
    }
}