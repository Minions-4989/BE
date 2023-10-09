package shopping.main.millions.dto.mypage;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MypageDto {
    //회원정보
    private String email;
    private String password;
    private String addressZipcode;
    private String address;
    private String name;
    private String addressDetail;
    private String telNumber;
    private String gender;
    private MultipartFile profileImage;
    //판매물품 목록
    //private List<>
    //구매물품 목록
    //private List
}
