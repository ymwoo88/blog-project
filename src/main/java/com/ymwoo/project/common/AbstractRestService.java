package com.ymwoo.project.common;

public abstract class AbstractRestService<Entity, Search> implements RestService<Entity, Search>
{
    @Override
    public void putList(Entity entity)
    {
        throw new RuntimeException("METHOD_NOT_ALLOWED");
    }





    @Override
    public void patchById(Entity entity)
    {
        throw new RuntimeException("METHOD_NOT_ALLOWED");
    }





    @Override
    public void patchList(Entity entity)
    {
        throw new RuntimeException("METHOD_NOT_ALLOWED");
    }





    @Override
    public void deleteList(Entity entity)
    {
        throw new RuntimeException("METHOD_NOT_ALLOWED");
    }
}
