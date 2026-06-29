// 주문 내역 창고 설계도

package com.example.limitedgoodsshop.repository;

import com.example.limitedgoodsshop.domain.Order;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class OrderRepository {
    // 임시로 주문 데이터를 저장할 메모리 공간 (주문번호, 주문객체)
    private static final Map<Long, Order> store = new HashMap<>();
    private static long sequence = 0L; // 주문 번호를 1씩 자동으로 올려주는 변수

    // 주문 정보 저장하기 (영수증 발행)
    public Order save(Order order) {
        if (order.getId() == null) {
            order.setId(++sequence);
        }
        store.put(order.getId(), order);
        return order;
    }

    // 주문 고유 번호로 영수증 찾기
    public Order findById(Long id) {
        return store.get(id);
    }

    // 특정 회원의 주문 내역만 모아서 보기 (고객 마이페이지용)
    public List<Order> findByUserId(Long userId) {
        return store.values().stream()
                .filter(order -> order.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    // 전체 주문 목록 보기 (관리자 모니터링 화면용)
    public List<Order> findAll() {
        return new ArrayList<>(store.values());
    }
}