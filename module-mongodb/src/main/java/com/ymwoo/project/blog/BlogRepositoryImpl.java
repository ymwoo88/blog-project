package com.ymwoo.project.blog;

import static com.ymwoo.project.blog.QBlog.blog;
import static org.springframework.util.ObjectUtils.isEmpty;

import java.util.List;

import com.ymwoo.project.common.SearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.support.QuerydslRepositorySupport;
import org.springframework.data.mongodb.repository.support.SpringDataMongodbQuery;
import org.springframework.stereotype.Repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class BlogRepositoryImpl extends QuerydslRepositorySupport implements SearchRepository <Blog, BlogSearch>
{
    public BlogRepositoryImpl(MongoOperations mongoOperations)
    {
        super(mongoOperations);
    }





    @Override
    public List<Blog> searchList(BlogSearch search)
    {
        return from(blog).where(titleEq(search.getTitle()), contentEq(search.getContent()))
                         .fetch();
    }





    @Override
    public Blog searchOne(BlogSearch search)
    {
        return from(blog).where(titleEq(search.getTitle()), contentEq(search.getContent()))
                         .fetchOne();
    }





    @Override
    public Page<Blog> searchPage(BlogSearch search)
    {
        // 정렬 및 페이지
        Pageable pageable = search.getPageable();

        SpringDataMongodbQuery<Blog> query = from(blog).where(titleEq(search.getTitle()), contentEq(search.getContent()));
        extractedSort(query, pageable);

        QueryResults<Blog> results = query.offset(pageable.getOffset())
                                          .limit(pageable.getPageSize())
                                          .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }





    @Override
    public Long count(BlogSearch search)
    {
        return from(blog).where(titleEq(search.getTitle()), contentEq(search.getContent()))
                         .fetchCount();
    }





    @Override
    public Boolean exists(BlogSearch search)
    {
        return count(search) > 0;
    }





    private BooleanExpression titleEq(String title)
    {
        return isEmpty(title) ? null : blog.title.eq(title);
    }





    private BooleanExpression contentEq(String content)
    {
        return isEmpty(content) ? null : blog.content.eq(content);
    }





    private void extractedSort(SpringDataMongodbQuery query, Pageable pageable)
    {
        for ( Sort.Order o : pageable.getSort() )
        {
            PathBuilder<Blog> pathBuilder = new PathBuilder<>(blog.getType(), blog.getMetadata());
            query.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }
    }
}
