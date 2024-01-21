package umc.tickettaka.converter;

import umc.tickettaka.domain.Invitation;
import umc.tickettaka.domain.Member;
import umc.tickettaka.domain.Team;
import umc.tickettaka.domain.enums.InvitationStatus;
import umc.tickettaka.web.dto.response.InvitationResponseDto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class InvitationConverter {

    public static InvitationResponseDto.InvitationDto invitationDto(Invitation invitation) {
        return InvitationResponseDto.InvitationDto.builder()
                .id(invitation.getId())
                .createdAt(invitation.getCreatedTime())
                .build();
    }

    public static Invitation toInvitation (Team team, Member sender, Member receiver) {
        return Invitation.builder()
                .team(team)
                .sender(sender)
                .receiver(receiver)
                .status(InvitationStatus.WAIT)
                .build();
    }

    public static InvitationResponseDto.InvitationListDto toInvitationListDto(Member member, List<Invitation> invitationList) {
        List<InvitationResponseDto.InvitationDto> invitationDtoList = invitationList.stream()
                .filter(invitation -> member.getId().equals(invitation.getReceiver().getId()))
                .filter(invitation -> InvitationStatus.WAIT.equals(invitation.getStatus()))
                .map(InvitationConverter::invitationDto)
                .collect(Collectors.toList());

        return InvitationResponseDto.InvitationListDto.builder()
                .invitationDtoList(invitationDtoList)
                .build();
    }
}
