package umc.tickettaka.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.tickettaka.domain.common.BaseEntity;
import umc.tickettaka.web.dto.request.ProjectRequestDto;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Project extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String name;
    @Column(length = 500)
    private String description;
    @Column(length = 500)
    private String imageUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Project update(String imageUrl, ProjectRequestDto.CreateProjectDto projectUpdateDto) {
        this.imageUrl = imageUrl;

        String updateName = projectUpdateDto.getName();
        if(updateName != null) this.name = updateName;

        String updateDescription = projectUpdateDto.getDescription();
        if(updateDescription != null) this.description = updateDescription;

        return this;
    }
}
