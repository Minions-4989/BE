package shopping.main.millions.dto.sales;


import lombok.*;
import shopping.main.millions.entity.product.GoodsStockEntity;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockSaveDto {
    private Long stockQuantity; // 품목 수량
    private String stockSize; // 품목 사이즈
    private String stockColor; // 품목 칼라
}
