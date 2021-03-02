package com.ymwoo.project.common;

import org.springframework.data.domain.Page;

public interface RestService<Entity, Search>
{
    Entity post(Entity entity);





    Entity getById(Entity entity);





    Page<Entity> getList(Search search);





    Entity putById(Entity entity);





    void putList(Entity entity);





    void patchById(Entity entity);





    void patchList(Entity entity);





    void deleteById(Entity entity);





    void deleteList(Entity entity);
}
