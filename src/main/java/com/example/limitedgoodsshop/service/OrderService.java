// 주문 및 자동 품절 매니저
package com.example.limitedgoodsshop.service;

import com.example.limitedgoodsshop.domain.Order;
import com.example.limitedgoodsshop.domain.Product;
import com.example.limitedgoodsshop.repository.OrderRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService; // 상품 상태를 바꾸기 위해 상품 매니저를 불러옵니다.

    public OrderService(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    /**
     * 주문하기 (실전성 핵심 코드)
     * 여기에 나중에 대량의 트래픽이 동시에 몰릴 때 동시성 에러를 잡아내는 디버깅을 할 것입니다.
     */
    public Order createOrder(Long userId, Long productId, int quantity) {
        // 1. 상품 조회
        Product product = productService.findOne(productId);
        if (product == null) {
            throw new IllegalArgumentException("상품이 존재하지 않습니다.");
        }

        // 2. 한정판 판매 상태 검사
        if ("READY".equals(product.getStatus())) {
            throw new IllegalStateException("아직 상품 오픈 시간이 되지 않았습니다.");
        } else if ("SOLDOUT".equals(product.getStatus())) {
            throw new IllegalStateException("이미 품절된 상품입니다.");
        }

        // 3. 재고가 충분한지 검사
        if (product.getStockQuantity() < quantity) {
            throw new IllegalStateException("남은 재고가 부족합니다. 현재 재고: " + product.getStockQuantity());
        }

        // 4. [자동 기능] 재고 차감 및 자동 품절 처리
        int remainingStock = product.getStockQuantity() - quantity;
        product.setStockQuantity(remainingStock);

        if (remainingStock == 0) {
            product.setStatus("SOLDOUT"); // 0개가 되는 순간 즉시 품절 상태로 변경!
            System.out.println("🎉 [시스템 자동화] 상품 " + product.getName() + "이 전량 판매되어 자동으로 SOLDOUT 처리되었습니다.");
        }

        // 5. 주문 영수증 생성 및 저장
        Order order = new Order();
        order.setUserId(userId);
        order.setProductId(productId);
        order.setOrderQuantity(quantity);
        order.setTotalPrice(product.getPrice() * quantity);
        order.setOrderTime(LocalDateTime.now());
        order.setPaymentStatus("PAID"); // 가상 결제가 완료되었다고 가정 (추후 토스 API 연동 예정)

        return orderRepository.save(order);
    }
}