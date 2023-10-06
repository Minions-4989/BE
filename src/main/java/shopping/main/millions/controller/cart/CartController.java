package shopping.main.millions.controller.cart;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopping.main.millions.dto.cart.CartProductDto;
import shopping.main.millions.jwt.TokenProvider;
import shopping.main.millions.service.cart.CartService;

import javax.servlet.http.HttpServletRequest;


@Log4j2
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final TokenProvider tokenProvider;

    //장바구니 조회
    @GetMapping("/")
    public ResponseEntity<?> viewCartProductList (HttpServletRequest request){
        String header = request.getHeader("X-AUTH-TOKEN");
        String userId = tokenProvider.getUserPk(header);
        return cartService.CartProductList(userId);
    }
}
