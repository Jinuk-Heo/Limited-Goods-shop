package com.example.limitedgoodsshop.domain;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class Order {
    private Long id;                // 주문 고유 번호 (1, 2, 3...)
    private Long userId;            // 어떤 회원이 주문했는지 (User의 id와 연결)
    private Long productId;         // 어떤 상품을 주문했는지 (Product의 id와 연결)
    private int orderQuantity;      // 주문한 수량
    private int totalPrice;         // 총 결제 금액 (가격 * 수량)
    private LocalDateTime orderTime;// 주문한 날짜와 시간
    private String paymentStatus;   // 결제 상태 (READY = 결제대기, PAID = 결제완료, CANCEL = 주문취소)
}