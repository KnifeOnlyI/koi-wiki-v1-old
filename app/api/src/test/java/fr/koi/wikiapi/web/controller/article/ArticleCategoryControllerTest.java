package fr.koi.wikiapi.web.controller.article;

import fr.koi.wikiapi.graphql.constants.GQLConstants;
import fr.koi.wikiapi.graphql.response.GQLResponse;
import fr.koi.wikiapi.graphql.response.article_category.GQLArticleCategory;
import fr.koi.wikiapi.graphql.response.article_category.GQLPageArticleCategory;
import fr.koi.wikiapi.web.BaseControllerTest;
import fr.koi.wikiapi.web.model.graphql.article_category.ArticleCategoryModel;
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
class ArticleCategoryControllerTest extends BaseControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testSearch() {
        HttpHeadersBuilder httpHeadersBuilder = new HttpHeadersBuilder().authorization(this.getRootToken());

        Map<String, String> body = new HashMap<>();
        body.put("query", """
            query {
                searchArticleCategories(criteria: {}) {%s}
            }""".formatted(GQLConstants.ArticleCategory.ALL_PAGE_FIELDS)
        );

        ResponseEntity<GQLResponse<GQLArticleCategory>> response = this.restTemplate.exchange(
            "/graphql",
            HttpMethod.POST,
            new HttpEntity<>(body, httpHeadersBuilder.build()),
            new ParameterizedTypeReference<>() {
            }
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        GQLResponse<GQLArticleCategory> graphQlResponseBody = response.getBody();
        assertThat(graphQlResponseBody).isNotNull();
        GQLPageArticleCategory responseBody = graphQlResponseBody.getData().getSearchArticleCategories();
        assertThat(responseBody).isNotNull();

        assertThat(responseBody.getContent()).isNotEmpty();
    }

    @Test
    void testGetArticleCategoryById() {
        HttpHeadersBuilder httpHeadersBuilder = new HttpHeadersBuilder().authorization(this.getRootToken());

        Map<String, String> body = new HashMap<>();
        body.put("query", """
            query {
                getArticleCategoryById(id: 1) {%s}
            }""".formatted(GQLConstants.ArticleCategory.ALL_FIELDS)
        );

        ResponseEntity<GQLResponse<GQLArticleCategory>> response = this.restTemplate.exchange(
            "/graphql",
            HttpMethod.POST,
            new HttpEntity<>(body, httpHeadersBuilder.build()),
            new ParameterizedTypeReference<>() {
            }
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        GQLResponse<GQLArticleCategory> graphQlResponseBody = response.getBody();
        assertThat(graphQlResponseBody).isNotNull();
        ArticleCategoryModel responseBody = graphQlResponseBody.getData().getGetArticleCategoryById();
        assertThat(responseBody).isNotNull();

        assertThat(responseBody.getId()).isNotNull();
        assertThat(responseBody.getName()).isEqualTo("Java");
        assertThat(responseBody.getDescription()).isEqualTo("All java articles");
        assertThat(responseBody.getCreatedAt()).isEqualTo(this.getNowZonedDateTime());
        assertThat(responseBody.getLastUpdateAt()).isNull();
        assertThat(responseBody.getDeletedAt()).isNull();
    }

    @Test
    void testCreateArticleCategory() {
        HttpHeadersBuilder httpHeadersBuilder = new HttpHeadersBuilder().authorization(this.getRootToken());

        Map<String, String> body = new HashMap<>();
        body.put("query", """
            mutation {
                createArticleCategory(data: {
                    name: "C++"
                    description: "All C++ articles"
                }) {%s}
            }""".formatted(GQLConstants.ArticleCategory.ALL_FIELDS)
        );

        ResponseEntity<GQLResponse<GQLArticleCategory>> response = this.restTemplate.exchange(
            "/graphql",
            HttpMethod.POST,
            new HttpEntity<>(body, httpHeadersBuilder.build()),
            new ParameterizedTypeReference<>() {
            }
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        GQLResponse<GQLArticleCategory> graphQlResponseBody = response.getBody();
        assertThat(graphQlResponseBody).isNotNull();
        ArticleCategoryModel responseBody = graphQlResponseBody.getData().getCreateArticleCategory();
        assertThat(responseBody).isNotNull();

        assertThat(responseBody.getId()).isNotNull();
        assertThat(responseBody.getName()).isEqualTo("C++");
        assertThat(responseBody.getDescription()).isEqualTo("All C++ articles");
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
                updateArticleCategory(data: {
                    id: 1
                    name: "C++"
                    description: "All C++ articles"
                }) {%s}
            }""".formatted(GQLConstants.ArticleCategory.ALL_FIELDS)
        );

        ResponseEntity<GQLResponse<GQLArticleCategory>> response = this.restTemplate.exchange(
            "/graphql",
            HttpMethod.POST,
            new HttpEntity<>(body, httpHeadersBuilder.build()),
            new ParameterizedTypeReference<>() {
            }
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        GQLResponse<GQLArticleCategory> graphQlResponseBody = response.getBody();
        assertThat(graphQlResponseBody).isNotNull();
        ArticleCategoryModel responseBody = graphQlResponseBody.getData().getUpdateArticleCategory();
        assertThat(responseBody).isNotNull();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getId()).isNotNull();
        assertThat(responseBody.getName()).isEqualTo("C++");
        assertThat(responseBody.getDescription()).isEqualTo("All C++ articles");
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
                deleteArticleCategory(id: 1)
            }"""
        );

        ResponseEntity<GQLResponse<GQLArticleCategory>> response = this.restTemplate.exchange(
            "/graphql",
            HttpMethod.POST,
            new HttpEntity<>(body, httpHeadersBuilder.build()),
            new ParameterizedTypeReference<>() {
            }
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        GQLResponse<GQLArticleCategory> graphQlResponseBody = response.getBody();
        assertThat(graphQlResponseBody).isNotNull();
        Long responseBody = graphQlResponseBody.getData().getDeleteArticleCategory();

        assertThat(responseBody).isEqualTo(1L);
    }
}