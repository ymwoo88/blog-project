package com.ubivelox.innovation.standard.blog;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ubivelox.innovation.standard.common.SearchRepository;

public interface BlogRepository extends JpaRepository<Blog, Long>, SearchRepository<Blog, BlogSearch>
{
}
