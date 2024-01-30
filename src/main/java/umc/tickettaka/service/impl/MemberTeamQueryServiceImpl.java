package umc.tickettaka.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import umc.tickettaka.domain.Team;
import umc.tickettaka.domain.mapping.MemberTeam;
import umc.tickettaka.repository.MemberTeamRepository;
import umc.tickettaka.service.MemberTeamQueryService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberTeamQueryServiceImpl implements MemberTeamQueryService {

    private final MemberTeamRepository memberTeamRepository;
    @Override
    public List<MemberTeam> findAllMembersByTeam(Team team) {
        return memberTeamRepository.findAllMembersByTeam(team);
    }
}