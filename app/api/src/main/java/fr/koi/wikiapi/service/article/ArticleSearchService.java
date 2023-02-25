package fr.koi.wikiapi.service.article;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.impl.JPAQuery;
import fr.koi.wikiapi.constants.Roles;
import fr.koi.wikiapi.domain.ArticleEntity;
import fr.koi.wikiapi.domain.QArticleEntity;
import fr.koi.wikiapi.dto.exception.ForbiddenException;
import fr.koi.wikiapi.dto.exception.NotFoundResourceException;
import fr.koi.wikiapi.mapper.ArticleMapper;
import fr.koi.wikiapi.service.user.UserService;
import fr.koi.wikiapi.web.model.article.ArticleModel;
import fr.koi.wikiapi.web.model.article.ArticleSearchCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The service to manage article search.
 */
@Service
@RequiredArgsConstructor
public class ArticleSearchService {
    /**
     * The mapper for article.
     */
    private final ArticleMapper articleMapper;

    /**
     * The service to manage users.
     */
    private final UserService userService;

    /**
     * The entity manager.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Search entities.
     *
     * @param criteria The criteria
     *
     * @return The results
     */
    public Page<ArticleModel> search(final ArticleSearchCriteria criteria) {
        if (!this.userService.hasRole(Roles.Article.SEARCH)) {
            throw new NotFoundResourceException();
        }

        if (((criteria.getDeleted() == null || criteria.getDeleted())
            && !this.userService.hasRole(Roles.Article.SEARCH_DELETED))
        ) {
            throw new ForbiddenException();
        }

        JPAQuery<ArticleEntity> query = this.buildQuery(criteria);
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getPageSize());

        Querydsl queryDSL = new Querydsl(
            this.entityManager,
            (new PathBuilderFactory()).create(ArticleEntity.class)
        );

        QueryResults<ArticleEntity> results = queryDSL.applyPagination(pageable, query).fetchResults();

        return new PageImpl<>(
            this.articleMapper.toModels(results.getResults()),
            pageable,
            results.getTotal()
        );
    }

    /**
     * Build the query.
     *
     * @param criteria The criteria
     *
     * @return The query
     */
    private JPAQuery<ArticleEntity> buildQuery(ArticleSearchCriteria criteria) {
        JPAQuery<ArticleEntity> query = new JPAQuery<>(this.entityManager);
        QArticleEntity qArticle = QArticleEntity.articleEntity;

        List<Predicate> whereFilters = new ArrayList<>();

        Optional.ofNullable(criteria.getDeleted())
            .ifPresent(value -> whereFilters.add(criteria.getDeleted()
                ? qArticle.deletedAt.isNotNull()
                : qArticle.deletedAt.isNull()
            ));

        Optional.ofNullable(criteria.getQ())
            .ifPresent(value -> whereFilters.add(qArticle.title.containsIgnoreCase(value)
                .or(qArticle.description.containsIgnoreCase(value)
                    .or(qArticle.content.containsIgnoreCase(value)))
            ));

        return query
            .distinct()
            .from(qArticle)
            .where(whereFilters.toArray(new Predicate[0]))
            .orderBy(this.getOrderBy(criteria.getSort()));
    }

    /**
     * Get the order specifier based on the complete sort string (column_name:sorting_direction).
     *
     * @param completeSort The complete sorting string
     *
     * @return The created order specifier
     */
    private OrderSpecifier<?> getOrderBy(final String completeSort) {
        String[] sortSplit = completeSort.split(":");

        return this.getSortDirection(this.getOrderColumn(sortSplit[0]), sortSplit[1]);
    }

    /**
     * Get a comparable based on the specified sorting column name.
     *
     * @param sort The sorting column name
     *
     * @return The created comparable
     */
    private ComparableExpressionBase<?> getOrderColumn(final String sort) {
        if (StringUtils.equalsIgnoreCase(sort, "title")) {
            return QArticleEntity.articleEntity.title;
        } else if (StringUtils.equalsIgnoreCase(sort, "description")) {
            return QArticleEntity.articleEntity.description;
        } else if (StringUtils.equalsIgnoreCase(sort, "createdAt")) {
            return QArticleEntity.articleEntity.createdAt;
        } else if (StringUtils.equalsIgnoreCase(sort, "lastUpdateAt")) {
            return QArticleEntity.articleEntity.lastUpdateAt;
        } else if (StringUtils.equalsIgnoreCase(sort, "deletedAt")) {
            return QArticleEntity.articleEntity.deletedAt;
        } else {
            return QArticleEntity.articleEntity.id;
        }
    }

    /**
     * Build an order specifier based on the specified order column and sorting direction.
     *
     * @param orderColumn The order column
     * @param direction   The sorting direction
     *
     * @return The created order specifier
     */
    private OrderSpecifier<?> getSortDirection(final ComparableExpressionBase<?> orderColumn, final String direction) {
        if (StringUtils.equalsIgnoreCase(direction, "desc")) {
            return orderColumn.desc();
        }

        return orderColumn.asc();
    }
}
