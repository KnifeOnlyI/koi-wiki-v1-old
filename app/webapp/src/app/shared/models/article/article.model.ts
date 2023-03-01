import {AuthorModel} from './author.model';

/**
 * Represent an article.
 */
export interface ArticleModel {
  id?: number;
  title?: string;
  description?: string;
  content?: string;
  isArchived?: boolean;
  author?: AuthorModel;
  authorId?: string;
  createdAt?: Date;
  lastUpdateAt?: Date;
  deletedAt?: Date;
  categories?: Array<number>;
}
