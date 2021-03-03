package com.ymwoo.project.blog;

import com.ymwoo.project.common.AbstractObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.Getter;
import lombok.Setter;

@Document
@Getter
@Setter
public class Blog extends AbstractObject <String>
{
    @Id
    private String id;

    private String title;

    private String content;

    private int    hitCount;
}
