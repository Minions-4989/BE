package shopping.main.millions.service.mypage;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import shopping.main.millions.dto.mypage.MypageMainDto;
import shopping.main.millions.entity.member.MemberEntity;
import shopping.main.millions.repository.member.MemberRepository;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class MypageService {
    private final MemberRepository memberRepository;

    public ResponseEntity<?> mypagemain(String userId) {
        //멤버테이블, 장바구니 리스트, 구매 리스트, 등록 리스트
        Optional<MemberEntity> memberEntityOptional = memberRepository.findById(Long.valueOf(userId));
        if (memberEntityOptional.isPresent()) {
            MemberEntity memberEntity = memberEntityOptional.get();
//            MypageMainDto mypageMainDto = MypageMainDto.builder()
//                    .gender()
//                    .email()
//                    .name()
//                    .
//                    .build();
            return null;
        }else {
            return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        }
    }
}
