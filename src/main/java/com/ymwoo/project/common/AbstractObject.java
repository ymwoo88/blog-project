package com.ymwoo.project.common;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractObject<ID> implements Persistable<ID>
{
    @Column(name = "regId")
    private String        owner;

    // TODO 스프링 데이터 Auditing 적용 예정
    @Column(name = "regDate")
    @CreatedDate
    private LocalDateTime createdDateTime;

    @Column(name = "modDate")
    @LastModifiedDate
    private LocalDateTime modifiedDateTime;

    @Override
    public boolean isNew()
    {
        return getId() == null;
    }





    public void copyBaseProperties(final AbstractObject from)
    {
        setCreatedDateTime(from.getCreatedDateTime());
        setModifiedDateTime(from.getModifiedDateTime());
    }
}
