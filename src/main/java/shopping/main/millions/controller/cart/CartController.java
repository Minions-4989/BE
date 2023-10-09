package shopping.main.millions.controller.cart;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopping.main.millions.dto.cart.CartAddDto;
import shopping.main.millions.dto.cart.CartProductDto;
import shopping.main.millions.entity.cart.CartProductEntity;
import shopping.main.millions.jwt.TokenProvider;
import shopping.main.millions.service.cart.CartService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@Log4j2
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final TokenProvider tokenProvider;

    //상품 장바구니에 추가
    @PutMapping("/new")
    public ResponseEntity<Map<String,String>> addProductToCart(@RequestBody CartAddDto cartAddDto,HttpServletRequest request){
        String header = request.getHeader("X-AUTH-TOKEN");
        String userId = tokenProvider.getUserPk(header);
        cartAddDto.setUserId(Long.valueOf(userId));
        return cartService.addCart(cartAddDto);
    }

    //장바구니 조회
    @GetMapping("/list")
    public ResponseEntity<?> viewCartProductList (HttpServletRequest request){
        String header = request.getHeader("X-AUTH-TOKEN");
        String userId = tokenProvider.getUserPk(header);
        return cartService.cartProductList(userId);
    }

    // 장바구니 수량 수정
    @PatchMapping("/{cartProductId}")
    public ResponseEntity<?> updateCartCount (
            @PathVariable Long cartProductId,
            @RequestParam String action,
            HttpServletRequest request) {
        String header = request.getHeader("X-AUTH-TOKEN");
        if ("increase".equals(action)) return cartService.increaseQuantity(cartProductId);
        else if ("decrease".equals(action)) return cartService.decreaseQuantity(cartProductId);
        else return ResponseEntity.badRequest().body("잘못된 접근입니다.");
    }

//    @PostMapping("/order")

    //장바구니 속 상품 삭제
    @DeleteMapping("/cartProduct")
    public ResponseEntity<Map<String,String>> deleteCartProduct(@RequestParam List<CartProductEntity> cartProductEntityList, HttpServletRequest request){
        String header = request.getHeader("X-AUTH-TOKEN");
        String userId = tokenProvider.getUserPk(header);
        return cartService.deleteProductList(cartProductIdList, userId);
    }

    //장바구니 주문
//    @PostMapping("/order")
//    public ResponseEntity<>

}
