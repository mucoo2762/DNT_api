package kr.dnt.api.resource;

import kr.dnt.api.dto.UserDto;
import kr.dnt.api.service.UserService;
import kr.dnt.api.service.common.CommonResult;
import kr.dnt.api.service.common.ResponseService;
import kr.dnt.api.service.common.SingleResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * -------------------------------------------------------------------------------------
 * ::::::'OO::::'OOO::::'OO:::'OO:'OO::::'OO:'OOOOOOOO:::'OOOOOOO::'OO::::'OO:'OO....OO:
 * :::::: OO:::'OO OO:::. OO:'OO:: OO::::.OO: OO.....OO:'OO.....OO: OO:::: OO: OOO...OO:
 * :::::: OO::'OO:..OO:::. OOOO::: OO::::.OO: OO::::.OO: OO::::.OO: OO:::: OO: OOOO..OO:
 * :::::: OO:'OO:::..OO:::. OO:::: OO::::.OO: OOOOOOOO:: OO::::.OO: OO:::: OO: OO.OO.OO:
 * OO:::: OO: OOOOOOOOO:::: OO:::: OO::::.OO: OO.. OO::: OO::::.OO: OO:::: OO: OO..OOOO:
 * :OO::::OO: OO.....OO:::: OO:::: OO::::.OO: OO::. OO:: OO::::.OO: OO:::: OO: OO:..OOO:
 * ::OOOOOO:: OO:::..OO:::: OO::::. OOOOOOO:: OO:::. OO:. OOOOOOO::. OOOOOOO:: OO::..OO:
 * :......:::..:::::..:::::..::::::.......:::..:::::..:::.......::::.......:::..::::..::
 * <p>
 * packageName    : kr.dnt.api.resource
 * fileName       : AdminUserResource
 * author         : dmsruf
 * date           : 2023/07/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/11        dmsruf       최초 생성
 */

@RestController
@RequestMapping("/v1/admin/users")
@Slf4j
@Validated
@RequiredArgsConstructor
public class AdminUserResource {

    private final UserService userService;
    private final ResponseService responseService;


    @GetMapping("/{seq}")
    public SingleResult<UserDto> apiGetUsers(
            @PathVariable("seq") @Min(1) Long seq
    ) {
        return responseService.getSingleResult(userService.findBySeq(seq));
    }

    @GetMapping
    public SingleResult<Page<UserDto>> apiGetUsersList(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "30") Integer limit,
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "email", required = false, defaultValue = "") String email,
            @RequestParam(value = "security_key", required = false, defaultValue = "") String security_key,
            @RequestParam(value = "client_id", required = false, defaultValue = "") String client_id
    ) {
        return responseService.getSingleResult(userService.findList(page, limit, name, email, security_key, client_id));
    }

    @PostMapping
    public SingleResult<UserDto> apiPostUsers(
            @RequestBody @NotNull(message = "Input must not NULL") @Valid UserDto userDto
    ) {
        if (userDto.getInsertDate() == null) {
            userDto.setInsertDate(LocalDateTime.now());
        }
        if (userDto.getUpdateDate() == null) {
            userDto.setUpdateDate(LocalDateTime.now());
        }

        try {
            userDto = userService.save(userDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (userDto == null || userDto.getSeq() == null || userDto.getSeq().longValue() <= 0) {
            return responseService.getFailSingleResult(-1, "유저 정보 저장에 실패했습니다.", null);
        } else {
            return responseService.getSingleResult(userDto);
        }
    }

    @PutMapping
    public SingleResult<UserDto> apiPutUsers(
            @RequestBody @NotNull(message = "Input must not NULL") @Valid UserDto userDto
    ) {
        if (userDto.getSeq() == null || userDto.getSeq().longValue() <= 0) {
            return responseService.getFailSingleResult(-1, "유효하지 않은 유저 정보입니다.", null);
        }

        userDto.setUpdateDate(LocalDateTime.now());
        return responseService.getSingleResult(userService.save(userDto));
    }

    @DeleteMapping("/{seq}")
    public CommonResult apiDeleteUsers(
            @PathVariable("seq") @Min(1) Long seq
    ) {
        try {
            userService.deleteBySeq(seq);
        } catch (Exception e) {
            e.printStackTrace();
            return responseService.getFailResult(-1, "삭제에 실패했습니다.");
        }
        return responseService.getSuccessResult();
    }
}
