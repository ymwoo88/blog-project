package com.ymwoo.project.blog;

import com.ymwoo.project.common.AbstractObject;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity(name = "blog")
@Getter
@Setter
public class Blog extends AbstractObject <String>
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @NotBlank
    private String title;
    @NotBlank
    private String content;

    private int    hitCount;
}
