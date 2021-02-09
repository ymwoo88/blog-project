package com.ymwoo.project.blog;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import com.ymwoo.project.common.AbstractObject;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "blog")
@Getter
@Setter
public class Blog extends AbstractObject<Long>
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long   id;

    @NotBlank
    private String title;
    @NotBlank
    private String content;

    private int    hitCount;
}
