package shopping.main.millions.dto.order;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {
    private Long cartProductId;
    private Long productId;
    private Long cartProductCount;
    private String cartProductSize;
    private String cartProductColor;
    private Integer productPrice;
}
