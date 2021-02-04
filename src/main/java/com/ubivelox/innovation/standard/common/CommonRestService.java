package com.ubivelox.innovation.standard.common;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public class CommonRestService<ID, Entity extends AbstractObject, Search extends AbstractSearch, BaseRepository extends JpaRepository<Entity, ID> & SearchRepository<Entity, Search>>
        extends AbstractRestService<Entity, Search> implements InitializingBean
{
    @Autowired
    protected BaseRepository                                        baseRepository;

    protected CommonRestService<ID, Entity, Search, BaseRepository> instance;

    @Override
    public void afterPropertiesSet()
    {
        if ( null == this.baseRepository )
        {
            throw new IllegalStateException("The baseRepository is null.");
        }

        this.instance = this;
    }





    @Override
    public Entity post(Entity entity)
    {
        Entity entityData = entity;

        if ( Optional.ofNullable(entityData.getId())
                     .isPresent() )
        {
            throw new RuntimeException("ID is exsist.");
        }

        // 전 처리
        Entity preEntity = this.instance.postPreProcess(entityData);
        if ( Optional.ofNullable(preEntity)
                     .isPresent() )
        {
            entityData = preEntity;
        }

        // DB process
        Entity resource = this.baseRepository.save(entity);

        // 후 처리
        Entity postEntity = this.instance.postPostProcess(resource);
        if ( Optional.ofNullable(postEntity)
                     .isPresent() )
        {
            resource = postEntity;
        }
        return resource;
    }





    @Override
    public Entity getById(Entity entity)
    {
        Entity entityData = entity;

        if ( Optional.ofNullable(entityData.getId())
                     .isEmpty() )
        {
            throw new RuntimeException("ID is null.");
        }

        // 전 처리
        this.instance.getByIdPreProcess(entityData);

        // DB process
        Optional<Entity> optional = this.baseRepository.findById((ID) entityData.getId());
        Entity resource = optional.isEmpty() ? null : optional.get();

        // 후 처리
        Entity postEntity = this.instance.getByIdPostProcess(resource);
        if ( Optional.ofNullable(postEntity)
                     .isPresent() )
        {
            resource = postEntity;
        }

        return resource;
    }





    @Override
    public List<Entity> getList(Search search)
    {
        Search searchData = search;

        // 전처리
        Search preSearch = this.instance.getListPreProcess(searchData);
        if ( Optional.ofNullable(preSearch)
                     .isPresent() )
        {
            searchData = preSearch;
        }

        // DB process
        List<Entity> resources = this.baseRepository.search(searchData);

        List<Entity> result = resources.stream()
                                       .map(resource -> {
                                           // 후처리
                                           Entity postEntity = this.instance.getListPostProcess(resource);
                                           if ( Optional.ofNullable(postEntity)
                                                        .isPresent() )
                                           {
                                               resource = postEntity;
                                           }
                                           return resource;
                                       })
                                       .collect(toList());

        return result;
    }





    @Override
    public Entity putById(Entity entity)
    {
        Entity entityData = entity;

        if ( Optional.ofNullable(entityData.getId())
                     .isEmpty() )
        {
            throw new RuntimeException("ID is null.");
        }

        Optional<Entity> optional = this.baseRepository.findById((ID) entityData.getId());
        Entity oldData = optional.isEmpty() ? null : optional.get();

        // 전 처리
        Entity preEntity = this.instance.putByIdPreProcess(oldData, entityData);
        if ( Optional.ofNullable(preEntity)
                     .isPresent() )
        {
            entityData = preEntity;
        }

        // DB process
        Entity resource = this.baseRepository.save(entityData);

        // 후 처리
        Entity postEntity = this.instance.putByIdPostProcess(resource);
        if ( Optional.ofNullable(postEntity)
                     .isPresent() )
        {
            resource = postEntity;
        }

        return resource;
    }





    @Override
    public void deleteById(Entity entity)
    {
        Entity entityData = entity;
        if ( Optional.ofNullable(entityData.getId())
                     .isEmpty() )
        {
            throw new RuntimeException("ID is null.");
        }

        // 전 처리
        this.instance.deleteByIdPreProcess(entityData);

        // DB process
        this.baseRepository.deleteById((ID) entityData.getId());

        // 후 처리
        this.instance.deleteByIdPostProcess(entityData);
    }





    ////////////////////////////////////////////////////////////////////////////////
    protected Entity postPreProcess(Entity entity)
    {
        return null;
    }





    protected Entity postPostProcess(Entity entity)
    {
        return null;
    }





    protected void getByIdPreProcess(Entity entity)
    {
    }





    protected Entity getByIdPostProcess(Entity entity)
    {
        return null;
    }





    protected Search getListPreProcess(Search search)
    {
        return null;
    }





    protected Entity getListPostProcess(Entity entity)
    {
        return null;
    }





    protected Entity putByIdPreProcess(Entity before, Entity after)
    {
        return null;
    }





    protected Entity putByIdPostProcess(Entity entity)
    {
        return null;
    }





    protected void deleteByIdPreProcess(Entity entity)
    {
    }





    protected void deleteByIdPostProcess(Entity entity)
    {
    }





    protected List<Entity> getListCollectionPostProcess(Entity response)
    {
        return null;
    }
}
