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
import umc.tickettaka.service.InvitationQueryService;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvitationCommandServiceImpl implements InvitationCommandService {

    private final InvitationRepository invitationRepository;
    private final InvitationQueryService invitationQueryService;

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

    @Override
    public Invitation acceptInvitation(Long id, Member receiver) {
        Invitation invitation = invitationQueryService.findInvitation(id);

        if (receiver.getId().equals(invitation.getReceiver().getId())) {
            invitation = Invitation.builder()
                    .id(id)
                    .team(invitation.getTeam())
                    .receiver(invitation.getReceiver())
                    .sender(invitation.getSender())
                    .status(InvitationStatus.ACCEPT)
                    .build();
            return invitationRepository.save(invitation);
        } else {
            throw new GeneralException(ErrorStatus.UNAUTHORIZED_ACCESS);
        }
    }

    @Override
    public Invitation rejectInvitation(Long id, Member receiver) {
        Invitation invitation = invitationQueryService.findInvitation(id);

        if (receiver.getId().equals(invitation.getReceiver().getId())) {
            invitation = Invitation.builder()
                    .id(id)
                    .team(invitation.getTeam())
                    .receiver(invitation.getReceiver())
                    .sender(invitation.getSender())
                    .status(InvitationStatus.REJECT)
                    .build();
            return invitationRepository.save(invitation);
        } else {
            throw new GeneralException(ErrorStatus.UNAUTHORIZED_ACCESS);
        }
    }
}
