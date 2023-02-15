import {Injectable} from "@angular/core";
import {MatPaginatorIntl} from "@angular/material/paginator";
import {Subject} from "rxjs";
import {TranslateService} from "@ngx-translate/core";

/**
 * The service to manage material paginators.
 */
@Injectable()
export class PaginatorService implements MatPaginatorIntl {
  firstPageLabel!: string;
  itemsPerPageLabel!: string;
  lastPageLabel!: string;
  nextPageLabel!: string;
  previousPageLabel!: string;
  changes = new Subject<void>();
  labelsInitialized = false;

  /**
   * Create a new instance.
   *
   * @param translateService The service to manage translations
   */
  constructor(private readonly translateService: TranslateService) {
  }

  getRangeLabel(page: number, pageSize: number, length: number): string {
    this.firstPageLabel = this.translateService.instant('pagination.firstPage');
    this.lastPageLabel = this.translateService.instant('pagination.lastPage');
    this.itemsPerPageLabel = this.translateService.instant('pagination.itemsPerPage');
    this.nextPageLabel = this.translateService.instant('pagination.nextPage');
    this.previousPageLabel = this.translateService.instant('pagination.previousPage');

    let currentPage = 1;
    let nbPages = 1;

    if (length !== 0) {
      currentPage = page + 1;
      nbPages = Math.ceil(length / pageSize);
    }

    const pageRangeLabel = this.translateService.instant('pagination.pageRange', {
      currentPage,
      nbPages,
      totalNbResults: length
    });

    if (!this.labelsInitialized) {
      this.changes.next();
    }

    return pageRangeLabel;
  }
}
