package shopping.main.millions.controller.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shopping.main.millions.dto.product.ProductDto;
import shopping.main.millions.dto.sales.GoodsImageDto;
import shopping.main.millions.dto.sales.GoodsModifyDto;
import shopping.main.millions.dto.sales.GoodsSaveDto;
import shopping.main.millions.dto.sales.StockSaveDto;
import shopping.main.millions.jwt.TokenProvider;
import shopping.main.millions.service.product.ProductService;
import shopping.main.millions.service.sales.GoodsSaveService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final GoodsSaveService goodsSaveService;
    private final TokenProvider tokenProvider;

    //상품 페이지 조회
    @GetMapping("/list")
    public ResponseEntity<List<ProductDto>> viewProductListByPage (@PageableDefault(page= 0, size = 10, sort = "productId", direction = Sort.Direction.ASC)
                                                                   Pageable pageable){
        List<ProductDto> products = productService.getProductsByPage(pageable);
        return ResponseEntity.ok(products);
    }

    //상품 상세 조회
    @GetMapping("/detail/{productId}")
    public ResponseEntity<?> searchProductById(@PathVariable Long productId) {
        return productService.findProductById(productId);
    }

    //카테고리별 조회
    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<ProductDto>> searchProductByCategory(@PageableDefault(page= 0, size = 10, sort = "productId", direction = Sort.Direction.ASC)
                                                                  Pageable pageable, @PathVariable("categoryName") String categoryName) {
        List<ProductDto> products = productService.getProductsByCategory(pageable,categoryName);
        return ResponseEntity.ok(products);
    }

    //상품 등록
    @PostMapping(value = {"" , "/"})
    public ResponseEntity<Map<String, String>> goodsEdit(@RequestPart("imageFile") List<MultipartFile> imageFile,
                                                         @RequestPart("goodsSaveDto") GoodsSaveDto goodsSaveDto ,HttpServletRequest request) {

        String header = request.getHeader("X-AUTH-TOKEN");
        String userId = tokenProvider.getUserPk(header);
        System.out.println("imageFile = " + imageFile + ", goodsSaveDto = " + goodsSaveDto);

        return goodsSaveService.editItem(goodsSaveDto, imageFile, userId);
    }
    //상품 수정
    @PutMapping("/{product_id}")
    public ResponseEntity<Map<String, String>> goodsUpdate(@PathVariable("product_id") Long productId,
                                                           @RequestPart GoodsModifyDto modifyDto,
                                                           @RequestPart StockSaveDto stockSaveDto,
                                                           HttpServletRequest request) {
        String header = request.getHeader("X-AUTH-TOKEN");
        String userId = tokenProvider.getUserPk(header);
       return goodsSaveService.modifyItem(modifyDto, productId, userId, stockSaveDto);

    }

    //판매자 상품 전체 조회
    @GetMapping(value = {"" , "/"})
    public ResponseEntity<?> goodsSearchList(HttpServletRequest request){

        String header = request.getHeader("X-AUTH-TOKEN");
        String userId = tokenProvider.getUserPk(header);
    return goodsSaveService.findGoods(userId);
    }
}


