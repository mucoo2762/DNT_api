package kr.dnt.api;

import org.springframework.restdocs.headers.RequestHeadersSnippet;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

import java.util.List;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

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
 * ApiDocumentationUtils 의 설명을 작성해주세요.
 *
 * @author :
 * @packageName : jayuroun.common.ApiDocumentationUtils
 * @fileName :
 * @date :
 * @description :
 * =======================================================================
 * DATE                   AUTHOR                NOTE
 * -----------------------------------------------------------------------
 *
 */

public interface ApiDocumentationUtils {
    static OperationRequestPreprocessor getDocumentRequest() {
        return preprocessRequest(
                modifyUris()
                        .scheme("https")
                        .host("docs.api.com")
                        .removePort(),
                prettyPrint()
        );
    }

    static OperationResponsePreprocessor getDocumentResponse() {
        return preprocessResponse(prettyPrint());
    }

    static RequestHeadersSnippet getRequestHeaders() {
        return requestHeaders(
                headerWithName("X-AUTH-TOKEN").description("X-AUTH-TOKEN")
        );
    }

    static ResponseFieldsSnippet setResponseFields() {

        ResponseFieldsSnippet res = responseFields(
                fieldWithPath("success")
                        .type(JsonFieldType.BOOLEAN)
                        .description("응답 성공여부").optional(),
                fieldWithPath("code")
                        .type(JsonFieldType.NUMBER)
                        .description("응답 코드").optional(),
                fieldWithPath("msg")
                        .type(JsonFieldType.STRING)
                        .description("응답 메시지").optional()
        );

        return res;
    }

    /*static User makeUserDetail(List<String> roles) {
        User user = User.builder()
                .id(1)
                .joinType(JoinCode.none)
                .uid("admin@test.com")
                .password("$2a$10$enO3sl1XschxqybWkifOBeo5U5nNapZGsJU.n6xyNXjLClj0LfaoC")
                .name("admin")
                .nickname("admin")
                .email("admin@test.com")
                .emailCert(0)
                .phone("12345678")
                .photo("")
                .birthday("")
                .sex(MFCode.M)
                .address_line1("")
                .address_line2("")
                .zipcode("12345")
                .city("")
                .street("")
                .country("")
                .isTermsserviceAgree(0)
                .isPrivacyAgree(0)
                .isMarketingAgree(0)
                .isBind( YNCode.YES )
                .state( YNCode.N )
                .roles(roles)
                .build();
        return user;
    }*/
}
