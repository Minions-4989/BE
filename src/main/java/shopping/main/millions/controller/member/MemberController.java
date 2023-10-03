package shopping.main.millions.controller.member;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopping.main.millions.dto.member.sighUp.SighUpDto;
import shopping.main.millions.service.member.MemberService;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
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
}
