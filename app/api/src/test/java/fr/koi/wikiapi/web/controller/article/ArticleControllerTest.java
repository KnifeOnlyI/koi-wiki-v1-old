package fr.koi.wikiapi.web.controller.article;

import fr.koi.wikiapi.constants.Urls;
import fr.koi.wikiapi.dto.RestPageTestImpl;
import fr.koi.wikiapi.web.BaseControllerTest;
import fr.koi.wikiapi.web.model.article.ArticleModel;
import fr.koi.wikiapi.web.model.article.ArticleSearchCriteria;
import fr.koi.wikiapi.web.model.article.CreateOrUpdateArticleModel;
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
class ArticleControllerTest extends BaseControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testSearch() {
        HttpHeadersBuilder httpHeadersBuilder = new HttpHeadersBuilder().authorization(this.getRootToken());

        ArticleSearchCriteria body = new ArticleSearchCriteria();

        ResponseEntity<RestPageTestImpl<ArticleModel>> response = this.restTemplate.exchange(
            Urls.Article.BASE,
            HttpMethod.GET,
            new HttpEntity<>(body, httpHeadersBuilder.build()),
            new ParameterizedTypeReference<>() {
            }
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void testGetById() {
        HttpHeadersBuilder httpHeadersBuilder = new HttpHeadersBuilder().authorization(this.getRootToken());

        ResponseEntity<ArticleModel> response = this.restTemplate.exchange(
            Urls.Article.UNIQUE,
            HttpMethod.GET,
            new HttpEntity<>(httpHeadersBuilder.build()),
            new ParameterizedTypeReference<>() {
            },
            1L
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        ArticleModel responseBody = response.getBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getId()).isNotNull();
        assertThat(responseBody.getTitle()).isEqualTo("Java 17");
        assertThat(responseBody.getDescription()).isEqualTo("All news in Java 17");
        assertThat(responseBody.getContent()).isEqualTo("Java 17 improve performance ?");
        assertThat(responseBody.getAuthorId()).isNotNull();
        assertThat(responseBody.getCreatedAt()).isEqualTo(this.getNowZonedDateTime());
        assertThat(responseBody.getLastUpdateAt()).isNull();
        assertThat(responseBody.getDeletedAt()).isNull();
    }

    @Test
    void testCreate() {
        HttpHeadersBuilder httpHeadersBuilder = new HttpHeadersBuilder().authorization(this.getRootToken());

        CreateOrUpdateArticleModel body = new CreateOrUpdateArticleModel()
            .setTitle("C++")
            .setDescription("C++ news")
            .setContent("C++ improve performance ?")
            .setIsArchived(false);

        ResponseEntity<ArticleModel> response = this.restTemplate.exchange(
            Urls.Article.BASE,
            HttpMethod.POST,
            new HttpEntity<>(body, httpHeadersBuilder.build()),
            new ParameterizedTypeReference<>() {
            }
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        ArticleModel responseBody = response.getBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getId()).isNotNull();
        assertThat(responseBody.getTitle()).isEqualTo(body.getTitle());
        assertThat(responseBody.getDescription()).isEqualTo(body.getDescription());
        assertThat(responseBody.getContent()).isEqualTo(body.getContent());
        assertThat(responseBody.getIsArchived()).isEqualTo(body.getIsArchived());
        assertThat(responseBody.getAuthorId()).isNotNull();
        assertThat(responseBody.getCreatedAt()).isEqualTo(this.getNowZonedDateTime());
        assertThat(responseBody.getLastUpdateAt()).isNull();
        assertThat(responseBody.getDeletedAt()).isNull();
    }

    @Test
    void testUpdate() {
        HttpHeadersBuilder httpHeadersBuilder = new HttpHeadersBuilder().authorization(this.getRootToken());

        CreateOrUpdateArticleModel body = new CreateOrUpdateArticleModel()
            .setTitle("C++")
            .setDescription("C++ news")
            .setContent("C++ improve performance ?")
            .setIsArchived(true);

        ResponseEntity<ArticleModel> response = this.restTemplate.exchange(
            Urls.Article.UNIQUE,
            HttpMethod.PUT,
            new HttpEntity<>(body, httpHeadersBuilder.build()),
            new ParameterizedTypeReference<>() {
            },
            1L
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        ArticleModel responseBody = response.getBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getId()).isNotNull();
        assertThat(responseBody.getTitle()).isEqualTo(body.getTitle());
        assertThat(responseBody.getContent()).isEqualTo(body.getContent());
        assertThat(responseBody.getDescription()).isEqualTo(body.getDescription());
        assertThat(responseBody.getIsArchived()).isEqualTo(body.getIsArchived());
        assertThat(responseBody.getAuthorId()).isNotNull();
        assertThat(responseBody.getCreatedAt()).isEqualTo(this.getNowZonedDateTime());
        assertThat(responseBody.getLastUpdateAt()).isEqualTo(this.getNowZonedDateTime());
        assertThat(responseBody.getDeletedAt()).isNull();
    }

    @Test
    void testDelete() {
        HttpHeadersBuilder httpHeadersBuilder = new HttpHeadersBuilder().authorization(this.getRootToken());

        ResponseEntity<ArticleModel> deleteResponse = this.restTemplate.exchange(
            Urls.Article.UNIQUE,
            HttpMethod.DELETE,
            new HttpEntity<>(httpHeadersBuilder.build()),
            new ParameterizedTypeReference<>() {
            },
            1L
        );

        assertThat(deleteResponse).isNotNull();
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(deleteResponse.getBody()).isNull();
    }
}