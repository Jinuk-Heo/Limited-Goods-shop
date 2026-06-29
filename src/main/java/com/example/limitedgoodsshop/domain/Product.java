package com.example.limitedgoodsshop.domain;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class Product {
    private Long id;                // 상품 고유 번호 (1, 2, 3...)
    private String name;            // 굿즈 상품 이름 (예: 한정판 키링)
    private int price;              // 상품 가격
    private int stockQuantity;      // 현재 남은 재고 수량 (실전 동시성 테스트의 핵심!)
    private LocalDateTime openTime; // 한정판 오픈 시간 (이 시간 전에는 주문 불가)
    private String status;          // 상품 상태 (READY = 오픈준비중, SALE = 판매중, SOLDOUT = 품절)
}