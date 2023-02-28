package fr.koi.wikiapi.service.article;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.impl.JPAQuery;
import fr.koi.wikiapi.constants.Roles;
import fr.koi.wikiapi.domain.ArticleCategoryEntity;
import fr.koi.wikiapi.domain.QArticleCategoryEntity;
import fr.koi.wikiapi.dto.exception.ForbiddenException;
import fr.koi.wikiapi.dto.exception.NotFoundResourceException;
import fr.koi.wikiapi.mapper.ArticleCategoryMapper;
import fr.koi.wikiapi.service.user.UserService;
import fr.koi.wikiapi.web.model.article.ArticleCategoryModel;
import fr.koi.wikiapi.web.model.article.ArticleCategorySearchCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
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
 * The service to manage article category search.
 */
@Service
@RequiredArgsConstructor
public class ArticleCategorySearchService {
    /**
     * The mapper for article categories.
     */
    private final ArticleCategoryMapper articleCategoryMapper;

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
    public Page<ArticleCategoryModel> search(final ArticleCategorySearchCriteria criteria) {
        if (!this.userService.hasRole(Roles.ArticleCategory.SEARCH)) {
            throw new NotFoundResourceException();
        }

        if (((criteria.getDeleted() == null || criteria.getDeleted())
            && !this.userService.hasRole(Roles.ArticleCategory.SEARCH_DELETED))
        ) {
            throw new ForbiddenException();
        }

        JPAQuery<ArticleCategoryEntity> query = this.buildQuery(criteria);
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getPageSize());

        Querydsl queryDSL = new Querydsl(
            this.entityManager,
            (new PathBuilderFactory()).create(ArticleCategoryEntity.class)
        );

        QueryResults<ArticleCategoryEntity> results = queryDSL.applyPagination(pageable, query).fetchResults();

        return new PageImpl<>(
            this.articleCategoryMapper.toModels(results.getResults()),
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
    private JPAQuery<ArticleCategoryEntity> buildQuery(ArticleCategorySearchCriteria criteria) {
        JPAQuery<ArticleCategoryEntity> query = new JPAQuery<>(this.entityManager);
        QArticleCategoryEntity qArticleCategory = QArticleCategoryEntity.articleCategoryEntity;

        List<Predicate> whereFilters = new ArrayList<>();

        Optional.ofNullable(criteria.getDeleted())
            .ifPresent(value -> whereFilters.add(criteria.getDeleted()
                ? qArticleCategory.deletedAt.isNotNull()
                : qArticleCategory.deletedAt.isNull()
            ));

        Optional.ofNullable(criteria.getName())
            .ifPresent(value -> whereFilters.add(qArticleCategory.name.containsIgnoreCase(value)));

        Optional.ofNullable(criteria.getDescription())
            .ifPresent(value -> whereFilters.add(qArticleCategory.description.containsIgnoreCase(value)));

        if (!CollectionUtils.isEmpty(criteria.getExcludedIds())) {
            whereFilters.add(qArticleCategory.id.notIn(criteria.getExcludedIds()));
        }

        return query
            .distinct()
            .from(qArticleCategory)
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
        if (StringUtils.equalsIgnoreCase(sort, "name")) {
            return QArticleCategoryEntity.articleCategoryEntity.name;
        } else if (StringUtils.equalsIgnoreCase(sort, "description")) {
            return QArticleCategoryEntity.articleCategoryEntity.description;
        } else if (StringUtils.equalsIgnoreCase(sort, "createdAt")) {
            return QArticleCategoryEntity.articleCategoryEntity.createdAt;
        } else if (StringUtils.equalsIgnoreCase(sort, "lastUpdateAt")) {
            return QArticleCategoryEntity.articleCategoryEntity.lastUpdateAt;
        } else if (StringUtils.equalsIgnoreCase(sort, "deletedAt")) {
            return QArticleCategoryEntity.articleCategoryEntity.deletedAt;
        } else {
            return QArticleCategoryEntity.articleCategoryEntity.id;
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
