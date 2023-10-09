package shopping.main.millions.controller.mypage;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.main.millions.jwt.TokenProvider;
import shopping.main.millions.service.mypage.MypageService;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@RestController
@RequestMapping("/my")
@RequiredArgsConstructor
public class MypageController {
    private final TokenProvider tokenProvider;
    private final MypageService mypageService;

    @GetMapping("/main")
    public ResponseEntity<?> viewMypage(HttpServletRequest request){
        String header = request.getHeader("X-AUTH-TOKEN");
        String userId = tokenProvider.getUserPk(header);

        return mypageService.viewMypage(Long.valueOf(userId));
    }

}
