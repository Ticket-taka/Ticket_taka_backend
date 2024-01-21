package umc.tickettaka.web.dto.request;

import lombok.Getter;

public class InvitationRequestDto {

    @Getter
    public static class InvitationDto {
        String receiverUsername;
    }
}
