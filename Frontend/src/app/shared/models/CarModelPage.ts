import { CarModelDTO } from './CarModelDTO';

export interface CarModelPage {
    content: Array<CarModelDTO>;        // content of current page
    numberOfElements: number;           // content length of current page
    totalElements: number;              // total number of matched elements (for all pages)
    size: number;                       // page size (as maximum content length)
    totalPages: number;                 // total number of pages
    number: number;                     // current page index
    first: boolean;                     // current page is first flag
    last: boolean;                      // current page is last flag
    empty: boolean;                     // current page is empty flag
}
