package kr.dnt.api.resource;

import kr.dnt.api.dto.DomainDto;
import kr.dnt.api.dto.DomainResp;
import kr.dnt.api.exception.ApiMessageException;
import kr.dnt.api.service.DomainService;
import kr.dnt.api.service.common.CommonResult;
import kr.dnt.api.service.common.ListResult;
import kr.dnt.api.service.common.ResponseService;
import kr.dnt.api.service.common.SingleResult;
import kr.dnt.api.util.EncryptUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
 * fileName       : DomainResource
 * author         : dmsruf
 * date           : 2023/07/03
 * description    : 플러그인 사용 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/03        dmsruf       최초 생성
 */

@RestController
@RequestMapping("/v1/domain")
@Slf4j
@Validated
@RequiredArgsConstructor
public class DomainResource {

    private final DomainService domainService;
    private final ResponseService responseService;


    // project, domain, lang 조건에 맞는 code 리스트 리턴
    @GetMapping
    public ListResult<DomainDto> apiGetCodeList(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(value = "search", required = false, defaultValue = "") String search,
            @RequestParam(value = "lang", required = true) @NotBlank @Pattern(regexp = "^(en|ko)$", message = "en/ko(en:영문. ko:한글) 값만 허용됩니다.") String lang
    ) {

        List<DomainDto> findList = new ArrayList<>();
        if (lang.equalsIgnoreCase("en")) {
            // search의 언어가 en이면, search=code 로 검색
            findList = domainService.findListByCode(page, limit, search);

        } else {
            // search의 언어가 en 외의 언어이면, search=domain 으로 검색
            findList = domainService.findListByDomain(page, limit, search, "");
        }

        return responseService.getListResult(findList);
    }

    // domain, lang 조건에 맞게 변환된 code 리턴
    @GetMapping("/transactions")
    public ListResult<DomainResp> apiGetTransactionCode(
            @RequestParam(value = "search", required = false, defaultValue = "") String search,
            @RequestParam(value = "lang", required = true) @NotBlank @Pattern(regexp = "^(en|ko)$", message = "en/ko(en:영문. ko:한글) 값만 허용됩니다.") String lang,
            @RequestParam(value = "project", required = true) @NotBlank String project
    ) {
        /**
         * !!! 프로젝트명에 해당되는 데이터 중 데이터 변환한 코드 찾기
         * 1. 리퀘스트 : search=테스트, lang=ko
         *  -> search의 언어가 ko 이므로, search 는 domain 과 비교
         *  -> search=domain 이고 project 가 일치하는 dto 검색
         *  -> dto의 code 리턴
         * 2. 리퀘스트 : search=test, lang=en
         *  -> search의 언어가 en 이므로, search 는 code 와 비교
         *  -> search=code 이고 project 가 일치하는 dto 검색
         *  -> dto의 domain 리턴
         */
        List<DomainResp> list = new ArrayList<>();
        if (lang.equalsIgnoreCase("en")) {
            // search의 언어가 en이면, search=code 로 검색. domain을 리턴
            DomainDto findDomain = domainService.findOneByCode(search, project);
            if (findDomain != null) {
                list.add(DomainResp.builder()
                        .domain(findDomain.getDomain())
                        .code(findDomain.getCode())
                        .description(findDomain.getDescription())
                        .dataType(findDomain.getDataType())
                        .build());
            }

        } else {
            // search의 언어가 en 외의 언어이면, search=domain 으로 검색. code를 리턴
            List<DomainDto> findDomainList = domainService.findListByDomain(0, 10, search, project);
            if (findDomainList != null) {
                for (DomainDto domainDto : findDomainList) {
                    if (domainDto == null) {
                        continue;
                    }
                    list.add(DomainResp.builder()
                            .domain(domainDto.getDomain())
                            .code(domainDto.getCode())
                            .description(domainDto.getDescription())
                            .dataType(domainDto.getDataType())
                            .build());
                }
            }
        }

        return responseService.getListResult(list);
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
}
