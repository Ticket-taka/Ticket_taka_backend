package umc.tickettaka.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import umc.tickettaka.domain.Invitation;
import umc.tickettaka.domain.Member;
import umc.tickettaka.domain.Team;
import umc.tickettaka.domain.enums.InvitationStatus;
import umc.tickettaka.payload.exception.GeneralException;
import umc.tickettaka.payload.status.ErrorStatus;
import umc.tickettaka.repository.InvitationRepository;
import umc.tickettaka.service.InvitationCommandService;
import umc.tickettaka.service.MemberQueryService;
import umc.tickettaka.service.TeamQueryService;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvitationCommandServiceImpl implements InvitationCommandService {

    private final InvitationRepository invitationRepository;

    @Override
    public Invitation sendInvitation(Member sender, Team team, Member receiver) {

        boolean invitationExists = invitationRepository.existsBySenderAndReceiverAndTeam(
                sender, receiver, team
        );
        if (invitationExists) {
            throw new GeneralException(ErrorStatus.INVITATION_ALREADY_EXIST);
        }

        Invitation invitation = Invitation.builder()
                .sender(sender)
                .receiver(receiver)
                .team(team)
                .status(InvitationStatus.WAIT)
                .build();

        return invitationRepository.save(invitation);
    }
}
