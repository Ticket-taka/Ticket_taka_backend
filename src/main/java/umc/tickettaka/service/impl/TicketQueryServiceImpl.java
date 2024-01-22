package umc.tickettaka.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.tickettaka.domain.ticket.Ticket;
import umc.tickettaka.payload.exception.GeneralException;
import umc.tickettaka.payload.status.ErrorStatus;
import umc.tickettaka.repository.TicketRepository;
import umc.tickettaka.service.TicketQueryService;

@Service
@RequiredArgsConstructor
public class TicketQueryServiceImpl implements TicketQueryService {

    private final TicketRepository ticketRepository;

    @Override
    public List<Ticket> findAllByTimelineId(Long timelineId) {
        return ticketRepository.findAllByTimelineIdOrderByEndTime(timelineId);
    }

    @Override
    public Ticket findById(Long id) {
        return ticketRepository.findById(id)
            .orElseThrow(() -> new GeneralException(ErrorStatus.TICKET_NOT_FOUND));
    }
}