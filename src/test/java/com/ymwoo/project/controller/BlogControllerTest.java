package com.ymwoo.project.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ymwoo.project.blog.Blog;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BlogControllerTest
{
    @Autowired
    ObjectMapper  objectMapper;
    @Autowired
    MockMvc       mvc;
    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void init()
    {
        int count = 1;
        for ( int loop : new int[10] )
        {
            Blog blog = new Blog();
            blog.setTitle("title" + count);
            blog.setContent("content" + count);
            this.em.persist(blog);

            count++;
        }
    }





    @Test
    @DisplayName("데이터 단건 등록")
    @WithMockUser(username = "test",
                  roles = { "USER" })
    void postTest() throws Exception
    {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("title", "post title");
        paramMap.add("content", "post content");

        MvcResult addResult = this.mvc.perform(post("/save").params(paramMap)
                                                            .with(csrf()))
                                      .andExpect(status().isOk())
                                      .andReturn();

        Blog blog = this.objectMapper.readValue(addResult.getResponse()
                                                         .getContentAsString(),
                                                Blog.class);

        Assertions.assertEquals("post title", blog.getTitle());
        Assertions.assertEquals("post content", blog.getContent());
    }





    @Test
    @DisplayName("데이터 등록 후 title, content 업데이트")
    @WithMockUser(username = "test",
                  roles = { "USER" })
    void putByIdTest() throws Exception
    {
        // 데이터 등록
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("title", "post title");
        paramMap.add("content", "post content");

        MvcResult addResult = this.mvc.perform(post("/save").params(paramMap)
                                                            .with(csrf()))
                                      .andExpect(status().isOk())
                                      .andReturn();

        Blog blog = this.objectMapper.readValue(addResult.getResponse()
                                                         .getContentAsString(),
                                                Blog.class);

        // 데이터 업데이트
        MultiValueMap<String, String> updateParamMap = new LinkedMultiValueMap<>();
        updateParamMap.add("id",
                           blog.getId()
                               .toString());
        updateParamMap.add("title", "update title");
        updateParamMap.add("content", "update content");

        MvcResult updateResult = this.mvc.perform(post("/update").params(updateParamMap)
                                                                 .with(csrf()))
                                         .andExpect(status().isOk())
                                         .andReturn();

        Blog updatedBlog = this.objectMapper.readValue(updateResult.getResponse()
                                                                   .getContentAsString(),
                                                       Blog.class);

        Assertions.assertEquals("update title", updatedBlog.getTitle());
        Assertions.assertEquals("update content", updatedBlog.getContent());
    }





    @Test
    @DisplayName("init에 등록 한 데이터 중 id=2항목을 조회")
    @WithMockUser(username = "test",
                  roles = { "USER" })
    void getByIdTest() throws Exception
    {
        MultiValueMap<String, String> updateParamMap = new LinkedMultiValueMap<>();
        updateParamMap.add("id", "2");

        MvcResult result = this.mvc.perform(post("/result").params(updateParamMap)
                                                           .with(csrf()))
                                   .andExpect(status().isOk())
                                   .andReturn();

        Blog blog = this.objectMapper.readValue(result.getResponse()
                                                      .getContentAsString(),
                                                Blog.class);

        Assertions.assertEquals("title2", blog.getTitle());
        Assertions.assertEquals("content2", blog.getContent());
    }





    @Test
    @DisplayName("10개의 목록 중 5개 size단위 및 정렬 ASC 페이지 조회")
    @WithMockUser(username = "test",
                  roles = { "USER" })
    void getListPageSortAscTest() throws Exception
    {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("page", "0");
        paramMap.add("size", "5");
        paramMap.add("sort", "title|1");

        this.mvc.perform(get("/results").params(paramMap))
                .andExpect(status().isOk())
                .andExpect(view().name("results"))
                .andDo(print());
    }





    @Test
    @DisplayName("10개의 목록 중 7개 size단위 및 정렬 DESC 페이지 조회")
    @WithMockUser(username = "test",
                  roles = { "USER" })
    void getListPageSortDescTest() throws Exception
    {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("page", "0");
        paramMap.add("size", "7");
        paramMap.add("sort", "title|-1");

        this.mvc.perform(get("/results").params(paramMap))
                .andExpect(status().isOk())
                .andExpect(view().name("results"))
                .andDo(print());
    }





    @Test
    @DisplayName("10개의 목록 중 검색 조건이 매칭되는 결과 단건 조회")
    @WithMockUser(username = "test",
                  roles = { "USER" })
    void getListSearchTest() throws Exception
    {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();

        paramMap.add("title", "title1");
        paramMap.add("content", "content1");
        paramMap.add("page", "0");
        paramMap.add("size", "10");
        paramMap.add("sort", "title|-1");

        this.mvc.perform(get("/results").params(paramMap))
                .andExpect(status().isOk())
                .andExpect(view().name("results"))
                .andDo(print());
    }





    @Test
    @DisplayName("등록 한 데이터 항목을 삭제")
    @WithMockUser(username = "test",
                  roles = { "USER" })
    void deleteByIdTest() throws Exception
    {
        // 데이터 등록
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("title", "post title");
        paramMap.add("content", "post content");

        MvcResult addResult = this.mvc.perform(post("/save").params(paramMap)
                                                            .with(csrf()))
                                      .andExpect(status().isOk())
                                      .andReturn();

        Blog blog = this.objectMapper.readValue(addResult.getResponse()
                                                         .getContentAsString(),
                                                Blog.class);

        // 데이터 삭제
        MultiValueMap<String, String> deleteParamMap = new LinkedMultiValueMap<>();
        deleteParamMap.add("id",
                           blog.getId()
                               .toString());

        System.out.println("delete id : " + blog.getId()
                                                .toString());

        this.mvc.perform(post("/delete").params(deleteParamMap)
                                        .with(csrf()))
                .andExpect(status().isOk());

        // 삭제한 데이터 조회
        MvcResult result = this.mvc.perform(post("/result").params(deleteParamMap)
                                                           .with(csrf()))
                                   .andExpect(status().isOk())
                                   .andReturn();

        Assertions.assertEquals("",
                                result.getResponse()
                                      .getContentAsString());
    }
}
