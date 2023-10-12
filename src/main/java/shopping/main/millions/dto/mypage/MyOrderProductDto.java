package shopping.main.millions.dto.mypage;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyOrderProductDto {
    private String productName;
    private Long cartProductCount;
    private String cartProductSize;
    private String cartProductColor;
    private Integer productPrice;
    private Long productId;
}
