package shopping.main.millions.dto.sales;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import lombok.experimental.SuperBuilder;
import shopping.main.millions.entity.product.ProductEntity;

import lombok.*;



import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsImageDto {
    private Long imageId;

    private Long productId;

    private String productImage; // s3주소가 저장될 컬럼

    private String productImageOriginName;  // 이미지 오리지널 네임이 저장될 컬럼 필요

    private String productImageSave; // 이미지 s3에 저장될 네임이 저장될 컬럼 필요
}
