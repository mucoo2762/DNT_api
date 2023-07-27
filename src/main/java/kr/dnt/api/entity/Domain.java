package kr.dnt.api.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

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
 * packageName    : kr.dnt.api.entity
 * fileName       : Domain
 * author         : dmsruf
 * date           : 2023/07/03
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/03        dmsruf       최초 생성
 */

@Data
@Entity
@Builder
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Domain extends AbstractMappedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    // 프로젝트명
    @Column(name = "project", columnDefinition = "varchar(255) comment '프로젝트명'")
    private String project;

    // search data.
    @Column(name = "domain", nullable = false, columnDefinition = "varchar(255) comment 'search data'")
    private String domain;

    // domain의 언어. en/ko...
    @Column(name = "lang", nullable = false, columnDefinition = "varchar(255) comment 'domain의 언어. (en/ko)'")
    private String lang;

    // code는 영어로 작성. domain+lang을 code(en)로 변환하거나 code(en)를 domain으로 변환..
    @Column(name = "code", nullable = false, columnDefinition = "varchar(255) comment 'domain + lang -> get code'")
    private String code;

    // 약어
    @Column(name = "abbreviation", columnDefinition = "varchar(255) comment '약어'")
    private String abbreviation;

    // 설명
    @Column(name = "description", columnDefinition = "varchar(500) comment '설명'")
    private String description;

    // String, date, ...
    @Column(name = "data_type", columnDefinition = "varchar(50) comment '데이터 타입. String,date ...'")
    private String dataType;

    // 사용 카운트
    @Column(name = "used_count", columnDefinition = "bigint(20) default 0 comment '사용 카운트'")
    @ColumnDefault("0")
    private long usedCount;

    // domain + lang + code + description + abbreviation
    @Column(name = "hash", nullable = false, unique = true, columnDefinition = "varchar(255) comment 'domain + lang + code + description + abbreviation'")
    private String hash;

}
