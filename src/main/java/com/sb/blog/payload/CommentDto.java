package com.sb.blog.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CommentDto {

    private Long id;
    @NotEmpty(message = "Name should not be null/empty.")
    private String name;

    @NotEmpty(message = "Email should not be empty/null")
    @Email
    private String email;

    @NotEmpty
    @Size(min = 10, message = "Body should greater than 10 characters.")
    private String body;

}
