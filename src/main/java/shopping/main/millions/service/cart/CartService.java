package shopping.main.millions.service.cart;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import shopping.main.millions.dto.cart.CartProductDto;
import shopping.main.millions.dto.product.ProductDto;
import shopping.main.millions.dto.sales.GoodsImageDto;
import shopping.main.millions.entity.cart.CartProductEntity;
import shopping.main.millions.entity.member.MemberEntity;
import shopping.main.millions.entity.product.GoodsImageEntity;
import shopping.main.millions.entity.product.ProductEntity;
import shopping.main.millions.repository.cart.CartProductRepository;
import shopping.main.millions.repository.member.MemberRepository;
import shopping.main.millions.repository.product.ProductRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Log4j2
@Service
@RequiredArgsConstructor
public class CartService {
    private final CartProductRepository cartProductRepository;

    public ResponseEntity<?> CartProductList(String userId) {
        List<CartProductEntity> cartProducts = cartProductRepository.findCartProductEntityByMemberEntity_UserId(Long.valueOf(userId));
        List<CartProductDto> cartProductDtoList = new ArrayList<>();

        for (CartProductEntity cartProductEntity : cartProducts) {
            CartProductDto dto = new CartProductDto().builder()
                    .userId(cartProductEntity.getMemberEntity().getUserId())
                    .productId(cartProductEntity.getProductEntity().getProductId())
                    .cartProductId(cartProductEntity.getCartProductId())
                    .cartProductCount(cartProductEntity.getCartProductCount())
                    .cartProductSize(cartProductEntity.getCartProductSize())
                    .cartProductColor(cartProductEntity.getCartProductColor())
                    .productPrice(cartProductEntity.getProductEntity().getProductPrice())
                    .productName(cartProductEntity.getProductEntity().getProductName())
                    .productImage(cartProductEntity.getProductEntity().getGoodsImageEntity())
                    .build();
            cartProductDtoList.add(dto);
        }
        return ResponseEntity.status(200).body(cartProductDtoList);
    }

}
