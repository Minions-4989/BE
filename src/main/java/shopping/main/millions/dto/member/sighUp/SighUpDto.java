package shopping.main.millions.dto.member.sighUp;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class SighUpDto {
    private String email;
    private String password;
    private String addressZipcode;
    private String address;
    private String name;
    private String addressDetail;
    private String telNumber;
    private String gender;
    private MultipartFile profileImage;
    }
