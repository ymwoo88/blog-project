package com.ymwoo.project.common;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.Persistable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractObject<ID> implements Persistable<ID>
{
    private String        owner;

    // TODO 스프링 데이터 Auditing 적용 예정
    @CreatedDate
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    private LocalDateTime modifiedDateTime;

    @Version
    protected Long        version;

    @Override
    public boolean isNew()
    {
        return getId() == null;
    }





    public void copyBaseProperties(final AbstractObject<ID> from)
    {
        setCreatedDateTime(from.getCreatedDateTime());
        setModifiedDateTime(from.getModifiedDateTime());
        setVersion(from.getVersion());
    }
}
