package shopping.main.millions.dto.sales;


import lombok.*;

@Getter @Setter
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
