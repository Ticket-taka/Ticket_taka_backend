package umc.tickettaka.web.dto.request;

import lombok.Getter;

public class InvitationRequestDto {

    @Getter
    public static class CreateInvitationDto {
        String receiverUsername;
        boolean isAccepted;
    }
}
