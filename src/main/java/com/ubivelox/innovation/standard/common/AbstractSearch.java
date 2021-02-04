package com.ubivelox.innovation.standard.common;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.thymeleaf.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Data
@SuperBuilder
public abstract class AbstractSearch
{
    private Long         id;

    private Pageable     pageable;

    private Integer      page;
    private Integer      size;
    private List<String> sort;

    @JsonIgnore
    public Pageable getPageable()
    {
        if ( null == this.pageable )
        {
            Sort sort = extractSort();
            if ( null == this.page && null == this.size )
            {
                if ( null == sort || Sort.unsorted() == sort )
                {
                    return Pageable.unpaged();
                }
                else
                {
                    return PageRequest.of(0, Integer.MAX_VALUE, sort);
                }
            }
            else
            {
                if ( null == this.page )
                {
                    this.page = 0;
                }
                if ( null == this.size )
                {
                    this.size = Integer.MAX_VALUE;
                }
                return PageRequest.of(null != getPage() ? getPage() : 0, getSize(), (null == sort) ? Sort.unsorted() : sort);
            }
        }
        return this.pageable;
    }





    public Sort extractSort()
    {
        if ( null == this.sort )
        {
            return Sort.unsorted();
        }
        return Sort.by(this.sort.stream()
                                .map(s -> s.split("\\|"))
                                .filter(sa -> 1 == sa.length || 2 == sa.length)
                                .map(sa -> {
                                    if ( 1 == sa.length )
                                    {
                                        return Sort.Order.asc(sa[0]);
                                    }
                                    else
                                    {
                                        return Sort.Order.by(sa[0])
                                                         .with(StringUtils.equals("1", sa[1]) ? Sort.Direction.ASC : Sort.Direction.DESC);
                                    }
                                })
                                .collect(Collectors.toList()));
    }
}
