package shopping.main.millions.jwt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import shopping.main.millions.entity.member.MemberEntity;
import shopping.main.millions.jwt.dto.CustomUserDetails;
import shopping.main.millions.repository.memberRepository.MemberRepository;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService{
    private final MemberRepository memberRepository;

    @Transactional
    public UserDetails loadUserByUsername(String userPk) throws UsernameNotFoundException {

        MemberEntity memberEntity = memberRepository.findById(Long.valueOf(userPk)).get();
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setID(memberEntity.getUserEmail());
        customUserDetails.setPASSWORD(memberEntity.getUserPassword());
        customUserDetails.setNAME(memberEntity.getUserName());
        customUserDetails.setAUTHORITY("ROLE_USER");
        return customUserDetails;
    }
}
