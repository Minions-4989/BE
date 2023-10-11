package shopping.main.millions.entity.order;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Table(name = "order_payment")
public class OrderPaymentEntity {

    @Id
    @GeneratedValue
    @Column(name = "order_payment_id")
    private Long orderPaymentId; // 주문 번호

    @Column(name = "card_num")
    private String cardNum; // 카드 번호

    @Column(name = "card_cvc")
    private String cardCvc; // 카드 cvc

    @Column(name = "card_expiration_period")
    private String cardExpirationPeriod; //카드 유효기간

    @Column(name = "total_price")
    private Integer totalPrice; // 결제 총 금액

    @Column(name = "order_date")
    private LocalDateTime orderDate; // 구매 날짜

    @ManyToOne
    @JoinColumn(name = "user_order_id")
    private UserOrderEntity userOrderEntity;
}
