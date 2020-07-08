package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

import static com.example.demo.locale.Translator.toLocale;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthorDTO {

    @NotBlank(message = "{error.msg.author.name.invalid}")
    private String name;
    @NotBlank(message = "{error.msg.author.address.invalid}")
    private String address;
    @NotBlank(message = "{error.msg.author.email.invalid}")
    private String email;
}
