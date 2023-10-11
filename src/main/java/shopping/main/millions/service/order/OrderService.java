package shopping.main.millions.service.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import shopping.main.millions.dto.cart.CartProductDto;
import shopping.main.millions.dto.order.OrderDto;
import shopping.main.millions.entity.cart.CartProductEntity;
import shopping.main.millions.entity.order.OrderEntity;
import shopping.main.millions.entity.order.OrderPaymentEntity;
import shopping.main.millions.entity.order.UserOrderEntity;
import shopping.main.millions.entity.product.GoodsStockEntity;
import shopping.main.millions.repository.cart.CartProductRepository;
import shopping.main.millions.repository.order.OrderPaymentRepository;
import shopping.main.millions.repository.order.OrderRepository;
import shopping.main.millions.repository.order.UserOrderRepository;
import shopping.main.millions.repository.sales.GoodsStockRepository;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserOrderRepository userOrderRepository;
    private final OrderPaymentRepository orderPaymentRepository;
    private final GoodsStockRepository goodsStockRepository;
    private final CartProductRepository cartProductRepository;

    public ResponseEntity<?> saveUser(OrderDto orderDto) {
        // Entity 변환 후 저장
        UserOrderEntity userOrderEntity = UserOrderEntity.builder()
                .telNumber(orderDto.getTelNumber())
                .userName(orderDto.getUserName())
                .address(orderDto.getAddress())
                .addressDetail(orderDto.getAddressDetail())
                .addressZipcode(orderDto.getAddressZipcode())
                .userEmail(orderDto.getUserEmail())
                .build();
        userOrderRepository.save(userOrderEntity);

        return ResponseEntity.ok("저장 완료");
    }

    public ResponseEntity<?> saveUserPayment(OrderDto orderDto){
        // Entity 변환 후 저장
        OrderPaymentEntity orderPaymentEntity = OrderPaymentEntity.builder()
                .cardNum(orderDto.getCardNum())
                .cardCvc(orderDto.getCardCvc())
                .cardExpirationPeriod(orderDto.getCardExpirationPeriod())
                .totalPrice(orderDto.getTotalPrice())
                .orderDate(orderDto.getOrderDate())
                .build();
        orderPaymentRepository.save(orderPaymentEntity);

        return ResponseEntity.ok("저장 완료");
    }

    public ResponseEntity<?> orderProcess(OrderDto orderDto) {
        // PutMapping - dto 받아서 거기에 있는 상품 리스트 통해,
        // 구매상품 OrderEntity에 저장, Stock 변경, CartProduct 삭제

        // 체크된 상품 리스트화
        List<CartProductDto> cartProductDtoList = orderDto.getCartProductDtoList();

        for (CartProductDto cartProductDto : cartProductDtoList) {

            // OrderEntity에 저장
            OrderEntity orderEntity = OrderEntity.builder()
                    .cartProductColor(cartProductDto.getCartProductColor())
                    .cartProductCount(cartProductDto.getCartProductCount())
                    .cartProductSize(cartProductDto.getCartProductSize())
                    .productPrice(cartProductDto.getProductPrice())
                    .productName(cartProductDto.getProductName())
                    .build();
            orderRepository.save(orderEntity);

            // Stock 변경
            Optional<GoodsStockEntity> goodsStockEntityById = goodsStockRepository.findByStockSizeAndStockColorAndProductEntity_ProductId(
                    cartProductDto.getCartProductSize(),
                    cartProductDto.getCartProductColor(),
                    cartProductDto.getProductId()
            );
            if (goodsStockEntityById.isPresent()) {
                GoodsStockEntity goodsStockEntity = goodsStockEntityById.get();

                GoodsStockEntity goodsStockEntityChanged = GoodsStockEntity.builder()
                        .stockQuantity(goodsStockEntity.getStockQuantity() - cartProductDto.getCartProductCount())
                        .build();
                goodsStockRepository.save(goodsStockEntityChanged);
            } else {
                return ResponseEntity.badRequest().body("존재하지 않는 상품입니다.");
            }

            // CartProduct에서 삭제
            cartProductRepository.deleteById(cartProductDto.getCartProductId());
        }

        return ResponseEntity.ok("주문에 성공하였습니다.");
    }
}