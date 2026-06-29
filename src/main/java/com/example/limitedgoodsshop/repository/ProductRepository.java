// 상품 창고 설계도
package com.example.limitedgoodsshop.repository;

import com.example.limitedgoodsshop.domain.Product;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductRepository {
    // 임시로 상품 데이터를 저장할 메모리 공간 (상품번호, 상품객체)
    private static final Map<Long, Product> store = new HashMap<>();
    private static long sequence = 0L; // 상품 번호를 1씩 자동으로 올려주는 변수

    // 굿즈 상품 저장하기 (등록 및 수정)
    public Product save(Product product) {
        if (product.getId() == null) {
            product.setId(++sequence);
        }
        store.put(product.getId(), product);
        return product;
    }

    // 상품 고유 번호로 찾기
    public Product findById(Long id) {
        return store.get(id);
    }

    // 전체 상품 목록 보기 (메인 화면 및 관리자 화면용)
    public List<Product> findAll() {
        return new ArrayList<>(store.values());
    }

    // 창고 비우기 (나중에 테스트 코드 짤 때 유용하게 쓰입니다)
    public void clearStore() {
        store.clear();
    }
}