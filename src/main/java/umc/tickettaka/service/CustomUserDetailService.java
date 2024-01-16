package umc.tickettaka.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import umc.tickettaka.domain.CustomUserDetailsAdapter;
import umc.tickettaka.domain.Member;
import umc.tickettaka.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findByUsername(username);

        if(optionalMember.isEmpty()) throw new UsernameNotFoundException("해당하는 회원을 찾을 수 없습니다.");

        Member member = optionalMember.get();

        return createUserDetails(member);

    }
    private CustomUserDetailsAdapter createUserDetails(Member member) {
        return CustomUserDetailsAdapter.builder()
                .member(member)
                .username(member.getUsername())
                .password(member.getPassword())
                .roles(member.getRoles())
                .build();

    }


}