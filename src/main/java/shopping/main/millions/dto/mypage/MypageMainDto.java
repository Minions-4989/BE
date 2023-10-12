package shopping.main.millions.dto.mypage;

import lombok.*;
import shopping.main.millions.dto.cart.CartProductDto;
import shopping.main.millions.dto.order.OrderResponseDto;
import shopping.main.millions.dto.product.ProductListResponseDto;
import shopping.main.millions.dto.sales.GoodsSaveDto;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MypageMainDto {
    //사용자 정보
    private String email;
    private String addressZipcode;
    private String address;
    private String name;
    private String addressDetail;
    private String telNumber;
    private String gender;
    private String profileImage;
    //장바구니 목록
    private List<CartProductDto> cartProductDtoList;
    //판매 목록
    private List<ProductListResponseDto> goodsSaveDtoList;
    //구매 목록
    private List<MyOrderDto> myOrderDtoList;
}
