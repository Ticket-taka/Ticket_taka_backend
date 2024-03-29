package umc.tickettaka.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Schema(description = "member request dto")
public class MemberRequestDto {

    @Getter
    @Schema(name = "update dto")
    public static class UpdateDto {

        private String name;

        private String username;

        private String password;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TokenDto {
        String accessToken;
    }
}
