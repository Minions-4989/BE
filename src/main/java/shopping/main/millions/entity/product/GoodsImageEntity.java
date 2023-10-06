package shopping.main.millions.entity.product;


import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Table(name = "goods_image") // 테이블이름을 image가 아닌 물품이미지 이름으로 수정하는게 좋을듯싶어용 ex) goodsimage
public class GoodsImageEntity {

    @Id
    @GeneratedValue
    @Column(name = "image_id")
    private Long imageId;


    @Column(name = "product_image", nullable = false)
    private String productImage; // s3주소가 저장될 컬럼

    // 이미지 오리지널 네임이 저장될 컬럼 필요
    @Column(name = "product_origin_name")
    private String productImageOriginName;
    // 이미지 s3에 저장될 네임이 저장될 컬럼 필요
    @Column(name = "product_image_save")
    private String productImageSave;
    // ManyToOne으로 물품과 연관관계가 필요할수도?

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    @Builder
    public GoodsImageEntity(Long imageId, String productImage) {
        this.imageId = imageId;
        this.productImage = productImage;
    }
}