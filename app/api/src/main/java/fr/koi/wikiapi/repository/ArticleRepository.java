package fr.koi.wikiapi.repository;

import fr.koi.wikiapi.domain.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The repository to manage article in the database.
 */
@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
    /**
     * Get a not deleted entity with the specified ID.
     *
     * @param id The ID of entity to get
     *
     * @return The entity
     */
    Optional<ArticleEntity> findByIdAndDeletedAtIsNull(Long id);
}
