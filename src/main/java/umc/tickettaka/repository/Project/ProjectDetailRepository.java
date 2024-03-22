package umc.tickettaka.repository.Project;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import umc.tickettaka.domain.Project;
import umc.tickettaka.domain.QTimeline;
import umc.tickettaka.domain.Timeline;
import umc.tickettaka.payload.exception.GeneralException;
import umc.tickettaka.payload.status.ErrorStatus;

import static umc.tickettaka.domain.QProject.project;

@RequiredArgsConstructor
public abstract class ProjectDetailRepository implements ProjectRepository{

    private final ProjectRepository projectRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Project findProjectById(Long id)
    {
        return projectRepository.findById(id)
                .orElseThrow(() -> new GeneralException(ErrorStatus.PROJECT_NOT_FOUND));
    }


    @Override
    public Project findByTimeline(@Param("timeline") Timeline timeline)
    {
        return jpaQueryFactory.selectFrom(project)
                .join(project.timelineList, QTimeline.timeline)
                .where(QTimeline.timeline.eq(timeline))
                .fetchOne();
    }
}
