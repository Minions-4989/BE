package shopping.main.millions.controller.cart;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopping.main.millions.dto.cart.CartProductDto;
import shopping.main.millions.service.cart.CartService;

import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    //상품 장바구니에 추가
//    @PostMapping("/add")
//    public ResponseEntity<Map<String,String>> addProductToCart(@RequestBody CartProductDto cartProductDto){
//
//        return cartService.addCart(cartProductDto,);
//    }

    //장바구니 조회
    @GetMapping("/")
    public ResponseEntity<CartProductDto> viewCart(){
        return  null;
    }
}
