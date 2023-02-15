/**
 * Represent an article category.
 */
export interface ArticleCategoryModel {
  id?: number;
  name?: string;
  description?: string;
  createdAt?: Date | null;
  lastUpdateAt?: Date | null;
  deletedAt?: Date | null;
}
