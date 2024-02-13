package umc.tickettaka.domain.mapping;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.tickettaka.domain.Member;
import umc.tickettaka.domain.Team;
import umc.tickettaka.domain.common.BaseEntity;
import umc.tickettaka.domain.enums.Color;
import umc.tickettaka.payload.exception.GeneralException;
import umc.tickettaka.payload.status.ErrorStatus;
import umc.tickettaka.web.dto.request.MemberTeamRequestDto;

import java.util.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberTeam extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Color color;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public void updateColor(MemberTeamRequestDto.UpdateColorDto updateDto) {
        if (updateDto != null) {
            Color newColor = updateDto.getColor();

            if (newColor != null) {
                this.color = newColor;
            }
        }
    }

    @Transient
    private static final Set<Color> allocatedColorList = new HashSet<>();

    public static Color getUnusedRandomColor() {
        List<Color> allColors = Arrays.asList(Color.values());

        for (Color color : allColors) {
            if (!allocatedColorList.contains(color)) {
                allocatedColorList.add(color);
                return color;
            }
        }

        throw new GeneralException(ErrorStatus.COLOR_ALREADY_USED_IN_TEAM);
    }
}