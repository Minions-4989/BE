package shopping.main.millions.controller.mypage;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopping.main.millions.jwt.TokenProvider;
import shopping.main.millions.service.member.MemberService;
import shopping.main.millions.service.mypage.MypageService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/my")
@RequiredArgsConstructor
public class MypageController {
    private final TokenProvider tokenProvider;
    private final MypageService mypageService;


    //마이페이지 조회
    @GetMapping("/main")
    public ResponseEntity<?> viewMypage(HttpServletRequest request){
        String header = request.getHeader("X-AUTH-TOKEN");
        String userId = tokenProvider.getUserPk(header);
        return mypageService.mypagemain(userId);
    }

    //구매물품목록 조회
    @GetMapping("/orderList")
    public ResponseEntity<?> viewOrderList(HttpServletRequest request){
        String header = request.getHeader("X-AUTH-TOKEN");
        String userId = tokenProvider.getUserPk(header);
        return ResponseEntity.ok().body(mypageService.getOrderDtoList(userId));
    }

    //회원탈퇴
    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(HttpServletRequest request){
        String header = request.getHeader("X-AUTH-TOKEN");
        String userId = tokenProvider.getUserPk(header);
        return mypageService.withdraw(userId);
    }

    // 회원탈퇴 전 비밀번호 확인하기
    @PostMapping("/pwCheck")
    public ResponseEntity<?> checkPw(@RequestBody Map<String,String> pw,HttpServletRequest request){
        String header = request.getHeader("X-AUTH-TOKEN");
        String userId = tokenProvider.getUserPk(header);
        boolean pwCheck = mypageService.checkPw(pw, userId);
        Map<String,Object> response = new HashMap<>();
        response.put("Check", pwCheck);
        response.put("email",pw.get("password"));
        return ResponseEntity.status(200).body(response);
    }

}
