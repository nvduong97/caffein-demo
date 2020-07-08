package com.example.demo.exception;

import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
//@XmlRootElement(name = "error")
public class ErrorResponse {

    private int code;
    //General message about error
    private String message;
    //errors in API request processing
    private List<String> details;

}
