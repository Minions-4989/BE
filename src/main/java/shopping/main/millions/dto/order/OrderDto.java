package shopping.main.millions.dto.order;

import lombok.*;
import shopping.main.millions.dto.cart.CartProductDto;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    // 결제 정보
    private String cardNum; // 카드 번호

    private String cardCvc; // 카드 cvc

    private String cardExpirationPeriod; //카드 유효기간

    private String telNumber;

    // 수령인 정보
    private String userName;

    private String addressZipcode;

    private String address;

    private String addressDetail;

    // 물품 정보
    private List<CartProductDto> cartProductDtoList;

    // 오더 정보
    private LocalDateTime orderDate;

    private Integer totalPrice;

}
