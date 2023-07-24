package fr.koi.wikiapi.service.article;

import fr.koi.wikiapi.domain.ArticleEntity;
import fr.koi.wikiapi.mapper.ArticleMapper;
import fr.koi.wikiapi.repository.dao.ArticleDao;
import fr.koi.wikiapi.service.user.UserService;
import fr.koi.wikiapi.web.model.graphql.article.CreateArticleModel;
import fr.koi.wikiapi.web.model.graphql.article.UpdateArticleModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class ArticleServiceTest {
    @Mock
    private ArticleDao articleDao;

    @Mock
    private ArticleMapper articleMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private ArticleService articleService;

    @Test
    void testGetByIdCallDaoAndMap() {
        var entity = new ArticleEntity().setId(1L);

        when(this.articleDao.getById(entity.getId())).thenReturn(entity);

        this.articleService.getById(entity.getId());

        verify(this.articleMapper).toModel(entity);
    }

    @Test
    void testCreateCallDaoAndMap() {
        var authorId = "1";
        var model = new CreateArticleModel();
        var entity = new ArticleEntity();

        when(this.userService.getUserId()).thenReturn(authorId);
        when(this.articleMapper.toEntity(model)).thenReturn(entity);

        this.articleService.create(model);

        verify(this.articleDao).save(entity);
        verify(this.articleMapper).toModel(entity);

        assertThat(entity.getAuthorId()).isEqualTo(authorId);
    }

    @Test
    void testUpdateCallDaoAndMap() {
        var entity = new ArticleEntity().setId(1L).setTitle("title");
        var updateModel = new UpdateArticleModel().setId(1L).setTitle("newTitle");

        when(this.articleDao.getById(entity.getId())).thenReturn(entity);

        this.articleService.update(updateModel);

        verify(this.articleMapper).updateEntity(entity, updateModel);
        verify(this.articleMapper).toModel(entity);
    }

    @Test
    void testDeleteCallDao() {
        var id = 1L;

        this.articleService.delete(id);

        verify(this.articleDao).delete(id);
    }
}