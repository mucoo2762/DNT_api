package kr.dnt.api.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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
 * packageName    : kr.dnt.api.dto
 * fileName       : DomainDto
 * author         : dmsruf
 * date           : 2023/07/03
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/03        dmsruf       최초 생성
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DomainDto {

    private Long id;

    // 프로젝트명
    private String project;

    // search data
    @NotBlank
    private String domain;

    // domain의 언어. en/ko
    @NotBlank
    @Pattern(regexp = "^(en|ko)$", message = "en/ko(en:영문. ko:한글) 값만 허용됩니다.")
    private String lang;

    // domain + lang -> get code
    @NotBlank
    private String code;

    // 약어
    private String abbreviation;

    // 설명
    private String description;

    // String, date, ...
    private String dataType;

    // 사용 카운트
    @Builder.Default
    private long usedCount = 0L;

    // domain + lang + code + description + abbreviation
    private String hash;

    private LocalDateTime insertDate;

    private LocalDateTime updateDate;
}
