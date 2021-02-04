package com.ubivelox.innovation.standard.blog;

import org.springframework.stereotype.Service;

import com.ubivelox.innovation.standard.common.CommonRestService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlogService extends CommonRestService<Long, Blog, BlogSearch, BlogRepository>
{
    @Override
    public Blog postPreProcess(Blog resource)
    {
        System.out.println("### postPreProcess : 블로그 서비스 전 처리를 태웁니다.");
        return resource;
    }





    @Override
    public Blog postPostProcess(Blog resouece)
    {
        System.out.println("### postPostProcess : 블로그 서비스 후 처리를 태웁니다.");
        return resouece;
    }





    @Override
    public Blog getListPostProcess(Blog resouece)
    {
        System.out.println("### getListPostProcess : 블로그 서비스 후 처리를 태웁니다.");
        return resouece;
    }

}
