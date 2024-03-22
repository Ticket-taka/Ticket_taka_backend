package umc.tickettaka.repository.Project;

import lombok.RequiredArgsConstructor;
import umc.tickettaka.domain.Project;

import java.util.List;

@RequiredArgsConstructor
public abstract class ProjectListRepository implements ProjectRepository{

    private final ProjectRepository projectRepository;

    @Override
    public List<Project> findAllByTeamId(Long teamId) {
        return projectRepository.findAllByTeamId(teamId);
    }
}
