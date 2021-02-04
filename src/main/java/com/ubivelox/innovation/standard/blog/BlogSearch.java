package com.ubivelox.innovation.standard.blog;

import com.ubivelox.innovation.standard.common.AbstractSearch;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@ToString(callSuper = true)
@Data
@SuperBuilder
public class BlogSearch extends AbstractSearch
{
    private String title;

    private String content;
}
