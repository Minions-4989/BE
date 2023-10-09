package shopping.main.millions.service.mypage;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import shopping.main.millions.entity.member.MemberEntity;
import shopping.main.millions.repository.member.MemberRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class MypageService {
    private final MemberRepository memberRepository;

    public ResponseEntity<?> viewMypage(Long userId){
        Map<String, Object> map = new HashMap<>();
        //내 정보
        Optional<MemberEntity> memberEntityOptional = memberRepository.findById(userId);
        MemberEntity memberEntity = memberEntityOptional.get();





        return null;

    }
}

