package shopping.main.millions.service.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopping.main.millions.dto.cart.CartProductDto;
import shopping.main.millions.entity.cart.CartEntity;
import shopping.main.millions.entity.member.MemberEntity;
import shopping.main.millions.entity.product.ProductEntity;
import shopping.main.millions.repository.cart.CartProductRepository;
import shopping.main.millions.repository.cart.CartRepository;
import shopping.main.millions.repository.member.MemberRepository;
import shopping.main.millions.repository.product.ProductRepository;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;

    public ResponseEntity<Map<String, String>> addCart(CartProductDto cartProductDto) {
//        for (CartProductDto cartProductDto : cartProductDto) {
//            System.out.println("Product ID: " + cartProductDto.getProductId());
//            System.out.println("Product Size: " + cartProductDto.getProductSize());
//            System.out.println("Product Color: " + cartProductDto.getProductColor());
//            System.out.println("Product Count: " + cartProductDto.getProductCount());
//            System.out.println(); // 개별 제품 간에 빈 줄 삽입
//        }
        return null;
    }

//    public Long addCart(CartProductDto cartProductDto, Long userId){
//        ProductEntity productEntity = productRepository.findById(cartProductDto.getCartProductId())
//                .orElseThrow(EntityNotFoundException::new);
//        MemberEntity memberEntity = memberRepository.findByUserEmail(userId); //타입이 달라서그런거같아여
//        //이거보다는 사용자 정보에 userId 숫자타입이 들어있을거거든여 위에 email 말구여ㅛㅇ?
//        //넹 그래서 사용자 아이디를 이용해서 장바구니id를 먼저 조회하고
//        //
//        CartEntity cartEntity = cartRepository.findByMemberId(memberEntity.getUserId());
//        if (cartEntity == null){
//            cartEntity = CartEntity.createCart(memberEntity);
//
//
//        }
//
//    }

}
