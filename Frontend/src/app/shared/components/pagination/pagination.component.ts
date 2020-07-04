import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { PagingInfo } from '../../models/pagingInfo';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.scss']
})
export class PaginationComponent implements OnInit {

  private pageSizeOptions = [5, 10, 20];

  @Input()
  pagingInfo: PagingInfo;

  @Output()
  paginationRequestEvent = new EventEmitter<PagingInfo>();

  constructor() { }

  ngOnInit() {
  }

  private onPageSelect(pageIndex: number) {
    this.pagingInfo.selectedPageIndex = pageIndex;
    this.paginationRequestEvent.emit(this.pagingInfo);
  }

  private onSizeSelect(pageSize: number) {
    this.pagingInfo.selectedPageSize = pageSize;
    this.pagingInfo.selectedPageIndex = 0;
    this.paginationRequestEvent.emit(this.pagingInfo);
  }

  private onPagingChanged(pagination) {
    this.pagingInfo.selectedPageSize = pagination.pageSize;
    this.pagingInfo.selectedPageIndex = pagination.pageIndex;
    this.paginationRequestEvent.emit(this.pagingInfo);
  }

}
