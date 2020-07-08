package com.example.demo.model;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class APIResponse {
    private Status status;
    private Object data;
    private PagingMetadata pagingMetadata;
}
