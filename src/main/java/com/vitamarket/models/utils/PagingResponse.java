package com.vitamarket.models.utils;

import java.util.List;

import com.vitamarket.models.Producto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class PagingResponse {
	
	/**
     * entity count
     */
    private Long count;
    
    /**
     * page number, 0 indicate the first page.
     */
    private Long pageNumber;
    
    /**
     * size of page, 0 indicate infinite-sized.
     */
    private Long pageSize;
    
    /**
     * Offset from the of pagination.
     */
    private Long pageOffset;
    
    /**
     * the number total of pages.
     */
    private Long pageTotal;
    
    /**
     * elements of page.
     */
    private List<Producto> elements;
    
}
