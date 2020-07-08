package com.example.demo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Status {
    private int code;
    private String message;
    private String description;
}
