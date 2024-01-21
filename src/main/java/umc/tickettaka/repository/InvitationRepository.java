package umc.tickettaka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.tickettaka.domain.Invitation;
import umc.tickettaka.domain.Member;
import umc.tickettaka.domain.Team;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    boolean existsBySenderAndReceiverAndTeam(Member sender, Member receiver, Team team);
}
