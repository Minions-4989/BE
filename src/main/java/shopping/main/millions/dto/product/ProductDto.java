package shopping.main.millions.dto.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class ProductDto {

    private Long productId;

    private String productName;

    private Integer productPrice;

    private LocalDateTime productDate;

    @Builder
    public ProductDto(Long productId, String productName, Integer productPrice, LocalDateTime productDate) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDate = productDate;
    }
}
