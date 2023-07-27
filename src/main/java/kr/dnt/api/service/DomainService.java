package kr.dnt.api.service;

import kr.dnt.api.dto.DomainDto;
import org.springframework.data.domain.Pageable;

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
 * packageName    : kr.dnt.api.service.impl
 * fileName       : DomainService
 * author         : dmsruf
 * date           : 2023/07/03
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/03        dmsruf       최초 생성
 */
public interface DomainService {

    DomainDto find(Long id);

    List<DomainDto> findList(int page, int limit, String search, String lang, String project);

    List<DomainDto> findListByDomain(int page, int limit, String search, String project);

    List<DomainDto> findListByCode(int page, int limit, String search);

    DomainDto findOneByDomain(String search, String project);

    DomainDto findOneByCode(String search, String project);

    List<String> findProject();

    DomainDto findByHash(String hash);

    DomainDto save(DomainDto domainDto);

    void delete(Long id) throws Exception;
}
