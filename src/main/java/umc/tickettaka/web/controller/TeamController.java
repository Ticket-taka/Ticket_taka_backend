package umc.tickettaka.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.tickettaka.config.security.jwt.AuthUser;
import umc.tickettaka.converter.InvitationConverter;
import umc.tickettaka.converter.TeamConverter;
import umc.tickettaka.domain.Invitation;
import umc.tickettaka.domain.Member;
import umc.tickettaka.domain.Team;
import umc.tickettaka.payload.ApiResponse;
import umc.tickettaka.service.InvitationCommandService;
import umc.tickettaka.service.MemberQueryService;
import umc.tickettaka.service.TeamQueryService;
import umc.tickettaka.web.dto.request.InvitationRequestDto;
import umc.tickettaka.web.dto.request.TeamRequestDto;
import umc.tickettaka.web.dto.response.InvitationResponseDto;
import umc.tickettaka.web.dto.response.TeamResponseDto;
import umc.tickettaka.service.TeamCommandService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
public class TeamController {

    private final TeamCommandService teamCommandService;
    private final TeamQueryService teamQueryService;
    private final MemberQueryService memberQueryService;
    private final InvitationCommandService invitationCommandService;

    @PostMapping(value = "/create", consumes = {MediaType.APPLICATION_JSON_VALUE , MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Team 생성 API", description = "Team 생성하는 API")
    public ApiResponse<TeamResponseDto.TeamDto> createTeam(
        @AuthUser Member member,
        @RequestPart(value = "image", required = false) MultipartFile image,
        @RequestPart(value = "request") TeamRequestDto.TeamDto request) throws IOException {
        Team team = teamCommandService.createTeam(member, image, request);
        return ApiResponse.onCreate(TeamConverter.toTeamResultDto(team));
    }

    @GetMapping("")
    @Operation(summary = "생성된 Team 조회 API", description = "생성된 Team 조회하는 API")
    public ApiResponse<TeamResponseDto.TeamListDto> getTeamList() {
        List<Team> teamList = teamQueryService.findAll();
        TeamResponseDto.TeamListDto teamListDto = TeamConverter.toTeamListDto(teamList);
        return ApiResponse.onSuccess(teamListDto);
    }

    @GetMapping("/{teamsId}")
    @Operation(summary = "특정 팀 조회 API",description = "특정 팀 조회하는 API")
    @Parameters({
            @Parameter(name = "teamsId", description = "팀의 아이디, path variable 입니다.")
    })
    public ApiResponse<TeamResponseDto.TeamDto> getTeam(@PathVariable(name = "teamsId") Long teamsId ) {
        Team team = teamQueryService.findTeam(teamsId);
        return ApiResponse.onSuccess(TeamConverter.toTeamResultDto(team));
    }

    @PatchMapping(value = "/{teamsId}/update", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "팀 정보 수정 API", description = "팀 정보를 업데이트하는 API")
    @Parameters({
            @Parameter(name = "teamsId", description = "팀의 아이디, path variable 입니다.")
    })
    public ApiResponse<TeamResponseDto.TeamDto> updateTeam(
            @PathVariable(name = "teamsId") Long teamsId,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestPart TeamRequestDto.TeamDto request) throws IOException{
        Team updatedTeam = teamCommandService.updateTeam(teamsId, image, request);
        return ApiResponse.onSuccess(TeamConverter.toTeamResultDto(updatedTeam));
    }

    @DeleteMapping("/{teamsId}")
    @Operation(summary = "팀 삭제 API", description = "팀을 삭제하는 API, 삭제하면 Response는 가입된 전체 팀이 조회됩니다.")
    @Parameters({
            @Parameter(name = "teamsId", description = "팀의 아이디, path variable 입니다.")
    })
    public ApiResponse<TeamResponseDto.TeamListDto> deleteTeam(@PathVariable(name = "teamsId") Long teamsId) throws IOException{
        teamCommandService.deleteTeam(teamsId);
        List<Team> teamList = teamQueryService.findAll();
        TeamResponseDto.TeamListDto teamListDto = TeamConverter.toTeamListDto(teamList);
        return ApiResponse.onSuccess(teamListDto);
    }

    @PostMapping("/{teamsId}/invite")
    @Operation(summary = "팀에 멤버 초대 API", description = "팀에 멤버 초대 API")
    @Parameters({
            @Parameter(name = "teamsId", description = "팀의 아이디, path variable 입니다.")
    })
    public ApiResponse<InvitationResponseDto.InvitationDto> inviteTeam(
            @PathVariable(name = "teamsId") Long teamsId,
            @AuthUser Member sender,
            @RequestBody InvitationRequestDto.CreateInvitationDto request) throws IOException {

        Team team = teamQueryService.findTeam(teamsId);
        Member receiver = memberQueryService.findByUsername(request.getReceiverUsername());
        Invitation invitation = invitationCommandService.sendInvitation(sender, team, receiver);

        return ApiResponse.onSuccess(InvitationConverter.invitationDto(invitation));
    }
}