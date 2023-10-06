package shopping.main.millions.dto.product;

import lombok.*;
import org.joda.time.DateTime;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private Long productId;

    private String productName;

    private Integer productPrice;

    private Date productDate;

}
