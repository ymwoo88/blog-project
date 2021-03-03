package com.ymwoo.project.blog;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ymwoo.project.blog.Blog;
import com.ymwoo.project.blog.BlogSearch;
import com.ymwoo.project.common.SearchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ymwoo.project.blog.QBlog.blog;
import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Repository
public class BlogRepositoryImpl implements SearchRepository <Blog, BlogSearch>
{
    private final JPAQueryFactory queryFactory;

    public BlogRepositoryImpl(EntityManager em)
    {
        this.queryFactory = new JPAQueryFactory(em);
    }





    @Override
    public List<Blog> searchList(BlogSearch search)
    {
        return this.queryFactory.selectFrom(blog)
                                .where(titleEq(search.getTitle()), contentEq(search.getContent()))
                                .fetch();
    }





    @Override
    public Blog searchOne(BlogSearch search)
    {
        return this.queryFactory.selectFrom(blog)
                                .where(titleEq(search.getTitle()), contentEq(search.getContent()))
                                .fetchOne();
    }





    @Override
    public Page<Blog> searchPage(BlogSearch search)
    {
        JPAQuery<Blog> query = this.queryFactory.selectFrom( blog)
                                                .where(titleEq(search.getTitle()), contentEq(search.getContent()));

        Pageable pageable = search.getPageable();
        extractedSort(query, pageable);

        QueryResults<Blog> results = query.offset( pageable.getOffset())
                                          .limit(pageable.getPageSize())
                                          .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }





    @Override
    public Long count(BlogSearch search)
    {
        return this.queryFactory.selectFrom(blog)
                                .where(titleEq(search.getTitle()), contentEq(search.getContent()))
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





    private void extractedSort(JPAQuery<Blog> query, Pageable pageable)
    {
        for ( Sort.Order o : pageable.getSort() )
        {
            PathBuilder<Blog> pathBuilder = new PathBuilder<>( blog.getType(), blog.getMetadata());
            query.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }
    }
}
