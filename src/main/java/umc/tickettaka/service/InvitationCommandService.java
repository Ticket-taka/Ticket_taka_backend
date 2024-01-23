package umc.tickettaka.service;

import umc.tickettaka.domain.Invitation;
import umc.tickettaka.domain.Member;
import umc.tickettaka.domain.Team;
import umc.tickettaka.web.dto.request.InvitationRequestDto;
import umc.tickettaka.web.dto.request.TeamRequestDto;

import java.io.IOException;
public interface InvitationCommandService {
    Invitation sendInvitation(Member sender, Team team, InvitationRequestDto.CreateInvitationDto request);
    void acceptInvitation(Long id, Member receiver);
    void rejectInvitation(Long id, Member receiver);
}
