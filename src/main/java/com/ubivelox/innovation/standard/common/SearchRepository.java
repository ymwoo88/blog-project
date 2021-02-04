package com.ubivelox.innovation.standard.common;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface SearchRepository<Entity, Search extends AbstractSearch>
{
    List<Entity> search(Search search);





    Long count(Search search);





    Boolean exists(Search search);
}
