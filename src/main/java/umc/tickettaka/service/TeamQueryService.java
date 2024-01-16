package umc.tickettaka.service;

import java.util.List;
import umc.tickettaka.domain.Team;
import java.util.Optional;

public interface TeamQueryService {
    Optional<Team> findTeam(Long id);
    List<Team> findAll();
}