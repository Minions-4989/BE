package shopping.main.millions.service.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import shopping.main.millions.dto.order.OrderDto;
import shopping.main.millions.entity.order.OrderEntity;
import shopping.main.millions.entity.order.OrderPaymentEntity;
import shopping.main.millions.entity.order.UserOrderEntity;
import shopping.main.millions.repository.order.OrderPaymentRepository;
import shopping.main.millions.repository.order.OrderRepository;
import shopping.main.millions.repository.order.UserOrderRepository;

@Log4j2
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserOrderRepository userOrderRepository;
    private final OrderPaymentRepository orderPaymentRepository;

    // 다른서비스가 아니고 그 저희 ENtity가 총 3개 있자나여 쟤네를 저장하는 메소드를 각각 만드는게 어떨까 싶어서요. 총 3개
    public ResponseEntity<?> saveUser(OrderDto orderDto) {
        // Entity 변환 후 저장
        UserOrderEntity userOrderEntity = UserOrderEntity.builder()
                .telNumber(orderDto.getTelNumber())
                .userName(orderDto.getUserName())
                .address(orderDto.getAddress())
                .addressDetail(orderDto.getAddressDetail())
                .addressZipcode(orderDto.getAddressZipcode())
                .build();
        //JPA의 JpaRepository 매개변수, 인터페이스 명이 일치한 지 확인하면 된다. bb
        userOrderRepository.save(userOrderEntity);

        return ResponseEntity.ok("저장 완료");
    }

    public ResponseEntity<?> saveUserPayment(OrderDto orderDto){
        OrderPaymentEntity orderPaymentEntity = OrderPaymentEntity.builder()
                .cardNum(orderDto.getCardNum())
                .cardCvc(orderDto.getCardCvc())
                .cardExpirationPeriod(orderDto.getCardExpirationPeriod())
                .totalPrice(orderDto.getTotalPrice())
                .build();
        orderPaymentRepository.save(orderPaymentEntity);
        return ResponseEntity.ok("저장 완료");

    }

//    public ResponseEntity<?> saveOrder(OrderDto orderDto){
//        OrderEntity orderEntity = OrderEntity.builder()
//                .productEntity()
//                .build();
//    }


}
//근데 준용님 이거 말고도 다른 서비스 만들어야하는게 몬데여,,넹 아 그럼 준용님이 유저오더에대한 인포를 만드는 거고 맞습니다!
//엔