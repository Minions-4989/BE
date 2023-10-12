package shopping.main.millions.service.mypage;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import shopping.main.millions.dto.cart.CartProductDto;
import shopping.main.millions.dto.mypage.MyOrderDto;
import shopping.main.millions.dto.mypage.MyOrderProductDto;
import shopping.main.millions.dto.mypage.MypageMainDto;
import shopping.main.millions.dto.order.OrderDto;
import shopping.main.millions.dto.product.ProductListResponseDto;
import shopping.main.millions.entity.cart.CartEntity;
import shopping.main.millions.entity.member.MemberEntity;
import shopping.main.millions.entity.order.OrderEntity;
import shopping.main.millions.entity.order.OrderPaymentEntity;
import shopping.main.millions.entity.order.UserOrderEntity;
import shopping.main.millions.repository.cart.CartRepository;
import shopping.main.millions.repository.member.MemberRepository;
import shopping.main.millions.repository.order.OrderPaymentRepository;
import shopping.main.millions.repository.order.OrderRepository;
import shopping.main.millions.repository.order.UserOrderRepository;
import shopping.main.millions.service.cart.CartService;
import shopping.main.millions.service.member.MemberService;
import shopping.main.millions.service.sales.GoodsSaveService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class MypageService {
    private final MemberRepository memberRepository;
    private final CartService cartService;
    private final GoodsSaveService goodsSaveService;
    private final CartRepository cartRepository;
    private final UserOrderRepository userOrderRepository;
    private final OrderPaymentRepository orderPaymentRepository;
    private final OrderRepository orderRepository;
    private final MemberService memberService;

    public ResponseEntity<?> mypagemain(String userId) {
        //멤버테이블, 장바구니 리스트, 구매 리스트, 등록 리스트
        Optional<MemberEntity> memberEntityOptional = memberRepository.findById(Long.valueOf(userId));
        if (memberEntityOptional.isPresent()) {
            MemberEntity memberEntity = memberEntityOptional.get();
            List<CartProductDto> cartProductDtoList = cartService.GetcartProductDtoList(userId);
            List<ProductListResponseDto> productListResponseDtoList = goodsSaveService.getProductListResDto(userId);
            List<MyOrderDto> myOrderDtoList = getOrderDtoList(userId);

            MypageMainDto mypageMainDto = MypageMainDto.builder()
                    .email(memberEntity.getUserEmail())
                    .addressZipcode(memberEntity.getAddressEntity().getAddressZipcode())
                    .address(memberEntity.getAddressEntity().getAddress())
                    .name(memberEntity.getUserName())
                    .addressDetail(memberEntity.getAddressEntity().getAddressDetail())
                    .telNumber(memberEntity.getTelNumber())
                    .gender(memberEntity.getGender())
                    .profileImage(memberEntity.getProfileImage())
                    .cartProductDtoList(cartProductDtoList)
                    .goodsSaveDtoList(productListResponseDtoList)
                    .myOrderDtoList(myOrderDtoList)
                    .build();
            return ResponseEntity.ok(mypageMainDto);
        }else {
            return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        }
    }

    public List<MyOrderDto> getOrderDtoList(String userId){
        //유저아이디>카트아이디
        //카트아이디> 유저오더테이블 리스트(주소, 전번, 이메일, 수령자이름, 유저오더아이디)
        //유저오더 아이디>오더페이먼트(주문날짜, 전체금액, 주문번호)
        //유저오더 아이디> 오더프로덕트 리스트(컬러, 수량, 사이즈, 이름, 개별가격)
        Optional<CartEntity> cartEntityOptional = cartRepository.findCartEntityByMemberEntity_UserId(Long.valueOf(userId));
        CartEntity cartEntity = cartEntityOptional.get();
        List<MyOrderDto> myOrderDtoList = new ArrayList<>();
        List<UserOrderEntity> userOrderEntityList = userOrderRepository.findByCartEntity_CartId(cartEntity.getCartId());
        for(UserOrderEntity userOrderEntity : userOrderEntityList) {
            OrderPaymentEntity orderPaymentEntity = orderPaymentRepository.findByUserOrderEntity_UserOrderId(userOrderEntity.getUserOrderId());
            List<OrderEntity> orderEntityList = orderRepository.findByUserOrderEntity_UserOrderId(userOrderEntity.getUserOrderId());
            List<MyOrderProductDto> myOrderProductDtoList = new ArrayList<>();
            for (OrderEntity orderEntity : orderEntityList){
                MyOrderProductDto myOrderProductDto = MyOrderProductDto.builder()
                        .productName(orderEntity.getProductName())
                        .cartProductCount(orderEntity.getCartProductCount())
                        .cartProductSize(orderEntity.getCartProductSize())
                        .cartProductColor(orderEntity.getCartProductColor())
                        .productPrice(orderEntity.getProductPrice())
                        .productId(orderEntity.getProductEntity().getProductId())
                        .build();
                myOrderProductDtoList.add(myOrderProductDto);
            }

            MyOrderDto myOrderDto = MyOrderDto.builder()
                    .orderId(userOrderEntity.getUserOrderId())
                    .totalPrice(orderPaymentEntity.getTotalPrice())
                    .orderDate(orderPaymentEntity.getOrderDate())
                    .myOrderProductDtoList(myOrderProductDtoList)
                    .build();
            myOrderDtoList.add(myOrderDto);
        }
        return myOrderDtoList;
    }

    public boolean checkPw(Map<String, String> pw, String userId) {
        Optional<MemberEntity> memberEntityOptional = memberRepository.findById(Long.valueOf(userId));
        MemberEntity memberEntity = memberEntityOptional.get();
        String password = pw.get("password");
        return memberService.findByPasswordCheck(memberEntity.getUserEmail(),password);
    }

    public ResponseEntity<?> withdraw(String userId) {
        return null;
    }
}
