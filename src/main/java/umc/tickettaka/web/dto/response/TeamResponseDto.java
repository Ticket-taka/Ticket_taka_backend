package umc.tickettaka.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.tickettaka.web.dto.request.TeamRequestDto;

import java.time.LocalDateTime;
import java.util.List;

public class TeamResponseDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeamDto{
        Long teamId;
        String name;
        String imageUrl;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeamAndInvitationListDto {
        List<TeamDto> teamDtoList;
        List<InvitationResponseDto.InvitationDto> invitationDtoList;
    }
}
