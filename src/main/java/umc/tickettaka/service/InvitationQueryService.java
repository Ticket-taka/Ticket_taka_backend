package umc.tickettaka.service;

import umc.tickettaka.domain.Invitation;
import umc.tickettaka.domain.Member;

import java.util.List;

public interface InvitationQueryService {
    Invitation findInvitation(Long id);
    List<Invitation> findAll();
}
