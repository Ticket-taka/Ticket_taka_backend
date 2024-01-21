package umc.tickettaka.service;

import umc.tickettaka.domain.Invitation;
import umc.tickettaka.domain.Member;
import umc.tickettaka.domain.Team;
import umc.tickettaka.web.dto.request.TeamRequestDto;

import java.io.IOException;
public interface InvitationCommandService {
    Invitation sendInvitation(Member sender, Team team, Member receiver);
}
