package com.ymwoo.project.common;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface SearchRepository<Entity, Search extends AbstractSearch>
{
    List<Entity> searchList(Search search);





    Entity searchOne(Search search);





    Page<Entity> searchPage(Search search);





    Long count(Search search);





    Boolean exists(Search search);
}
