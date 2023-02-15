package fr.koi.wikiapi.repository;

import fr.koi.wikiapi.domain.ArticleCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The repository to manage article categories in the database.
 */
@Repository
public interface ArticleCategoryRepository extends JpaRepository<ArticleCategoryEntity, Long> {
    /**
     * Get a not deleted entity with the specified ID.
     *
     * @param id The ID of entity to get
     *
     * @return The entity
     */
    Optional<ArticleCategoryEntity> findByIdAndDeletedAtIsNull(Long id);
}
