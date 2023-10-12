package shopping.main.millions.controller.member;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopping.main.millions.dto.member.sighUp.SighUpDto;
import shopping.main.millions.jwt.TokenProvider;
import shopping.main.millions.service.member.MemberService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class MemberController {
    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> memberSignUp(@ModelAttribute SighUpDto sighUp){
        return memberService.memberSignUp(sighUp);
    }
    @PostMapping("/idcheck")
    public ResponseEntity<?> memberIdCheck(@RequestBody Map<String,String> email){
        boolean emailCheck = memberService.findByEmail(email.get("email"));
        Map<String,Object> response = new HashMap<>();
        response.put("Check", emailCheck);
        response.put("email",email.get("email"));
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> memberLogin(@RequestBody Map<String,String> login){
        return memberService.login(login);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> memberLogOut(HttpServletRequest request, HttpServletResponse response){
        String header = request.getHeader("X-AUTH-TOKEN");
        String userId = tokenProvider.getUserPk(header);
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken")) {
                    // 쿠키를 무효화하기 위해 쿠키의 값을 지움
                    cookie.setValue("");
                    // 쿠키의 유효기간을 0으로 설정하여 삭제
                    cookie.setMaxAge(0);
                    // 쿠키를 다시 클라이언트로 보냄
                    response.addCookie(cookie);
                }
            }
        }
        return memberService.logOut(userId);
    }
}
