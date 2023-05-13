package ru.itis.easttrade.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import ru.itis.easttrade.dto.ArticleDto;
import ru.itis.easttrade.dto.NewOrUpdateArticleDto;
import ru.itis.easttrade.dto.NewOrUpdateTaskDto;
import ru.itis.easttrade.models.Article;
import ru.itis.easttrade.services.ArticlesService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ArticlesController {
    private final ArticlesService articlesService;

    @GetMapping("/articles")
    public String getAllArticles(@RequestParam(value = "sortBy", defaultValue = "new") String sort, Model model) {
        List<ArticleDto> articles;
        if (sort.equals("new")) {
            articles = articlesService.getAllArticlesOrderByDateAsc();
        } else if (sort.equals("old")) {
            articles = articlesService.getAllArticlesOrderByDateDesc();
        } else {
            articles = articlesService.getAllArticlesOrderByDateAsc();
        }
        model.addAttribute("articles", articles);
        return "articles";
    }

    @GetMapping("/articles/{id}")
    public String getArticleById(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("article", articlesService.findArticleById(id));
        return "article";
    }

    @GetMapping("/create-article")
    public String getCreateArticle(Model model) {
        model.addAttribute("article", new NewOrUpdateArticleDto());
        return "create-article";
    }

    @PostMapping("/create-article")
    public String createArticle(@ModelAttribute("article") ArticleDto articleDto, Principal principal) {
        int id = articlesService.saveArticle(articleDto).getId();
        return "redirect:" + MvcUriComponentsBuilder.fromMappingName("TC#getArticleById").arg(0, id).build();
    }

    @GetMapping("/article/{id}/update")
    public String getUpdateArticle(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("article", articlesService.findArticleById(id));
        return "update-article";
    }

    @PostMapping("/article/{id}/update")
    public String updateArticle(@PathVariable("id") Integer id, @ModelAttribute("article") NewOrUpdateArticleDto articleDto, Model model) {
        articlesService.updateArticleById(id, articleDto);
        return "redirect:"+MvcUriComponentsBuilder.fromMappingName("AC#getArticleById").arg(0,id).build();
    }

}