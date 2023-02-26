package fr.koi.wikiapi.web.controller.user;

import fr.koi.wikiapi.constants.Urls;
import fr.koi.wikiapi.web.BaseControllerTest;
import fr.koi.wikiapi.web.model.user.UsernameModel;
import fr.koi.wikiapi.web.utils.HttpHeadersBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserControllerTest extends BaseControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testGetUsername() {
        HttpHeadersBuilder httpHeadersBuilder = new HttpHeadersBuilder().authorization(this.getRootToken());

        ResponseEntity<UsernameModel> response = this.restTemplate.exchange(
            Urls.User.UNIQUE,
            HttpMethod.GET,
            new HttpEntity<>(httpHeadersBuilder.build()),
            new ParameterizedTypeReference<>() {
            },
            "4a04cb37-cbe4-4556-9288-7c35215b07ec"
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNull();
    }
}