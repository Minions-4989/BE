package shopping.main.millions.service.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import shopping.main.millions.dto.cart.CartProductDto;
import shopping.main.millions.dto.order.OrderDto;
import shopping.main.millions.entity.cart.CartEntity;
import shopping.main.millions.entity.cart.CartProductEntity;
import shopping.main.millions.entity.member.MemberEntity;
import shopping.main.millions.entity.order.OrderEntity;
import shopping.main.millions.entity.order.OrderPaymentEntity;
import shopping.main.millions.entity.order.UserOrderEntity;
import shopping.main.millions.entity.product.GoodsStockEntity;
import shopping.main.millions.entity.product.ProductEntity;
import shopping.main.millions.repository.cart.CartProductRepository;
import shopping.main.millions.repository.cart.CartRepository;
import shopping.main.millions.repository.member.MemberRepository;
import shopping.main.millions.repository.order.OrderPaymentRepository;
import shopping.main.millions.repository.order.OrderRepository;
import shopping.main.millions.repository.order.UserOrderRepository;
import shopping.main.millions.repository.product.ProductRepository;
import shopping.main.millions.repository.sales.GoodsStockRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public UserOrderEntity saveUser(OrderDto orderDto , String userId) {
        // Entity 변환 후 저장
        MemberEntity memberEntity = memberRepository.findById(Long.valueOf(userId)).get();
        UserOrderEntity userOrderEntity = UserOrderEntity.builder()
                .tellNumber(orderDto.getTellNumber())
                .userName(orderDto.getUserName())
                .address(orderDto.getAddress())
                .addressDetail(orderDto.getAddressDetail())
                .addressZipcode(orderDto.getAddressZipcode())
                .userEmail(orderDto.getUserEmail())
                .cartEntity(memberEntity.getCartEntity())
                .build();
        UserOrderEntity userOrder = userOrderRepository.save(userOrderEntity);

        return userOrder;
    }

    public ResponseEntity<?> saveUserPayment(OrderDto orderDto,  UserOrderEntity userOrder){
        // Entity 변환 후 저장
        Map<String,Long> map = new HashMap<>();

        OrderPaymentEntity orderPaymentEntity = OrderPaymentEntity.builder()
                .cardNum(orderDto.getCardNum())
                .cardCvc(orderDto.getCardCvc())
                .cardExpirationPeriod(orderDto.getCardExpirationPeriod())
                .totalPrice(orderDto.getTotalPrice())
                .orderDate(orderDto.getOrderDate())
                .userOrderEntity(userOrder)
                .build();
        orderPaymentRepository.save(orderPaymentEntity);
        map.put("userOrderId", userOrder.getUserOrderId());
        return ResponseEntity.ok(map);
    }

    public ResponseEntity<?> orderProcess(OrderDto orderDto, String userId) {
        // PutMapping - dto 받아서 거기에 있는 상품 리스트 통해,
        // 구매상품 OrderEntity에 저장, Stock 변경, CartProduct 삭제

        //카트아이디로 유저오더를 검색
        Optional<UserOrderEntity> userOrderEntityOptional = userOrderRepository.findById(orderDto.getUserOrderId());
        UserOrderEntity userOrderEntity = userOrderEntityOptional.get();

            // 체크된 상품 리스트화
            List<CartProductDto> cartProductDtoList = orderDto.getCartProductDtoList();

            for (CartProductDto cartProductDto : cartProductDtoList) {
                // (재고 수량 < 구매할 수량) 이면, 재고 부족 알림
                Optional<CartProductEntity> cartProductById = cartProductRepository.findById(cartProductDto.getCartProductId());
                if (cartProductById.isPresent()) {
                    CartProductEntity cartProductEntity = cartProductById.get();
                    Optional<GoodsStockEntity> goodsStockById =
                            goodsStockRepository.findByStockSizeAndStockColorAndProductEntity_ProductId(
                                    cartProductEntity.getCartProductSize(),
                                    cartProductEntity.getCartProductColor(),
                                    cartProductEntity.getProductEntity().getProductId()
                            );
                    GoodsStockEntity goodsStockEntity = goodsStockById.get();

                    if (goodsStockEntity.getStockQuantity() < cartProductEntity.getCartProductCount()) {
                        return ResponseEntity.badRequest().body("재고가 부족합니다");
                    }
                Optional<ProductEntity> productEntityOptional = productRepository.findById(cartProductDto.getProductId());
                    ProductEntity productEntity = productEntityOptional.get();
                // OrderEntity에 저장
                OrderEntity orderEntity = OrderEntity.builder()
                        .cartProductColor(cartProductDto.getCartProductColor())
                        .cartProductCount(cartProductDto.getCartProductCount())
                        .cartProductSize(cartProductDto.getCartProductSize())
                        .productPrice(cartProductDto.getProductPrice())
                        .productName(cartProductDto.getProductName())
                        .userOrderEntity(userOrderEntity)
                        .productEntity(productEntity)
                        .build();
                orderRepository.save(orderEntity);

                // Stock 변경
                Optional<GoodsStockEntity> goodsStockEntityById = goodsStockRepository.findByStockSizeAndStockColorAndProductEntity_ProductId(
                        cartProductDto.getCartProductSize(),
                        cartProductDto.getCartProductColor(),
                        cartProductDto.getProductId()
                );

                if (goodsStockEntityById.isPresent()) {
                    GoodsStockEntity goodsStockEntity2 = goodsStockEntityById.get();

                    GoodsStockEntity goodsStockEntityChanged = GoodsStockEntity.builder()
                            .stockId(goodsStockEntity2.getStockId())
                            .stockQuantity(goodsStockEntity2.getStockQuantity() - cartProductDto.getCartProductCount())
                            .stockSize(goodsStockEntity2.getStockSize())
                            .stockColor(goodsStockEntity2.getStockColor())
                            .productEntity(goodsStockEntity2.getProductEntity())
                            .build();
                    goodsStockRepository.save(goodsStockEntityChanged);

                } else {
                    return ResponseEntity.badRequest().body("존재하지 않는 상품입니다.");
                }

                // CartProduct에서 삭제
                cartProductRepository.deleteById(cartProductDto.getCartProductId());
            }
        }
        return ResponseEntity.ok("주문에 성공하였습니다.");
    }
}