package kr.dnt.api.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.BooleanPath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.dnt.api.dto.DomainDto;
import kr.dnt.api.entity.Domain;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;


import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static kr.dnt.api.entity.QDomain.domain1;

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
 * packageName    : kr.dnt.api.repository
 * fileName       : DomainRepoCommonImpl
 * author         : dmsruf
 * date           : 2023/07/03
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/03        dmsruf       최초 생성
 */

@Repository
@Transactional
public class DomainRepoCommonImpl implements DomainRepoCommon {
    private final JPAQueryFactory queryFactory;
    private EntityManager em;
    private final ModelMapper modelMapper;

    public DomainRepoCommonImpl(EntityManager em, ModelMapper modelMapper) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<DomainDto> findList(Pageable pageable, String search, String lang, String project) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(eqStringColumn(domain1.lang, lang, null));
        booleanBuilder.and(eqStringColumn(domain1.project, project, null));
        if (!ObjectUtils.isEmpty(search)) {
            booleanBuilder.and(
                    containStringColumn(domain1.domain, search, " ")
                            .or(containStringColumn(domain1.code, search, " "))
            );
        }

        List<Domain> results = queryFactory
                .select(domain1)
                .from(domain1)
                .where(booleanBuilder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        if (results == null || results.size() <= 0) {
            return new ArrayList<>();

        } else {
            List<DomainDto> list = new ArrayList<>();
            for (Domain entity : results) {
                list.add(modelMapper.map(entity, DomainDto.class));
            }
            return list;
        }
    }

    @Override
    public List<DomainDto> findListByDomain(Pageable pageable, String search, String project) {
        if (ObjectUtils.isEmpty(search)) {
            return new ArrayList<>();
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(eqStringColumn(domain1.project, project, null));
        booleanBuilder.and(
                containStringColumn(domain1.domain, search, " ")
                        .or(containStringColumn(domain1.description, search, " "))
        );

        List<Domain> results = queryFactory
                .select(domain1)
                .from(domain1)
                .where(booleanBuilder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        if (results == null || results.size() <= 0) {
            return new ArrayList<>();

        } else {
            List<DomainDto> list = new ArrayList<>();
            for (Domain entity : results) {
                list.add(modelMapper.map(entity, DomainDto.class));
            }
            return list;
        }
    }

    @Override
    public List<DomainDto> findListByCode(Pageable pageable, String search) {
        if (ObjectUtils.isEmpty(search)) {
            return new ArrayList<>();
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(containStringColumn(domain1.code, search, " "));

        List<Domain> results = queryFactory
                .select(domain1)
                .from(domain1)
                .where(booleanBuilder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        if (results == null || results.size() <= 0) {
            return new ArrayList<>();

        } else {
            List<DomainDto> list = new ArrayList<>();
            for (Domain entity : results) {
                list.add(modelMapper.map(entity, DomainDto.class));
            }
            return list;
        }
    }

    @Override
    public DomainDto findOneByDomain(String search, String project) {
        if (ObjectUtils.isEmpty(search) || ObjectUtils.isEmpty(project)) {
            return null;
        }

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(eqStringColumn(domain1.domain, search, " "));
        booleanBuilder.and(eqStringColumn(domain1.project, project, null));

        Domain result = queryFactory
                .select(domain1)
                .from(domain1)
                .where(booleanBuilder)
                .fetchFirst();
        if (result == null) {
            return null;
        } else {
            return modelMapper.map(result, DomainDto.class);
        }
    }

    @Override
    public DomainDto findOneByCode(String search, String project) {
        if (ObjectUtils.isEmpty(search) || ObjectUtils.isEmpty(project)) {
            return null;
        }

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(eqStringColumn(domain1.code, search, " "));
        booleanBuilder.and(eqStringColumn(domain1.project, project, null));

        Domain result = queryFactory
                .select(domain1)
                .from(domain1)
                .where(booleanBuilder)
                .fetchFirst();
        if (result == null) {
            return null;
        } else {
            return modelMapper.map(result, DomainDto.class);
        }
    }

    @Override
    public List<String> findProject() {
        return queryFactory
                .select(domain1.project)
                .distinct()
                .from(domain1)
                .where(domain1.project.isNotNull(), domain1.project.isNotEmpty())
                .orderBy(domain1.project.asc())
                .fetch();
    }

    @Override
    public DomainDto findByHash(String hash) {
        if (ObjectUtils.isEmpty(hash)) {
            return null;
        }

        Domain result = queryFactory
                .select(domain1)
                .from(domain1)
                .where(domain1.hash.eq(hash))
                .fetchFirst();
        if (result == null) {
            return null;
        } else {
            return modelMapper.map(result, DomainDto.class);
        }
    }


    private BooleanExpression eqLongColumn(NumberPath<Long> column, Long value) {
        if (column == null || value == null || value.longValue() <= 0) {
            return null;
        }

        return column.eq(value);
    }

    private BooleanBuilder eqStringColumn(StringPath column, String value, String separator) {
        if (column == null || ObjectUtils.isEmpty(value)) {
            return null;
        }

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (ObjectUtils.isEmpty(separator)) {
            booleanBuilder.and(column.eq(value));

        } else {
            for (String str : value.split(separator)) {
                booleanBuilder.or(column.eq(str));
            }
        }

        return booleanBuilder;
    }

    private BooleanBuilder containStringColumn(StringPath column, String value, String separator) {
        if (column == null || ObjectUtils.isEmpty(value)) {
            return null;
        }

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (ObjectUtils.isEmpty(separator)) {
            booleanBuilder.and(column.containsIgnoreCase(value));

        } else {
            for (String str : value.split(separator)) {
                booleanBuilder.or(column.containsIgnoreCase(str));
            }
        }

        return booleanBuilder;
    }
}
