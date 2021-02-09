package com.ymwoo.project.blog;

import com.ymwoo.project.common.AbstractSearch;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class BlogSearch extends AbstractSearch
{
    private String title;

    private String content;
}
