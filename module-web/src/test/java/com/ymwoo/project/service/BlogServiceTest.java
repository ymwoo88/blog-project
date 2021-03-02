package com.ymwoo.project.service;

import com.ymwoo.project.blog.Blog;
import com.ymwoo.project.blog.BlogSearch;
import com.ymwoo.project.blog.BlogService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
public class BlogServiceTest
{
    @Autowired
    BlogService blogService;

    Blog blog1;
    Blog        blog2;

    @BeforeEach
    void init()
    {
        Blog blog1 = new Blog();
        blog1.setTitle("title1");
        blog1.setContent("content1");
        blog1.setCreatedDateTime(LocalDateTime.now());
        blog1.setOwner("admin");
        this.blog1 = blog1;

        Blog blog2 = new Blog();
        blog2.setTitle("title2");
        blog2.setContent("content2");
        blog2.setCreatedDateTime(LocalDateTime.now());
        this.blog2 = blog2;
    }





    @Test
    @DisplayName("id값으로 단건 조회")
    void getByIdTest()
    {
        Blog resource = this.blogService.post(this.blog1);

        // id로 조회
        Blog blog = this.blogService.getById(resource);

        Assertions.assertEquals("title1", blog.getTitle());
        Assertions.assertEquals("content1", blog.getContent());
    }





    @Test
    @DisplayName("전체 리스트 조회 (검색조선 없음)")
    void getListTest()
    {
        Blog resource1 = this.blogService.post(this.blog1);
        Blog resource2 = this.blogService.post(this.blog2);

        // 검색 데이터 생성
        BlogSearch blogSearch = new BlogSearch();
        blogSearch.setPage(0);
        blogSearch.setSize(10);

        // 다건 검색
        Page<Blog> response = this.blogService.getList(blogSearch);

        Assertions.assertEquals(2, response.getTotalElements());
    }





    @Test
    @DisplayName("저장한 정보 단건 업데이트")
    void putByIdTest()
    {
        Blog blog = this.blogService.post(this.blog1);
        System.out.println("saved blog title : " + blog.getTitle());
        System.out.println("saved blog content : " + blog.getContent());

        // 내용 변경 및 업데이트 진행
        Blog updateBlog = new Blog();
        updateBlog.setId(blog.getId());
        updateBlog.setTitle("update title");
        updateBlog.setContent("update content");
        Blog result = this.blogService.putById(updateBlog);

        // 업데이트 된 결과 로그
        System.out.println("updated blog title : " + blog.getTitle());
        System.out.println("updated blog content : " + blog.getContent());

        Assertions.assertEquals("update title", result.getTitle());
        Assertions.assertEquals("update content", result.getContent());
    }





    @Test
    @DisplayName("저장한 정보 단건 삭제")
    void deleteByIdTest()
    {
        Blog blog = this.blogService.post(this.blog1);
        System.out.println("saved blog title : " + blog.getTitle());
        System.out.println("saved blog content : " + blog.getContent());

        // id로 삭제
        this.blogService.deleteById(blog);

        // 삭제한 id로 조회하여 null인지 체크
        Blog result = this.blogService.getById(blog);

        Assertions.assertNull(result);
    }
}
