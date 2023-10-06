package shopping.main.millions.dto.sales;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import shopping.main.millions.entity.product.ProductEntity;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class GoodsImageDto {
    private String productImage;
    private String productImageOriginName;
    private String productImageSave;
    private ProductEntity productEntity;
}
