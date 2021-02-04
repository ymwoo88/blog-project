package com.ubivelox.innovation.standard.common;

import java.util.List;

public interface RestService<Entity, Search>
{
    Entity post(Entity entity);





    Entity getById(Entity entity);





    List<Entity> getList(Search search);





    Entity putById(Entity entity);





    void putList(Entity entity);





    void patchById(Entity entity);





    void patchList(Entity entity);





    void deleteById(Entity entity);





    void deleteList(Entity entity);
}
