package shopping.main.millions.controller.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopping.main.millions.dto.order.OrderDto;
import shopping.main.millions.dto.order.OrderGetDto;
import shopping.main.millions.entity.order.UserOrderEntity;
import shopping.main.millions.jwt.TokenProvider;
import shopping.main.millions.service.order.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final TokenProvider tokenProvider;

    // 장바구니에서 선택된 상품 불러오기
    @GetMapping("/list")
    public ResponseEntity<?> getCartItems(@RequestBody OrderGetDto orderGetDto, HttpServletRequest request) {
        // 장바구니 선택 상품목록을 dto로 받는다
        String header = request.getHeader("X-AUTH-TOKEN");
        String userId = tokenProvider.getUserPk(header);
        return ResponseEntity.ok(orderGetDto);
    }

    // 수령인, 결제 관련 정보 저장
    @PostMapping("/payment")
    public ResponseEntity<?> orderInfo (@RequestBody OrderDto orderDto, HttpServletRequest request) {
        String header = request.getHeader("X-AUTH-TOKEN");
        String userId = tokenProvider.getUserPk(header);
        UserOrderEntity userOrderEntity = orderService.saveUser(orderDto, userId);
        return orderService.saveUserPayment(orderDto , userOrderEntity);
    }

    // 장바구니 선택 상품 주문
    @PutMapping("/payment")
    public ResponseEntity<?> orderItems(@RequestBody OrderDto orderDto, HttpServletRequest request) {
        String header = request.getHeader("X-AUTH-TOKEN");
        String userId = tokenProvider.getUserPk(header);
        return  orderService.orderProcess(orderDto,userId);
    }
}