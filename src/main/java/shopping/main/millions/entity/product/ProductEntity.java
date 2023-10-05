package shopping.main.millions.entity.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.joda.time.DateTime;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.joda.time.DateTime;
import shopping.main.millions.entity.cart.CartProductEntity;
import shopping.main.millions.entity.category.CategoryEntity;
import shopping.main.millions.entity.member.MemberEntity;
import shopping.main.millions.entity.order.OrderEntity;
import shopping.main.millions.repository.sales.GoodsEditRepository;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@AllArgsConstructor
@SuperBuilder
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName; // 상품이름

    @Column(name = "product_price")
    private Integer productPrice; //상품가격

    @Column(name = "product_date")
    private DateTime productDate; //판매가능날짜

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    private List<CartProductEntity> cartProductEntityList;

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<GoodsStockEntity> goodsStockEntityList;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryEntity; // 카테고리
    //private final GoodsEditRepository goodsEditRepository;
    // 이런걸 쓰는이유가 무엇일가영 goodsEditRepository를 사용하기 위해서
    // 저걸 무슨방식이라고 할까영?아 생성자 주입방식이라고 합니다 아하
    // @Autowired = 필드주입방식식 네 자동으로 인식해서 주입해준다고
    // 생성자 주입을 쓰는걸 권장하기에 그렇게 쓰고있는^^
    // builder 저렇게 안쓰고 그냥 전에 하시던 아래다가 적는거로 하셔도되영ㅎ_ㅎ
    //아하 superBuilder를 사용 함으로써 서로 extends를 해준다는게 신기하네요 그러면 저는
    // 아 슈퍼빌드를 사용 함으로써 여러개의 엔티티를 한번에 빌드를 세울 수 있다는 뜻인가요 ?
    //이걸 상속받아서 쓰고있죵? 꼭 extends 를 안써도 다른 .java 파일에서 가져온 것을 쓰는게 상속이라는거네요 ?
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL) // 물건을 없애면 이미지도 날라가는거야 으이 !
    private List<GoodsImageEntity> goodsImageEntity;
    // May To One 으로 재고테이블 연관관계

    @ManyToOne
    @JoinColumn(name="user_id")
    private MemberEntity memberEntity; //나의 판매물품 조회를 위한 유저 연관관계

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderEntity> orderEntityList;
}

//여기에 합치고
//cascade가 저게 그..
// 흠.. 삭제할때 이게 결국 부모 자식관계거든용
//연관관계가 오 저거 좋네요 설명 이해 단번에 됐네
// one To Many 로 이미지 리스트 받아오는거로
// cascade가 단 하나만있는게 아닌 만약에
// 우리가 게시판 을 만든다고 봤을때 ;;
// 회원이 삭제되면 게시판을 지울수도있고
// 회원이 삭제되었다 해서 게시판은 그대로 놔둘수가있자나용?
// 그때 게시판은 삭제를 안하게 해버리면 연관관계에서 ex) 닉네임을 가져와서 게시판의 닉네임을 적어준다 가졍하죠
// 그럼 회원삭제했어 근데 게시판은 놔둘거야 그럼 오류가 발생하겠죵? 넵 존재하지않는 유저님 ㅝ니 ..
// 근데 그런 유저가 많아지면 상당히 귀찮아지겠네요 ;; 흐음 간단하게 할수있긴한데.. 고건 jpa공부를 더 해보시는거로...ㅎ_ㅎ
// 알아가는재미를위해^^ 정말 재밌네요 껄껄
// 그럼 그 유저가 없으니 null로 바꿔주고 오류가안뜨게 할수도있어용^_^ 아 폴더 따로있어서 불편하네
// 어떤폴더용? product하고 sales 폴더용 뭔가 불편
// 옮기시죵? 이제 dto를 만들어야겠네요 넵