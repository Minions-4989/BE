package shopping.main.millions.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    //상품 전체 조회
    @GetMapping
    public ResponseEntity<List<ProductDto>> viewProductList() {
        return productService.findAll();
    }

    //상품 상세 조회
    @GetMapping("/{product_id}")
    public ResponseEntity<ProductDto> searchProductById(@PathVariable Long productId) {
        return productService.findProductById(productId);
    }

}
