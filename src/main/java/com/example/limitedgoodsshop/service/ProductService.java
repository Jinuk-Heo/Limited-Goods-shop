// 상품 및 발주 매니저
package com.example.limitedgoodsshop.service;

import com.example.limitedgoodsshop.domain.Product;
import com.example.limitedgoodsshop.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 관리자의 새 굿즈 상품 등록
    public Product registerProduct(Product product) {
        // 처음 등록할 때는 오픈 준비 중(READY) 상태로 시작
        product.setStatus("READY");
        return productRepository.save(product);
    }

    // 실전용 발주 시스템 로직
    // 특정 상품의 재고를 확인하고 부족하면 관리자 알림 및 자동 충전
    public void checkAndReorder(Long productId) {
        Product product = productRepository.findById(productId);
        if (product == null) return;

        // 재고가 5개 미만으로 떨어지면 시스템 발주 진행
        if (product.getStockQuantity() < 5) {
            System.out.println("⚠️ [시스템 발주 알림] " + product.getName() + "의 재고가 " + product.getStockQuantity() + "개로 부족합니다! 자동 발주를 진행합니다.");

            // 가상으로 재고를 100개 채워 넣음
            product.setStockQuantity(product.getStockQuantity() + 100);
            product.setStatus("SALE"); // 품절이었다면 판매중으로 복구
            productRepository.save(product);
        }
    }

    // 전체 상품 보기
    public List<Product> findProducts() {
        return productRepository.findAll();
    }

    // 상품 단건 조회
    public Product findOne(Long productId) {
        return productRepository.findById(productId);
    }
}