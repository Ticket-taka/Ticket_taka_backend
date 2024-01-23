package umc.tickettaka.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.tickettaka.domain.Invitation;
import umc.tickettaka.domain.Member;
import umc.tickettaka.domain.Team;
import umc.tickettaka.domain.mapping.MemberTeam;
import umc.tickettaka.payload.exception.GeneralException;
import umc.tickettaka.payload.status.ErrorStatus;
import umc.tickettaka.repository.InvitationRepository;
import umc.tickettaka.repository.MemberTeamRepository;
import umc.tickettaka.service.InvitationCommandService;
import umc.tickettaka.service.InvitationQueryService;
import umc.tickettaka.service.MemberQueryService;
import umc.tickettaka.service.TeamQueryService;
import umc.tickettaka.web.dto.request.InvitationRequestDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvitationCommandServiceImpl implements InvitationCommandService {

    private final InvitationRepository invitationRepository;
    private final InvitationQueryService invitationQueryService;
    private final TeamQueryService teamQueryService;
    private final MemberTeamRepository memberTeamRepository;
    private final MemberQueryService memberQueryService;

    @Override
    @Transactional
    public Invitation sendInvitation(Member sender, Team team, InvitationRequestDto.CreateInvitationDto request) {
        Member receiver = memberQueryService.findByUsername(request.getReceiverUsername());

        Optional<Invitation> invitationOptional = invitationRepository.findByReceiverAndTeam(receiver, team);
        if (invitationOptional.isPresent()) {
            throw new GeneralException(ErrorStatus.INVITATION_ALREADY_EXIST);
        }

        Invitation invitation = Invitation.builder()
                .sender(sender)
                .receiver(receiver)
                .team(team)
                .build();

        return invitationRepository.save(invitation);
    }

    @Override
    public void acceptInvitation(Long id, Member receiver) {
        Invitation invitation = invitationQueryService.findInvitation(id);
        Team team = teamQueryService.findTeam(invitation.getTeam().getId());

        if (receiver.getId().equals(invitation.getReceiver().getId())) {
            Optional<MemberTeam> existingMemberTeam = memberTeamRepository.findByTeamAndMember(team, receiver);

            if (existingMemberTeam.isPresent()) {
                throw new GeneralException(ErrorStatus.MEMBER_TEAM_ALREADY_EXIST);
            }

            List<MemberTeam> memberTeamList = new ArrayList<>();

            MemberTeam newMemberTeam = MemberTeam.builder()
                    .team(team)
//                    .color()  멤버별 color설정
                    .member(receiver)
                    .build();

            memberTeamList.add(newMemberTeam);
            memberTeamRepository.saveAll(memberTeamList);
            invitationRepository.delete(invitation);
        } else {
            throw new GeneralException(ErrorStatus.UNAUTHORIZED_ACCESS);
        }
    }

    @Override
    @Transactional
    public void rejectInvitation(Long id, Member receiver) {
        Invitation invitation = invitationQueryService.findInvitation(id);

        if (receiver.getId().equals(invitation.getReceiver().getId())) {
            invitationRepository.delete(invitation);
        } else {
            throw new GeneralException(ErrorStatus.UNAUTHORIZED_ACCESS);
        }
    }
}
