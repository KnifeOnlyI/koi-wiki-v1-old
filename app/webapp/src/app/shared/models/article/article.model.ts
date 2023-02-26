/**
 * Represent an article.
 */
export interface ArticleModel {
  id?: number;
  title?: string;
  description?: string;
  content?: string;
  isArchived?: boolean | null;
  authorId?: string | null;
  createdAt?: Date | null;
  lastUpdateAt?: Date | null;
  deletedAt?: Date | null;
}
