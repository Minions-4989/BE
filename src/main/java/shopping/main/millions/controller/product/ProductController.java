package shopping.main.millions.controller.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopping.main.millions.dto.product.ProductDto;
import shopping.main.millions.entity.product.ProductEntity;
import shopping.main.millions.service.product.ProductService;

@Log4j2
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    //상품 페이지 조회
    @GetMapping("/")
    public ResponseEntity<Page<ProductDto>> viewProductListByPage (@PageableDefault(page= 0, size = 10, sort = "productId", direction = Sort.Direction.ASC)
                                                                   Pageable pageable){
        Page<ProductDto> products = productService.getProductsByPage(pageable);
        return ResponseEntity.ok(products);
    }

    //상품 상세 조회
    @GetMapping("/detail/{productId}")
    public ResponseEntity<ProductDto> searchProductById(@PathVariable Long productId) {
        return productService.findProductById(productId);
    }

    //카테고리별 조회
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<ProductDto>> searchProductByCategory(@PageableDefault(page= 0, size = 10, sort = "productId", direction = Sort.Direction.ASC)
                                                                  Pageable pageable, @PathVariable Long categoryId) {
        Page<ProductDto> products = productService.getProductsByCategory(pageable,categoryId);
        return ResponseEntity.ok(products);
    }

}
