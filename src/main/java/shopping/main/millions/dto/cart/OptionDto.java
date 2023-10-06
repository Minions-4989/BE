package shopping.main.millions.dto.cart;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OptionDto {

        private String productSize; // 옵션사이즈
        private String productColor; // 옵션컬러
        private Long productCount; // 구매할개수

}
