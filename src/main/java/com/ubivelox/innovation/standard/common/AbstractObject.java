package com.ubivelox.innovation.standard.common;

import java.time.LocalDateTime;

import javax.persistence.Column;

import org.springframework.data.domain.Persistable;

import lombok.Getter;

@Getter
public abstract class AbstractObject<ID> implements Persistable<ID>
{
    @Column(name = "regId")
    private String        owner;

    // TODO 스프링 데이터 Auditing 적용 예정
    @Column(name = "regDate")
    private LocalDateTime createdDateTime;
}
