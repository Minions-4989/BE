package shopping.main.millions.service.cart;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.main.millions.dto.cart.CartAddDto;
import shopping.main.millions.dto.cart.CartProductDto;
import shopping.main.millions.dto.cart.CartProductInputDto;
import shopping.main.millions.dto.cart.OptionDto;
import shopping.main.millions.entity.cart.CartProductEntity;
import shopping.main.millions.entity.member.MemberEntity;
import shopping.main.millions.entity.product.GoodsStockEntity;
import shopping.main.millions.entity.product.ProductEntity;
import shopping.main.millions.repository.cart.CartProductRepository;
import shopping.main.millions.repository.member.MemberRepository;
import shopping.main.millions.repository.product.ProductRepository;
import shopping.main.millions.repository.sales.GoodsStockRepository;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final CartProductRepository cartProductRepository;
    private final GoodsStockRepository goodsStockRepository;

    //물품 장바구니 담기
    @Transactional
    public ResponseEntity<Map<String, String>> addCart(CartAddDto cartAddDto) {
        Map<String, String> map = new HashMap<>();
        List<OptionDto> optionList = cartAddDto.getOption();
        //옵션을 선택한 수량 만큼 db라인 하나씩 추가
        //그전에 하나씩 선택수량이 주문 가능한지 판별
        for(OptionDto optionDto : optionList){
            //cartProductDto의 productId와 optionList의 사이즈, 컬러 로 재고량 검색
            Optional<GoodsStockEntity> goodsStockEntityOptional =
                    goodsStockRepository.findByStockSizeAndStockColorAndProductEntity_ProductId
                            (optionDto.getProductSize(),optionDto.getProductColor(),
                                    cartAddDto.getProductId());
            GoodsStockEntity goodsStockEntity = goodsStockEntityOptional.get();
            //재고보다 주문 수량이 많으면 리턴하기
            if(optionDto.getProductCount()>goodsStockEntity.getStockQuantity()){
                map.put("message","주문 불가능 한 상품이 포함되어있습니다.");
                return ResponseEntity.status(400).body(map);
            }
        }

        //전체 물품이 주문 가능한 것으로 판별 됨
        //이제 모든 데이터를 CartProduct DB에 저장함
        for (OptionDto optionDto : optionList){
            CartProductInputDto cartProductInputDto = new CartProductInputDto();
            //디티오에 저장하기 위해서 멤버엔티티, 프로덕트엔티티를 가져와야함 (근데 엔티티에 setter를 쓸 수 없으니, repository에서 불러옴)
            Optional<MemberEntity> memberEntityOptional = memberRepository.findById(cartAddDto.getUserId());
            MemberEntity memberEntity = memberEntityOptional.get();
            cartProductInputDto.setMemberEntity(memberEntity);

            Optional<ProductEntity> productEntityOptional = productRepository.findById(cartAddDto.getProductId());
            ProductEntity productEntity = productEntityOptional.get();
            cartProductInputDto.setProductEntity(productEntity);

            cartProductInputDto.setProductColor(optionDto.getProductColor());
            cartProductInputDto.setProductSize(optionDto.getProductSize());
            cartProductInputDto.setProductCount(optionDto.getProductCount());
            //여기까지 카트 인풋 디티오 완성

            //카트 인풋 디티오중에 장바구니에 이미 같은 품목이 있는가 검색
            Optional<CartProductEntity> serchCartEntityOptional = cartProductRepository.findCartProductEntityByCartProductColorAndCartProductSizeAndMemberEntity_UserIdAndProductEntity_ProductId(
                    optionDto.getProductColor(),optionDto.getProductSize(),memberEntity.getUserId(),productEntity.getProductId()
            );
            if(serchCartEntityOptional.isPresent()){
                //있으면 있는거에서 수량을 늘리기
                CartProductEntity serchCartEntity = serchCartEntityOptional.get();
                //카트프로덕트 아이디를 가져온다.
                Long cartProductId = serchCartEntity.getCartProductId();
                //해당 아이디 값의 데이터에서 카운트 만 업데이트
                cartProductRepository.updateCartProductCount(cartProductInputDto.getProductCount(),cartProductId);
            }else{
            //없다면 데이터 추가

            //디티오를 엔티티로 변환하기
            CartProductEntity insertEntity =CartProductEntity.builder()
                    .memberEntity(cartProductInputDto.getMemberEntity())
                    .productEntity(cartProductInputDto.getProductEntity())
                    .cartProductCount(cartProductInputDto.getProductCount())
                    .cartProductSize(cartProductInputDto.getProductSize())
                    .cartProductColor(cartProductInputDto.getProductColor())
                    .build();

            cartProductRepository.save(inputEntity);

        }
        map.put("message","장바구니에 추가되었습니다.");
        return ResponseEntity.status(200).body(map);
    }


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

    public ResponseEntity<?> increaseQuantity(Long cartProductId) {

        Optional<CartProductEntity> cartProductById = cartProductRepository.findById(cartProductId);
        if (cartProductById.isPresent()){
            CartProductEntity cartProductEntity = cartProductById.get();
            Optional<GoodsStockEntity> goodsStockById =
                    goodsStockRepository.findByStockSizeAndStockColorAndProductEntity_ProductId(
                    cartProductEntity.getCartProductSize(),
                    cartProductEntity.getCartProductColor(),
                    cartProductEntity.getCartProductId()
            );
            GoodsStockEntity goodsStockEntity = goodsStockById.get();

            // 구매수량이 재고수량보다 작으면
            if (cartProductEntity.getCartProductCount() < goodsStockEntity.getStockQuantity()){

                // 구매수량 +1 후 save
                cartProductEntity.setCartProductCount(cartProductEntity.getCartProductCount()+1);
                cartProductRepository.save(cartProductEntity);

                // Dto 변환
                CartProductDto cartProductDto = new CartProductDto().builder()
                        .cartProductCount(cartProductEntity.getCartProductCount())
                        .userId(cartProductEntity.getMemberEntity().getUserId())
                        .productId(cartProductEntity.getCartProductId())
                        .cartProductId(cartProductEntity.getCartProductId())
                        .cartProductSize(cartProductEntity.getCartProductSize())
                        .cartProductColor(cartProductEntity.getCartProductColor())
                        .productPrice(cartProductEntity.getProductEntity().getProductPrice())
                        .productName(cartProductEntity.getProductEntity().getProductName())
                        .productImage(cartProductEntity.getProductEntity().getGoodsImageEntity())
                        .build();

                return ResponseEntity.ok(cartProductDto);
            } else {
                return ResponseEntity.badRequest().body("재고부족으로 더 이상 추가할 수 없습니다.");
            }

        } else {
            return ResponseEntity.badRequest().body("해당 상품이 존재하지 않습니다.");
        }
    }

    public ResponseEntity<?> decreaseQuantity(Long cartProductId) {
        Optional<CartProductEntity> cartProductById = cartProductRepository.findById(cartProductId);
        if (cartProductById.isPresent()){
            CartProductEntity cartProductEntity = cartProductById.get();

            // 구매수량이 1이 아니면
            if (cartProductEntity.getCartProductCount() != 1){

                // 구매수량 -1
                cartProductEntity.setCartProductCount(cartProductEntity.getCartProductCount()-1);
                cartProductRepository.save(cartProductEntity);

               // Dto 변환
                CartProductDto cartProductDto = new CartProductDto().builder()
                        .cartProductCount(cartProductEntity.getCartProductCount())
                        .userId(cartProductEntity.getMemberEntity().getUserId())
                        .productId(cartProductEntity.getCartProductId())
                        .cartProductId(cartProductEntity.getCartProductId())
                        .cartProductSize(cartProductEntity.getCartProductSize())
                        .cartProductColor(cartProductEntity.getCartProductColor())
                        .productPrice(cartProductEntity.getProductEntity().getProductPrice())
                        .productName(cartProductEntity.getProductEntity().getProductName())
                        .productImage(cartProductEntity.getProductEntity().getGoodsImageEntity())
                        .build();

                return ResponseEntity.ok(cartProductDto);
            } else {
                return ResponseEntity.badRequest().body("구매수량을 더 이상 감소시킬 수 없습니다.");
            }

        } else {
            return ResponseEntity.badRequest().body("해당 상품이 존재하지 않습니다.");
        }
    }
    public ResponseEntity<Map<String,String>> deleteProductList(List<CartProductEntity> cartProductEntityList, String userId){
        Map<String, String> deleteMap = new HashMap<>();

        List<Long> cartProductIds = cartProductEntityList.stream()
                .map(CartProductEntity::getCartProductId)
                .collect(Collectors.toList());

        // 사용자 ID와 선택한 카트 상품 ID 목록을 기반으로 삭제
        cartProductRepository.deleteByMemberEntity_UserIdAndCartProductIdIn(userId, cartProductIds);

        deleteMap.put("message", "카트 상품이 삭제되었습니다.");
        return ResponseEntity.ok(deleteMap);
    }
}
