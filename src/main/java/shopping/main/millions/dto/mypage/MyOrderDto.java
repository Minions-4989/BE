package shopping.main.millions.dto.mypage;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyOrderDto {
    private Long orderId;
    private Integer totalPrice;
    private Date orderDate;
    private List<MyOrderProductDto> myOrderProductDtoList;
}
