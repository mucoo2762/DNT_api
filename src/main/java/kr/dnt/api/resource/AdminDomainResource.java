package kr.dnt.api.resource;

import kr.dnt.api.dto.DomainDto;
import kr.dnt.api.service.DomainService;
import kr.dnt.api.service.common.CommonResult;
import kr.dnt.api.service.common.ListResult;
import kr.dnt.api.service.common.ResponseService;
import kr.dnt.api.service.common.SingleResult;
import kr.dnt.api.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
 * fileName       : AdminDomainResource
 * author         : dmsruf
 * date           : 2023/07/05
 * description    : 관리자 사용 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/05        dmsruf       최초 생성
 */

@RestController
@RequestMapping("/v1/admin/domain")
@Slf4j
@RequiredArgsConstructor
public class AdminDomainResource {

    private final DomainService domainService;
    private final ResponseService responseService;


    @GetMapping("/{id}")
    public SingleResult<DomainDto> apiGetDomain(
            @PathVariable("id") @Min(1) Long id
    ) {
        return responseService.getSingleResult(domainService.find(id));
    }

    @GetMapping
    public ListResult<DomainDto> apiGetDomainList(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(value = "search", required = false, defaultValue = "") String search,
            @RequestParam(value = "lang", required = false, defaultValue = "") String lang,
            @RequestParam(value = "project", required = false, defaultValue = "") String project
    ) {
        return responseService.getListResult(domainService.findList(page, limit, search, lang, project));
    }

    @GetMapping("/project")
    public ListResult<String> apiGetProjectGroup() {
        return responseService.getListResult(domainService.findProject());
    }

    @PostMapping
    public SingleResult<DomainDto> apiPostDomain(
            @RequestBody @NotNull(message = "Input must not NULL") @Valid DomainDto domainDto
    ) {
        if (domainDto.getInsertDate() == null) {
            domainDto.setInsertDate(LocalDateTime.now());
        }
        if (domainDto.getUpdateDate() == null) {
            domainDto.setUpdateDate(LocalDateTime.now());
        }

        // hash 생성, 추가
        String combineStr = domainDto.getDomain() + domainDto.getLang() + domainDto.getCode() + domainDto.getAbbreviation() + domainDto.getDescription();
        String hash = "";
        try {
            hash = EncryptUtil.encryptSha512(combineStr);
            // hash로 데이터 조회
            DomainDto findDto = domainService.findByHash(hash);
            if (findDto != null && findDto.getId() != null && findDto.getId().longValue() > 0) {
                return responseService.getFailSingleResult(-1, "같은 값의 데이터가 존재합니다.", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 데이터 없으면 hash 저장 가능
        domainDto.setHash(hash);
        return responseService.getSingleResult(domainService.save(domainDto));
    }

    @PutMapping
    public SingleResult<DomainDto> apiPutDomain(
            @RequestBody @NotNull(message = "Input must not NULL") @Valid DomainDto domainDto
    ) {
        if (domainDto.getId() == null || domainDto.getId().longValue() <= 0) {
            return responseService.getFailSingleResult(-1, "유효하지 않은 도메인 정보입니다.", null);
        }

        // hash 생성, 수정
        String combineStr = domainDto.getDomain() + domainDto.getLang() + domainDto.getCode() + domainDto.getAbbreviation() + domainDto.getDescription();
        String hash = "";
        try {
            hash = EncryptUtil.encryptSha512(combineStr);
            // hash로 데이터 조회 (기존 도메인의 해시값이었을 경우 저장 가능)
            DomainDto findDto = domainService.findByHash(hash);
            if (findDto != null && findDto.getId() != null && findDto.getId().longValue() > 0 && !findDto.getId().equals(domainDto.getId())) {
                return responseService.getFailSingleResult(-1, "같은 값의 데이터가 존재합니다.", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 데이터 없으면 hash 저장 가능
        domainDto.setHash(hash);
        domainDto.setUpdateDate(LocalDateTime.now());
        return responseService.getSingleResult(domainService.save(domainDto));
    }

    @DeleteMapping("/{id}")
    public CommonResult apiDeleteDomain(
            @PathVariable("id") @Min(1) Long id
    ) {
        try {
            domainService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return responseService.getFailResult(-1, "삭제에 실패했습니다.");
        }
        return responseService.getSuccessResult();
    }
}
