package shopping.main.millions.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    //상품 전체 조회
//    @GetMapping
//    public ResponseEntity<List<ProductDto>> viewProductList() {
//        return productService.findAll();
//    }

    //상품 페이지 조회
    @GetMapping("/")
    public ResponseEntity<Page<ProductDto>> viewProductListByPage (@PageableDefault(page= 0, size = 10, sort = "productId", direction = Sort.Direction.ASC)
                                                                   Pageable pageable){
        Page<ProductDto> products = productService.getProductsByPage(pageable);
        return ResponseEntity.ok(products);
    }

    //상품 상세 조회
    @GetMapping("/{product_id}")
    public ResponseEntity<ProductDto> searchProductById(@PathVariable Long productId) {
        return productService.findProductById(productId);
    }

}
