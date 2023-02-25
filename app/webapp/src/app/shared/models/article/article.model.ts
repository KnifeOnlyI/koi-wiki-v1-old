/**
 * Represent an article.
 */
export interface ArticleModel {
  id?: number;
  title?: string;
  content?: string;
  description?: string;
  createdAt?: Date | null;
  lastUpdateAt?: Date | null;
  deletedAt?: Date | null;
}
