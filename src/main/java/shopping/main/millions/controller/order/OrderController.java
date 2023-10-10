package shopping.main.millions.controller.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopping.main.millions.dto.cart.CartProductDto;
import shopping.main.millions.jwt.TokenProvider;
import shopping.main.millions.service.order.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final TokenProvider tokenProvider;


    @GetMapping("")
    public ResponseEntity<?> getCartItems(
            //@RequestParam("userId") String userId,
            HttpServletRequest request) {
        // 주문 서비스의 메서드를 호출하여 장바구니 목록을 가져온다.
        String header = request.getHeader("X-AUTH-TOKEN");
        String userId = tokenProvider.getUserPk(header);
        //List<CartProductDto> cartProductDtoList = orderService.getClass(userId);
        return ResponseEntity.ok(
                //cartProductDtoList
                null);
    }
}

