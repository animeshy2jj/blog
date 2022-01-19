package com.sb.blog.payload;

import lombok.Data;

import java.util.List;

@Data
public class PostResponse {

    private List<PostDto> content;
    private int page;
    private int size;
    private Long totalElements;
    private int totalPage;
    private Boolean last;
}
