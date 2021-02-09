package com.ymwoo.project.blog;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.ymwoo.project.common.SearchRepository;

public interface BlogRepository extends PagingAndSortingRepository<Blog, Long>, QueryByExampleExecutor<Blog>, SearchRepository<Blog, BlogSearch>
{
}
