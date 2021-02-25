package com.ymwoo.project.blog;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.NonUniqueResultException;
import com.querydsl.jpa.impl.JPAQueryFactory;

@SpringBootTest
@Transactional
class BlogRepositoryImplTest
{

    @Autowired
    EntityManager   em;

    JPAQueryFactory queryFactory;

    final int       TOTAL_SIZE = 9;

    @BeforeEach
    void setUp()
    {
        this.queryFactory = new JPAQueryFactory( this.em );

        for (int i = 1; i <= this.TOTAL_SIZE; i++ )
        {
            Blog blog = creatBlog("title" + i, "content" + i);
            this.em.persist( blog);
        }
    }

    @Autowired
    BlogRepository blogRepository;

    @Test
    void searchListAll()
    {
        // given

        // when
        List<Blog> blogs = this.blogRepository.searchList( new BlogSearch());

        // then
        assertThat(blogs.size()).isEqualTo( this.TOTAL_SIZE );
    }





    @Test
    void searchListByParam()
    {
        // given
        BlogSearch search = new BlogSearch();
        search.setTitle( "title" + this.TOTAL_SIZE );
        search.setContent( "content" + this.TOTAL_SIZE );

        // when
        List<Blog> blogs = this.blogRepository.searchList( search);

        // then
        assertThat(blogs.size()).isEqualTo(1);
    }





    @Test
    void searchOne_NoRecord()
    {
        // given
        String paramTitle = "title" + this.TOTAL_SIZE + 10;
        String paramContent = "content" + this.TOTAL_SIZE + 10;

        BlogSearch search = new BlogSearch();
        search.setTitle(paramTitle);
        search.setContent(paramContent);

        // when
        Blog blog = this.blogRepository.searchOne( search);

        // then
        assertThat(blog).isNull();
    }





    @Test
    void searchOne_Duplicate()
    {
        Exception exception = assertThrows(NonUniqueResultException.class, () -> {
            // given
            String paramTitle = "title" + this.TOTAL_SIZE;
            String paramContent = "content" + this.TOTAL_SIZE;

            Blog dupleBlog = new Blog();
            dupleBlog.setTitle(paramTitle);
            dupleBlog.setContent(paramContent);
            this.em.persist( dupleBlog);

            BlogSearch search = new BlogSearch();
            search.setTitle(paramTitle);
            search.setContent(paramContent);

            // when
            this.blogRepository.searchOne( search);
        });

        assertThat(exception).isInstanceOf(NonUniqueResultException.class);
    }





    @Test
    void searchOne_OK()
    {
        // given
        String paramTitle = "title" + this.TOTAL_SIZE;
        String paramContent = "content" + this.TOTAL_SIZE;

        BlogSearch search = new BlogSearch();
        search.setTitle(paramTitle);
        search.setContent(paramContent);

        // when
        Blog blog = this.blogRepository.searchOne( search);

        // then
        assertThat(blog.getTitle()).isEqualTo(paramTitle);
        assertThat(blog.getContent()).isEqualTo(paramContent);
    }





    @Test
    void searchPage()
    {
        // given
        final int pageSize = 3;
        final String sortKey = "title";

        // when
        BlogSearch search = new BlogSearch();
        search.setPageable(PageRequest.of(0, pageSize, Sort.by(Sort.Order.desc(sortKey))));
        Page<Blog> page = this.blogRepository.searchPage( search);

        // then
        assertThat(page.getTotalElements()).isEqualTo( this.TOTAL_SIZE );
        assertThat(page.getSize()).isEqualTo(pageSize);
        assertThat(page.getContent()
                       .stream()
                       .findFirst()
                       .get()
                       .getTitle()).isEqualTo( "title" + this.TOTAL_SIZE );
    }





    private Blog creatBlog(String title, String content)
    {
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setContent(content);
        return blog;
    }

}
