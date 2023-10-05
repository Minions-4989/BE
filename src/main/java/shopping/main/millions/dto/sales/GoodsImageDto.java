package shopping.main.millions.dto.sales;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class GoodsImageDto {

    private Long imageId;

    private Long productId;

    public GoodsImageDto toImageEntity(){
        return GoodsImageDto.builder()
                .imageId(this.imageId)
                .build();
    }
    @Builder
    public GoodsImageDto(Long imageId, Long productId) {
        this.imageId = imageId;
        this.productId = productId;
    }
}
