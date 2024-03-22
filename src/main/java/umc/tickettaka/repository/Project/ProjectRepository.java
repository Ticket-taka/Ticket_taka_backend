package umc.tickettaka.repository.Project;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc.tickettaka.domain.Project;
import umc.tickettaka.domain.Timeline;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findAllByTeamId(Long teamId);


    //@Query("select p from Project p join fetch p.timelineList tl where tl = :timeline")
    Project findByTimeline(@Param("timeline") Timeline timeline);

    Project findProjectById(Long id);
}
