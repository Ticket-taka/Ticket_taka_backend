package umc.tickettaka.service;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import umc.tickettaka.domain.Member;
import umc.tickettaka.domain.Team;
import umc.tickettaka.web.dto.request.MemberTeamRequestDto;
import umc.tickettaka.web.dto.request.TeamRequestDto;

public interface TeamCommandService {
    Team createTeam(Member member, MultipartFile image, TeamRequestDto.CreateTeamDto request) throws IOException;

    Team updateTeam(Long id, MultipartFile image, TeamRequestDto.CreateTeamDto request) throws IOException;

    void deleteTeam(Long id) throws IOException;

    void updateMemberTeamColor(Member member, Long teamsId, MemberTeamRequestDto.UpdateColorDto requeset);
}