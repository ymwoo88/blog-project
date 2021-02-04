package com.ubivelox.innovation.standard.blog;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.ubivelox.innovation.standard.common.SearchRepository;

@Component
public class BlogRepositoryImpl implements SearchRepository<Blog, BlogSearch>
{
    private final JpaRepository repository;

    public BlogRepositoryImpl(@Lazy JpaRepository<Blog, Long> jpaRepository)
    {
        this.repository = jpaRepository;
    }





    @Override
    public List<Blog> search(BlogSearch search)
    {
        // TODO 페이징 및 검색 조건을 설정하는 JPA를 더 숙지해서 로직 구현 필요
        Blog prove = new Blog();
        prove.setTitle(search.getTitle());
        prove.setContent(search.getContent());

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny()
                                                      .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                                                      .withIgnoreCase()
                                                      .withIgnoreNullValues();

        Example<Blog> example = Example.of(prove, exampleMatcher);

        return this.repository.findAll(example);
    }





    @Override
    public Long count(BlogSearch search)
    {
        // new Search()를 해서 사용하는 이유는 setPageable 변경을 위해서 새로만듬
        BlogSearch blogSearch = new BlogSearch();
        // TODO Data copy로직 구현 필요 파라메터 search > blogSearch로 복사

        // 전체 count를 위해 페이징 조건 제회
        blogSearch.setPageable(Pageable.unpaged());

        return this.search(blogSearch)
                   .stream()
                   .count();
    }





    @Override
    public Boolean exists(BlogSearch search)
    {
        return this.count(search) > 0;
    }
}
