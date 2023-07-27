package kr.dnt.api.service.impl;

import kr.dnt.api.dto.DomainDto;
import kr.dnt.api.entity.Domain;
import kr.dnt.api.exception.ApiMessageException;
import kr.dnt.api.repository.DomainRepository;
import kr.dnt.api.service.DomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
 * fileName       : DomainServiceImpl
 * author         : dmsruf
 * date           : 2023/07/03
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/03        dmsruf       최초 생성
 */

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class DomainServiceImpl implements DomainService {

    private final DomainRepository domainRepository;
    private final ModelMapper modelMapper;


    @Override
    public DomainDto find(Long id) {
        Domain domain = domainRepository.findById(id).orElseThrow(() -> new ApiMessageException("데이터를 찾을 수 없습니다."));
        if (domain == null) {
            return null;
        } else {
            return modelMapper.map(domain, DomainDto.class);
        }
    }

    @Override
    public List<DomainDto> findList(int page, int limit, String search, String lang, String project) {
        return domainRepository.findList(PageRequest.of(page, limit), search, lang, project);
    }

    @Override
    public List<DomainDto> findListByDomain(int page, int limit, String search, String project) {
        return domainRepository.findListByDomain(PageRequest.of(page, limit), search, project);
    }

    @Override
    public List<DomainDto> findListByCode(int page, int limit, String search) {
        return domainRepository.findListByCode(PageRequest.of(page, limit), search);
    }

    @Override
    public DomainDto findOneByDomain(String search, String project) {
        return domainRepository.findOneByDomain(search, project);
    }

    @Override
    public DomainDto findOneByCode(String search, String project) {
        return domainRepository.findOneByCode(search, project);
    }

    @Override
    public List<String> findProject() {
        return domainRepository.findProject();
    }

    @Override
    public DomainDto findByHash(String hash) {
        return domainRepository.findByHash(hash);
    }

    @Override
    public DomainDto save(DomainDto domainDto) {
        if (domainDto == null) {
            return null;
        }

        Domain domain = modelMapper.map(domainDto, Domain.class);
        domain = domainRepository.save(domain);
        return modelMapper.map(domain, DomainDto.class);
    }

    @Override
    public void delete(Long id) throws Exception {
        domainRepository.deleteById(id);
    }
}
