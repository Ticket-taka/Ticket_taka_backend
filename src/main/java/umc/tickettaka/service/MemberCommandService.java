package umc.tickettaka.service;

import org.springframework.web.multipart.MultipartFile;
import umc.tickettaka.config.security.jwt.JwtToken;
import umc.tickettaka.domain.Member;
import umc.tickettaka.web.dto.common.CommonMemberDto;
import umc.tickettaka.web.dto.request.MemberRequestDto;
import umc.tickettaka.web.dto.request.SignRequestDto;
import umc.tickettaka.web.dto.response.SignResponseDto;

import java.io.IOException;

public interface MemberCommandService {
    JwtToken signIn(SignRequestDto.SignInDto signInDto);
    Member save(SignRequestDto.SignUpDto signUpDto);

    CommonMemberDto.ShowMemberProfileListDto getCommonMemberDto(Long teamId);
    CommonMemberDto.ShowMemberProfileDto getMemberProfileDto(Member member, Long teamId);
    Member updateMember(Long memberId, MultipartFile image, MemberRequestDto.UpdateDto memberUpdateDto) throws IOException;

    String getAccessToken(String expiredAccessToken);

    SignResponseDto.SignOutResultDto signOut(SignRequestDto.SignOutDto signOutDto);
}
