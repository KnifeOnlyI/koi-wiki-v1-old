package fr.koi.wikiapi.service.article_category;

import fr.koi.wikiapi.domain.ArticleCategoryEntity;
import fr.koi.wikiapi.mapper.ArticleCategoryMapper;
import fr.koi.wikiapi.repository.dao.ArticleCategoryDao;
import fr.koi.wikiapi.web.model.graphql.article_category.CreateArticleCategoryModel;
import fr.koi.wikiapi.web.model.graphql.article_category.UpdateArticleCategoryModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleCategoryServiceTest {
    @Mock
    private ArticleCategoryDao articleCategoryDao;

    @Mock
    private ArticleCategoryMapper articleCategoryMapper;

    @InjectMocks
    private ArticleCategoryService articleCategoryService;

    @Test
    void testGetByIdCallDaoAndMap() {
        var entity = new ArticleCategoryEntity().setId(1L);

        when(this.articleCategoryDao.getById(entity.getId())).thenReturn(entity);

        this.articleCategoryService.getById(entity.getId());

        verify(this.articleCategoryMapper).toModel(entity);
    }

    @Test
    void testCreateCallDaoAndMap() {
        var model = new CreateArticleCategoryModel();
        var entity = new ArticleCategoryEntity();

        when(this.articleCategoryMapper.toEntity(model)).thenReturn(entity);

        this.articleCategoryService.create(model);

        verify(this.articleCategoryDao).save(entity);
        verify(this.articleCategoryMapper).toModel(entity);
    }

    @Test
    void testUpdateCallDaoAndMap() {
        var entity = new ArticleCategoryEntity().setId(1L).setName("name");
        var updateModel = new UpdateArticleCategoryModel().setId(1L).setName("newName");

        when(this.articleCategoryDao.getById(entity.getId())).thenReturn(entity);

        this.articleCategoryService.update(updateModel);

        verify(this.articleCategoryMapper).updateEntity(entity, updateModel);
        verify(this.articleCategoryMapper).toModel(entity);
    }

    @Test
    void testDeleteCallDao() {
        var id = 1L;

        this.articleCategoryService.delete(id);

        verify(this.articleCategoryDao).delete(id);
    }
}