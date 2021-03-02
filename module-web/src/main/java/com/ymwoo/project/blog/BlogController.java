package com.ymwoo.project.blog;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class BlogController
{

    private final BlogService blogService;

    @GetMapping("/")
    public String showForm(Blog blog)
    {
        return "form";
    }





    @PostMapping("/")
    public String save(@Valid Blog blog, BindingResult bindingResult)
    {
        if ( bindingResult.hasErrors() )
        {
            return "form";
        }

        String owner = "tester"; // TODO 시큐리티로 대체해야 함
        this.blogService.post(blog);

        return "redirect:/results";
    }





    @PostMapping("/save")
    @ResponseBody
    public Blog save(Blog blog)
    {
        return this.blogService.post(blog);
    }





    @PostMapping("/update")
    @ResponseBody
    public Blog update(Blog blog)
    {
        String owner = "tester"; // TODO 시큐리티로 대체해야 함
        blog.setOwner(owner);
        return this.blogService.putById(blog);
    }





    @PostMapping("/result")
    @ResponseBody
    public Blog result(Blog blog)
    {
        String owner = "tester"; // TODO 시큐리티로 대체해야 함
        blog.setOwner(owner);
        return this.blogService.getById(blog);
    }





    @GetMapping("/results")
    public String results(BlogSearch blogSearch, Model model)
    {
        // TODO 화면 미구현으로 임의로 페이징 값 셋팅
        blogSearch.setPageable(PageRequest.of(0, 100, Sort.by(Sort.Order.desc("title"))));

        model.addAttribute("blogs", this.blogService.getList(blogSearch));
        return "results";
    }





    @PostMapping("/delete")
    @ResponseBody
    public void delete(Blog blog)
    {
        String owner = "tester"; // TODO 시리티로 대체해야 함
        blog.setOwner(owner);
        this.blogService.deleteById(blog);
    }

}
