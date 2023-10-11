package shopping.main.millions.controller.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopping.main.millions.dto.order.OrderDto;
import shopping.main.millions.dto.order.OrderGetDto;
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

    // Get - 장바구니에서 선택한 상품 정보 불러오기
    @GetMapping("/list")
    public ResponseEntity<?> getCartItems(@RequestBody OrderGetDto orderGetDto, HttpServletRequest request) {
        // 장바구니 선택 상품목록을 dto로 받는다
        String header = request.getHeader("X-AUTH-TOKEN");
        String userId = tokenProvider.getUserPk(header);
        return ResponseEntity.ok(orderGetDto);
    }

    // Post - 배송지, 결제정보 등 저장
    @PostMapping("/payment")
    public ResponseEntity<?> orderInfo (@RequestBody OrderDto orderDto, HttpServletRequest request) {
        String header = request.getHeader("X-AUTH-TOKEN");
        String userId = tokenProvider.getUserPk(header);
        orderService.saveUser(orderDto);
        orderService.saveUserPayment(orderDto);
        return null;
    }

    // PutMapping - dto받아서 거기상에 있는 상품 리스트 통해,
    // 구매상품 OrderEntity에 저장, Stock 변경, CartProduct 삭제

    // 느낌표 하나만 붙여주세요ㄴ 네~!
}