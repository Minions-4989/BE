package shopping.main.millions.jwt.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenRequestDto {
    private String accessToken;
    private Date issuedAt;
    private Date accessTokenExpireDate;


}
