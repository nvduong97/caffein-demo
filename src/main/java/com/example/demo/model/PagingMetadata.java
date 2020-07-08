package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PagingMetadata {
    private int size;
    private long totalElement;
    private int totalPage;
    private int currentPage;
    private String nextPageURL;
    private String previousPageURL;
}
