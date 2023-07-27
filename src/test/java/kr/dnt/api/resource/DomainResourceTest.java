package kr.dnt.api.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.dnt.api.ApiDocumentationUtils;
import kr.dnt.api.dto.DomainDto;
import kr.dnt.api.service.DomainService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
 * fileName       : DomainResourceTest
 * author         : dmsruf
 * date           : 2023/07/05
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/05        dmsruf       최초 생성
 */

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest()
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DomainResourceTest {

    // MockMvc를 build 없이 주입받을 떄 사용
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    DomainService domainService; // 이 Test Controller인 TestController에서 사용하는 TestService를 @MockBean으로 Mock 형태의 객체로 생성해서 사용

    @Autowired
    private ObjectMapper objectMapper;

//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;


    @Test
    void apiGetCodeList() throws Exception {

        //given
        String token = "";

        List<DomainDto> response = new ArrayList<>();
        response.add(DomainDto.builder()
                .id(1L)
                .project("test_project")
                .domain("테스트")
                .lang("ko")
                .code("test")
                .abbreviation("tst")
                .description("테스트")
                .dataType("String")
                .usedCount(0)
                .hash("1ead357faf1dcf34d8242381a05b77f7bf58a5ac5626b25f13230bcd3629166c357e799edce3a7a3a7536eeb4f22df765d3914a60c95dae8c39f70bff7ed89e0")
                .insertDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build());
        response.add(DomainDto.builder()
                .id(1L)
                .project("test_project")
                .domain("테스트 프로젝트")
                .lang("ko")
                .code("test pj")
                .abbreviation("tst_pj")
                .description("테스트 프로젝트")
                .dataType("String")
                .usedCount(0)
                .hash("fce7dae5e221be1f92eb5fbb8ae01d6b31d27336fa5fc9b5aae72dda7fc98f04794b1cab402c2a89896d700764309babebd4fa56d68d32911dbc4c6652a567af")
                .insertDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build());

        given(domainService.findListByCode(
                any(Integer.class),
                any(Integer.class),
                any(String.class)
        )).willReturn(response);

        //when
        ResultActions result = this.mockMvc.perform(
                RestDocumentationRequestBuilders.get("/v1/domain")
                        .header("X-AUTH-TOKEN", token)
                        .param("page", "0")
                        .param("limit", "10")
                        .param("search", "test")
                        .param("lang", "en")
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isOk())
                .andDo(document("test-apiGetCodeList",
                        ApiDocumentationUtils.getDocumentRequest(),
                        ApiDocumentationUtils.getDocumentResponse(),
                        ApiDocumentationUtils.getRequestHeaders(),
                        requestParameters(
                                parameterWithName("page").description("페이지 시작"),
                                parameterWithName("limit").description("제한 개수"),
                                parameterWithName("search").description("검색어"),
                                parameterWithName("lang").description("검색어의 언어")
                        ),
                        ApiDocumentationUtils.setResponseFields().and(
                                fieldWithPath("data[]").type(JsonFieldType.ARRAY).description("리턴 데이터"),
                                fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("고유번호"),
                                fieldWithPath("data[].project").type(JsonFieldType.STRING).description("프로젝트명"),
                                fieldWithPath("data[].domain").type(JsonFieldType.STRING).description("도메인 데이터"),
                                fieldWithPath("data[].lang").type(JsonFieldType.STRING).description("도메인 언어"),
                                fieldWithPath("data[].code").type(JsonFieldType.STRING).description("코드명"),
                                fieldWithPath("data[].abbreviation").type(JsonFieldType.STRING).description("약어"),
                                fieldWithPath("data[].description").type(JsonFieldType.STRING).description("설명"),
                                fieldWithPath("data[].dataType").type(JsonFieldType.STRING).description("데이터 타입"),
                                fieldWithPath("data[].usedCount").type(JsonFieldType.NUMBER).description("사용 횟수"),
                                fieldWithPath("data[].hash").type(JsonFieldType.STRING).description("해시값"),
                                fieldWithPath("data[].insertDate").type(JsonFieldType.STRING).description("등록인"),
                                fieldWithPath("data[].updateDate").type(JsonFieldType.STRING).description("수정일")
                        )
                ));
    }

    @Test
    void apiGetTransactionCode() throws Exception {

        //given
        String token = "";

        List<DomainDto> response = new ArrayList<>();
        response.add(DomainDto.builder()
                .id(1L)
                .project("test_project")
                .domain("테스트")
                .lang("ko")
                .code("time")
                .abbreviation("tst")
                .description("테스트")
                .dataType("String")
                .usedCount(0)
                .hash("1ead357faf1dcf34d8242381a05b77f7bf58a5ac5626b25f13230bcd3629166c357e799edce3a7a3a7536eeb4f22df765d3914a60c95dae8c39f70bff7ed89e0")
                .insertDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build());

        given(domainService.findListByDomain(
                any(Integer.class),
                any(Integer.class),
                any(String.class),
                any(String.class)
        )).willReturn(response);

        //when
        ResultActions result = this.mockMvc.perform(
                RestDocumentationRequestBuilders.get("/v1/domain/transactions")
                        .header("X-AUTH-TOKEN", token)
                        .param("search", "테스트")
                        .param("lang", "ko")
                        .param("project", "test_project")
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isOk())
                .andDo(document("test-apiGetTransactionCode",
                        ApiDocumentationUtils.getDocumentRequest(),
                        ApiDocumentationUtils.getDocumentResponse(),
                        ApiDocumentationUtils.getRequestHeaders(),
                        requestParameters(
                                parameterWithName("search").description("검색어"),
                                parameterWithName("lang").description("검색어의 언어"),
                                parameterWithName("project").description("프로젝트명")
                        ),
                        ApiDocumentationUtils.setResponseFields().and(
                                fieldWithPath("data[]").type(JsonFieldType.ARRAY).description("리턴 데이터"),
                                fieldWithPath("data[].domain").type(JsonFieldType.STRING).description("도메인"),
                                fieldWithPath("data[].code").type(JsonFieldType.STRING).description("영문 코드값"),
                                fieldWithPath("data[].description").type(JsonFieldType.STRING).description("설명"),
                                fieldWithPath("data[].dataType").type(JsonFieldType.STRING).description("데이터 타입")
                        )
                ));
    }

    @Test
    void apiPostDomain() throws Exception {

        //given
        String token = "";

        DomainDto request = DomainDto.builder()
                .project("test_project")
                .domain("날짜")
                .lang("ko")
                .code("date")
                .abbreviation("dt")
                .description("날짜")
                .dataType("String")
                .build();
        DomainDto response = DomainDto.builder()
                .id(14L)
                .project("test_project")
                .domain("날짜")
                .lang("ko")
                .code("date")
                .abbreviation("dt")
                .description("날짜")
                .dataType("String")
                .usedCount(0)
                .hash("885b01df9eaeb56991701dd02bf5cefa46be187942e54f21aeed278b9287ad9d3af539a564ac204ade6eb1d443db2afd919c0c7c5008a847e470f5ec3c33ed91")
                .insertDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        DomainDto hashResponse = null;

        given(domainService.findByHash(any(String.class))).willReturn(hashResponse);
        given(domainService.save(any(DomainDto.class))).willReturn(response);

        //when
        ResultActions result = this.mockMvc.perform(
                RestDocumentationRequestBuilders.post("/v1/domain")
                        .header("X-AUTH-TOKEN", token)
                        .content(objectMapper.writeValueAsString(request))
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );

        //then
        result.andExpect(status().isOk())
                .andDo(document("test-apiPostDomain",
                        ApiDocumentationUtils.getDocumentRequest(),
                        ApiDocumentationUtils.getDocumentResponse(),
                        ApiDocumentationUtils.getRequestHeaders(),
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.STRING).description("고유번호").optional(),
                                fieldWithPath("project").type(JsonFieldType.STRING).description("프로젝트명"),
                                fieldWithPath("domain").type(JsonFieldType.STRING).description("도메인 데이터"),
                                fieldWithPath("lang").type(JsonFieldType.STRING).description("도메인 언어"),
                                fieldWithPath("lang").type(JsonFieldType.STRING).description("도메인 언어"),
                                fieldWithPath("code").type(JsonFieldType.STRING).description("코드명"),
                                fieldWithPath("dataType").type(JsonFieldType.STRING).description("데이터 타입"),
                                fieldWithPath("abbreviation").type(JsonFieldType.STRING).description("약어"),
                                fieldWithPath("description").type(JsonFieldType.STRING).description("설명"),
                                fieldWithPath("usedCount").type(JsonFieldType.NUMBER).description("사용 횟수").optional(),
                                fieldWithPath("hash").type(JsonFieldType.STRING).description("해시값").optional(),
                                fieldWithPath("insertDate").type(JsonFieldType.OBJECT).description("등록일").optional(),
                                fieldWithPath("updateDate").type(JsonFieldType.OBJECT).description("수정일").optional()
                        ),
                        ApiDocumentationUtils.setResponseFields().and(
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("리턴 데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("고유번호"),
                                fieldWithPath("data.project").type(JsonFieldType.STRING).description("프로젝트명"),
                                fieldWithPath("data.domain").type(JsonFieldType.STRING).description("도메인 데이터"),
                                fieldWithPath("data.lang").type(JsonFieldType.STRING).description("도메인 언어"),
                                fieldWithPath("data.code").type(JsonFieldType.STRING).description("코드명"),
                                fieldWithPath("data.abbreviation").type(JsonFieldType.STRING).description("약어"),
                                fieldWithPath("data.description").type(JsonFieldType.STRING).description("설명"),
                                fieldWithPath("data.dataType").type(JsonFieldType.STRING).description("데이터 타입"),
                                fieldWithPath("data.usedCount").type(JsonFieldType.NUMBER).description("사용 횟수"),
                                fieldWithPath("data.hash").type(JsonFieldType.STRING).description("해시값"),
                                fieldWithPath("data.insertDate").type(JsonFieldType.STRING).description("등록일"),
                                fieldWithPath("data.updateDate").type(JsonFieldType.STRING).description("수정일")
                        )
                ));
    }
}