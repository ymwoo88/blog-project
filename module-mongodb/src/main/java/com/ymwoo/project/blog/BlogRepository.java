package com.ymwoo.project.blog;

import com.ymwoo.project.common.SearchRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;


public interface BlogRepository extends PagingAndSortingRepository<Blog, String>, QueryByExampleExecutor<Blog>, SearchRepository <Blog, BlogSearch>
{
}
