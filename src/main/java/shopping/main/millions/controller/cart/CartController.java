package shopping.main.millions.controller.cart;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopping.main.millions.dto.cart.CartAddDto;
import shopping.main.millions.dto.cart.CartProductDto;
import shopping.main.millions.jwt.TokenProvider;
import shopping.main.millions.service.cart.CartService;

import javax.servlet.http.HttpServletRequest;
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
        return cartService.CartProductList(userId);
    }
}
