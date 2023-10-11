package shopping.main.millions.dto.order;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderGetDto {
    private List<OrderResponseDto> orderResponseDtoList;
}
