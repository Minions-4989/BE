package shopping.main.millions.dto.cart;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptionDto {
    private String productSize;
    private String productColor;
    private Long productCount;
}
