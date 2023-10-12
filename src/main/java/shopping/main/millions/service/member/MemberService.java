package shopping.main.millions.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import shopping.main.millions.dto.member.sighUp.SighUpDto;
import shopping.main.millions.entity.cart.CartEntity;
import shopping.main.millions.entity.member.AddressEntity;
import shopping.main.millions.entity.member.MemberEntity;
import shopping.main.millions.jwt.TokenProvider;
import shopping.main.millions.jwt.dto.LoginTokenSaveDto;
import shopping.main.millions.jwt.dto.Token;
import shopping.main.millions.jwt.entity.RefreshToken;
import shopping.main.millions.jwt.repository.JwtRepository;
import shopping.main.millions.repository.cart.CartRepository;
import shopping.main.millions.repository.member.AddressRepository;
import shopping.main.millions.repository.member.MemberRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final AddressRepository addressRepository;
    private final MemberImageSaveService memberImageSaveService;
    private final TokenProvider tokenProvider;
    private final JwtRepository jwtRepository;
    private final CartRepository cartRepository;

    public ResponseEntity<?> memberSignUp(SighUpDto sighUp) {
        String emailPattern = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
        String passwordPatten = "^[A-Za-z0-9]{8,20}$";
        Map<String, Object> response = new HashMap<>();
        if (!findByEmail(sighUp.getEmail())) {
            if (!findByPhoneNumber(sighUp.getTelNumber())) {
                if (Pattern.matches(emailPattern, sighUp.getEmail())) {
                    if (Pattern.matches(passwordPatten, sighUp.getPassword())) {
                        if (sighUp.getProfileImage().isEmpty()) {
                            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                            String encPassword = bCryptPasswordEncoder.encode(sighUp.getPassword());
                            MemberEntity memberEntity = MemberEntity.builder()
                                    .userEmail(sighUp.getEmail())
                                    .userPassword(encPassword)
                                    .telNumber(sighUp.getTelNumber())
                                    .userName(sighUp.getName())
                                    .gender(sighUp.getGender())
                                    .profileImage("https://s3-minions-image-bucket.s3.ap-northeast-2.amazonaws.com/default_profile_img.gif")
                                    .status(true)
                                    .build();

                            AddressEntity addressEntity = AddressEntity.builder()
                                    .addressZipcode(sighUp.getAddressZipcode())
                                    .address(sighUp.getAddress())
                                    .addressDetail(sighUp.getAddressDetail())
                                    .build();
                            AddressEntity savedAddress = addressRepository.save(addressEntity);

                            memberEntity.setAddressEntity(savedAddress);

                            MemberEntity member = memberRepository.save(memberEntity);
                            CartEntity cartEntity = new CartEntity();
                            cartEntity.setMemberEntity(member);
                            cartRepository.save(cartEntity);
                            if (member.getUserId() > 0) {
                                response.put("success", true);
                                response.put("message", "회원가입이 성공적으로 완료되었습니다.");
                                return ResponseEntity.status(200).body(response);
                            } else {
                                response.put("success", false);
                                response.put("message", "회원가입에 실패했습니다.");
                                return ResponseEntity.status(500).body(response);
                            }
                        } else {
                            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                            String encPassword = bCryptPasswordEncoder.encode(sighUp.getPassword());
                            String saveUrl = memberImageSaveService.imageSave(sighUp.getProfileImage());
                            AddressEntity addressEntity = AddressEntity.builder()
                                    .addressZipcode(sighUp.getAddressZipcode())
                                    .address(sighUp.getAddress())
                                    .addressDetail(sighUp.getAddressDetail())
                                    .build();
                            AddressEntity savedAddress = addressRepository.save(addressEntity);


                            MemberEntity memberEntity = MemberEntity.builder()
                                    .userEmail(sighUp.getEmail())
                                    .userPassword(encPassword)
                                    .telNumber(sighUp.getTelNumber())
                                    .userName(sighUp.getName())
                                    .gender(sighUp.getGender())
                                    .profileImage(saveUrl)
                                    .addressEntity(savedAddress)
                                    .status(true)
                                    .build();

                            MemberEntity member = memberRepository.save(memberEntity);
                            CartEntity cartEntity = new CartEntity();
                            cartEntity.setMemberEntity(member);
                            cartRepository.save(cartEntity);
                            if (member.getUserId() > 0) {
                                response.put("success", true);
                                response.put("message", "회원가입이 성공적으로 완료되었습니다.");
                                return ResponseEntity.status(200).body(response);
                            } else {
                                response.put("success", false);
                                response.put("message", "회원가입에 실패했습니다.");
                                return ResponseEntity.status(500).body(response);
                            }
                        }
                    } else {
                        return ResponseEntity.status(400).body("형식이 잘못된 비밀번호 입니다");
                    }
                } else {
                    return ResponseEntity.status(400).body("형식이 잘못된 이메일 입니다");
                }
            } else {
                return ResponseEntity.status(400).body("이미 가입된 전화번호 입니다.");
            }
        } else {
            return ResponseEntity.status(400).body("이미 가입된 이메일 주소입니다.");
        }
    }

    public boolean findByEmail(String email) {
        MemberEntity memberEntity = memberRepository.findByUserEmail(email);
        if (memberEntity != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean findByPhoneNumber(String telNumber) {
        MemberEntity memberEntity = memberRepository.findByTelNumber(telNumber);

        if (memberEntity != null) {
            return true;
        } else {
            return false;
        }
    }

    public ResponseEntity<?> login(Map<String, String> login) {
        Map<String, String> result = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        if (findByEmail(login.get("email"))) {
            if (findByPasswordCheck(login.get("email"), login.get("password"))) {
                MemberEntity memberEntity = memberRepository.findByUserEmail(login.get("email"));
                LoginTokenSaveDto loginTokenSaveDto = LoginTokenSaveDto.builder()
                        .id(memberEntity.getUserId())
                        .email(memberEntity.getUserEmail())
                        .build();
                Token token = tokenProvider.createToken(loginTokenSaveDto.getId(), loginTokenSaveDto);
                RefreshToken refreshToken = RefreshToken.builder()
                        .token(token.getRefreshToken())
                        .memberEntity(memberEntity)
                        .build();
                jwtRepository.save(refreshToken);
                return ResponseEntity.status(200).headers(headers).body(token);
            }else{
                result.put("message" , "이메일 또는 비밀번호가 일치하지 않아요!");
                return ResponseEntity.status(401).body(result);
            }
        }else {
            result.put("message" , "이메일 또는 비밀번호가 일치하지 않아요!");
            return ResponseEntity.status(401).body(result);
        }
    }

    public boolean findByPasswordCheck(String email ,  String password){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String entityPassword = memberRepository.findByUserEmail(email).getUserPassword();
        return bCryptPasswordEncoder.matches(password,entityPassword);
    }
}
