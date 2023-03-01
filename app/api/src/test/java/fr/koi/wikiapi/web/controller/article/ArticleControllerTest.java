package fr.koi.wikiapi.web.controller.article;

import fr.koi.wikiapi.graphql.constants.GQLConstants;
import fr.koi.wikiapi.graphql.response.GQLResponse;
import fr.koi.wikiapi.graphql.response.article.GQLArticleModel;
import fr.koi.wikiapi.graphql.response.article.GQLPageArticle;
import fr.koi.wikiapi.web.BaseControllerTest;
import fr.koi.wikiapi.web.model.graphql.article.ArticleModel;
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

import java.util.HashMap;
import java.util.Map;

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

        Map<String, String> body = new HashMap<>();
        body.put("query", """
            query {
                searchArticles(criteria: {}) {%s}
            }""".formatted(GQLConstants.Article.ALL_PAGE_FIELDS)
        );

        ResponseEntity<GQLResponse<GQLArticleModel>> response = this.restTemplate.exchange(
            "/graphql",
            HttpMethod.POST,
            new HttpEntity<>(body, httpHeadersBuilder.build()),
            new ParameterizedTypeReference<>() {
            }
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        GQLResponse<GQLArticleModel> graphQlResponseBody = response.getBody();
        assertThat(graphQlResponseBody).isNotNull();
        GQLPageArticle responseBody = graphQlResponseBody.getData().getSearchArticles();
        assertThat(responseBody).isNotNull();

        assertThat(responseBody.getContent()).isNotEmpty();
    }

    @Test
    void testGetById() {
        HttpHeadersBuilder httpHeadersBuilder = new HttpHeadersBuilder().authorization(this.getRootToken());

        Map<String, String> body = new HashMap<>();
        body.put("query", """
            query {
                getArticleById(id: 1) {%s}
            }""".formatted(GQLConstants.Article.ALL_FIELDS)
        );

        ResponseEntity<GQLResponse<GQLArticleModel>> response = this.restTemplate.exchange(
            "/graphql",
            HttpMethod.POST,
            new HttpEntity<>(body, httpHeadersBuilder.build()),
            new ParameterizedTypeReference<>() {
            }
        );

        GQLResponse<GQLArticleModel> graphQlResponseBody = response.getBody();
        assertThat(graphQlResponseBody).isNotNull();
        ArticleModel responseBody = graphQlResponseBody.getData().getGetArticleById();
        assertThat(responseBody).isNotNull();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getId()).isNotNull();
        assertThat(responseBody.getTitle()).isEqualTo("Java 17");
        assertThat(responseBody.getDescription()).isEqualTo("All news in Java 17");
        assertThat(responseBody.getContent()).isEqualTo("Java 17 improve performance ?");
        assertThat(responseBody.getAuthor()).isNotNull();
        assertThat(responseBody.getAuthor().getId()).isEqualTo("author_keycloak_id");
        assertThat(responseBody.getAuthor().getUsername()).isNull();
        assertThat(responseBody.getCreatedAt()).isEqualTo(this.getNowZonedDateTime());
        assertThat(responseBody.getLastUpdateAt()).isNull();
        assertThat(responseBody.getDeletedAt()).isNull();
    }

    @Test
    void testCreate() {
        HttpHeadersBuilder httpHeadersBuilder = new HttpHeadersBuilder().authorization(this.getRootToken());

        Map<String, String> body = new HashMap<>();
        body.put("query", """
            mutation {
                createArticle(data: {
                    title: "C++"
                    description: "C++ news"
                    content: "C++ improve performance ?"
                    isArchived: true
                }) {%s}
            }""".formatted(GQLConstants.Article.ALL_FIELDS)
        );

        ResponseEntity<GQLResponse<GQLArticleModel>> response = this.restTemplate.exchange(
            "/graphql",
            HttpMethod.POST,
            new HttpEntity<>(body, httpHeadersBuilder.build()),
            new ParameterizedTypeReference<>() {
            }
        );

        GQLResponse<GQLArticleModel> graphQlResponseBody = response.getBody();
        assertThat(graphQlResponseBody).isNotNull();
        ArticleModel responseBody = graphQlResponseBody.getData().getCreateArticle();
        assertThat(responseBody).isNotNull();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getId()).isNotNull();
        assertThat(responseBody.getTitle()).isEqualTo("C++");
        assertThat(responseBody.getDescription()).isEqualTo("C++ news");
        assertThat(responseBody.getContent()).isEqualTo("C++ improve performance ?");
        assertThat(responseBody.getIsArchived()).isTrue();
        assertThat(responseBody.getAuthor()).isNotNull();
        assertThat(responseBody.getAuthor().getId()).isNotNull();
        assertThat(responseBody.getAuthor().getUsername()).isNotNull();
        assertThat(responseBody.getCreatedAt()).isEqualTo(this.getNowZonedDateTime());
        assertThat(responseBody.getLastUpdateAt()).isNull();
        assertThat(responseBody.getDeletedAt()).isNull();
    }

    @Test
    void testUpdate() {
        HttpHeadersBuilder httpHeadersBuilder = new HttpHeadersBuilder().authorization(this.getRootToken());

        Map<String, String> body = new HashMap<>();
        body.put("query", """
            mutation {
                updateArticle(data: {
                    id: 1
                    title: "C++"
                    description: "C++ news"
                    content: "C++ improve performance ?"
                    isArchived: true
                }) {%s}
            }""".formatted(GQLConstants.Article.ALL_FIELDS)
        );

        ResponseEntity<GQLResponse<GQLArticleModel>> response = this.restTemplate.exchange(
            "/graphql",
            HttpMethod.POST,
            new HttpEntity<>(body, httpHeadersBuilder.build()),
            new ParameterizedTypeReference<>() {
            }
        );

        GQLResponse<GQLArticleModel> graphQlResponseBody = response.getBody();
        assertThat(graphQlResponseBody).isNotNull();
        ArticleModel responseBody = graphQlResponseBody.getData().getUpdateArticle();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getId()).isNotNull();
        assertThat(responseBody.getTitle()).isEqualTo("C++");
        assertThat(responseBody.getDescription()).isEqualTo("C++ news");
        assertThat(responseBody.getContent()).isEqualTo("C++ improve performance ?");
        assertThat(responseBody.getIsArchived()).isTrue();
        assertThat(responseBody.getAuthor()).isNotNull();
        assertThat(responseBody.getAuthor().getId()).isNotNull();
        assertThat(responseBody.getAuthor().getUsername()).isNull();
        assertThat(responseBody.getCreatedAt()).isEqualTo(this.getNowZonedDateTime());
        assertThat(responseBody.getLastUpdateAt()).isEqualTo(this.getNowZonedDateTime());
        assertThat(responseBody.getDeletedAt()).isNull();
    }

    @Test
    void testDelete() {
        HttpHeadersBuilder httpHeadersBuilder = new HttpHeadersBuilder().authorization(this.getRootToken());

        Map<String, String> body = new HashMap<>();
        body.put("query", """
            mutation {
                deleteArticle(id: 1)
            }"""
        );

        ResponseEntity<GQLResponse<GQLArticleModel>> response = this.restTemplate.exchange(
            "/graphql",
            HttpMethod.POST,
            new HttpEntity<>(body, httpHeadersBuilder.build()),
            new ParameterizedTypeReference<>() {
            }
        );

        GQLResponse<GQLArticleModel> graphQlResponseBody = response.getBody();
        assertThat(graphQlResponseBody).isNotNull();
        Long responseBody = graphQlResponseBody.getData().getDeleteArticle();

        assertThat(responseBody).isEqualTo(1L);
    }
}