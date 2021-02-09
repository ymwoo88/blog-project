package com.ymwoo.project.blog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.ymwoo.project.common.SearchRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class BlogRepositoryImpl implements SearchRepository<Blog, BlogSearch>
{
    @PersistenceContext
    private EntityManager em;

    @Override
    public Page search(BlogSearch search)
    {
        CriteriaBuilder builder = this.em.getCriteriaBuilder();
        CriteriaQuery<Blog> query = builder.createQuery(Blog.class);
        Root<Blog> root = query.from(Blog.class);

        // select from
        query.select(root);

        // where
        List<Predicate> predicates = new ArrayList<>();
        if ( Optional.ofNullable(search.getTitle())
                     .isPresent() )
        {
            predicates.add(builder.equal(root.get("title"), search.getTitle()));
        }
        if ( Optional.ofNullable(search.getContent())
                     .isPresent() )
        {
            predicates.add(builder.equal(root.get("content"), search.getContent()));
        }
        query.where(predicates.toArray(new Predicate[predicates.size()]));

        // order by
        search.extractSort()
              .stream()
              .forEach(sort -> {
                  if ( Sort.Direction.ASC.equals(sort.getDirection()) )
                  {
                      query.orderBy(builder.asc(root.get(sort.getProperty())));
                  }
                  else
                  {
                      query.orderBy(builder.desc(root.get(sort.getProperty())));
                  }
              });

        // 전체 조회 수
        int totalElements = this.em.createQuery(query)
                                   .getResultList()
                                   .size();

        // 검색 매칭 data 리스트
        Pageable pageable = search.getPageable();
        List<Blog> resultList = this.em.createQuery(query)
                                       .setFirstResult(Long.valueOf(pageable.getOffset())
                                                           .intValue())
                                       .setMaxResults(pageable.getPageSize())
                                       .getResultList();

        return new PageImpl(resultList, pageable, totalElements);
    }





    @Override
    public Long count(BlogSearch search)
    {
        return this.search(search)
                   .getContent()
                   .stream()
                   .count();
    }





    @Override
    public Boolean exists(BlogSearch search)
    {
        return this.search(search)
                   .getContent()
                   .stream()
                   .count() > 0;
    }
}
