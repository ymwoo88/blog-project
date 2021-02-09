package com.ymwoo.project.common;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface SearchRepository<Entity, Search extends AbstractSearch>
{
    Page search(Search search);





    Long count(Search search);





    Boolean exists(Search search);
}
