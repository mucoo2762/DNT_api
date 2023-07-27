package kr.dnt.api.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.dnt.api.dto.UserDto;
import kr.dnt.api.entity.User;
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

import static kr.dnt.api.entity.QUser.user;

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
 * fileName       : UserRepoCommonImpl
 * author         : dmsruf
 * date           : 2023/07/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/11        dmsruf       최초 생성
 */

@Repository
@Transactional
public class UserRepoCommonImpl implements UserRepoCommon {

    private final JPAQueryFactory queryFactory;
    private EntityManager em;
    private final ModelMapper modelMapper;

    public UserRepoCommonImpl(EntityManager em, ModelMapper modelMapper) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
        this.modelMapper = modelMapper;
    }


    @Override
    public Page<UserDto> findList(Pageable pageable, String name, String email, String security_key, String client_id) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(containStringColumn(user.name, name));
        booleanBuilder.and(containStringColumn(user.email, email));
        booleanBuilder.and(eqStringColumn(user.securityKey, security_key));
        booleanBuilder.and(eqStringColumn(user.clientId, client_id));

        long totalCnt = queryFactory
                .select(user.seq)
                .from(user)
                .where(booleanBuilder)
                .fetchCount();
        if (totalCnt <= 0) {
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        }

        List<User> results = queryFactory
                .select(user)
                .from(user)
                .where(booleanBuilder)
                .orderBy(user.insertDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<UserDto> list = new ArrayList<>();
        for (User user1 : results) {
            list.add(modelMapper.map(user1, UserDto.class));
        }

        return new PageImpl<>(list, pageable, totalCnt);
    }

    private BooleanExpression eqStringColumn(StringPath column, String value) {
        if (column == null || ObjectUtils.isEmpty(value)) {
            return null;
        }

        return column.eq(value);
    }

    private BooleanExpression containStringColumn(StringPath column, String value) {
        if (column == null || ObjectUtils.isEmpty(value)) {
            return null;
        }

        return column.containsIgnoreCase(value);
    }
}
