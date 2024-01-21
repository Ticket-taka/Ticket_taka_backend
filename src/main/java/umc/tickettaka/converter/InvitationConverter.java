package umc.tickettaka.converter;

import umc.tickettaka.domain.Invitation;
import umc.tickettaka.domain.Member;
import umc.tickettaka.domain.Team;
import umc.tickettaka.domain.Timeline;
import umc.tickettaka.domain.enums.InvitationStatus;
import umc.tickettaka.domain.enums.TicketStatus;
import umc.tickettaka.domain.ticket.Ticket;
import umc.tickettaka.payload.exception.GeneralException;
import umc.tickettaka.payload.status.ErrorStatus;
import umc.tickettaka.web.dto.request.TicketRequestDto;
import umc.tickettaka.web.dto.response.InvitationResponseDto;
import umc.tickettaka.web.dto.response.TicketResponseDto;

import java.time.LocalDate;

public class InvitationConverter {

    public static InvitationResponseDto.InvitationDto invitationDto(Invitation invitation) {
        return InvitationResponseDto.InvitationDto.builder()
                .id(invitation.getId())
                .createdTime(invitation.getCreatedTime())
                .build();
    }

    public static Invitation toInvitation (Team team, Member sender, Member receiver) {
        return Invitation.builder()
                .team(team)
                .receiver(receiver)
                .sender(sender)
                .status(InvitationStatus.WAIT)
                .build();
    }
}
