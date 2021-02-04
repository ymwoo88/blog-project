package com.ubivelox.innovation.standard.blog;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;

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





    @GetMapping("/results")
    public String results(Model model)
    {
        BlogSearch blogSearch = BlogSearch.builder()
                                          .build();
        model.addAttribute("blogs", this.blogService.getList(blogSearch));

        return "results";
    }

}
