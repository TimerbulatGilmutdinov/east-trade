package ru.itis.easttrade.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import ru.itis.easttrade.dto.AccountDto;
import ru.itis.easttrade.dto.ArticleDto;
import ru.itis.easttrade.dto.NewOrUpdateArticleDto;
import ru.itis.easttrade.dto.TaskDto;
import ru.itis.easttrade.services.AccountsService;
import ru.itis.easttrade.services.ArticlesService;
import ru.itis.easttrade.services.TasksService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ArticlesController {
    private final TasksService tasksService;
    private final AccountsService accountsService;
    private final ArticlesService articlesService;

    @GetMapping("/articles")
    public String getAllArticles(@RequestParam(value = "sortBy",defaultValue = "new") String sort, Model model) {
        List<ArticleDto> articles;
        if (sort.equals("new")) {
            articles = articlesService.getAllArticlesOrderByDateAsc();
        } else if (sort.equals("old")) {
            articles = articlesService.getAllArticlesOrderByDateDesc();
        } else {
            articles = articlesService.getAllArticlesOrderByDateDesc();
        }
        model.addAttribute("articles", articles);
        return "articles";
    }

    @GetMapping("/my-articles")
    public String myArticles(Model model, Principal principal) {
        AccountDto accountDto = accountsService.getAccountByEmail(principal.getName());
        List<ArticleDto> articles = articlesService.getArticlesByAccount(accountDto);
        model.addAttribute("articles", articles);
        return "my-articles";
    }
    @GetMapping("/articles/{id}")
    public String getArticleById(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("article", articlesService.getArticleById(id));
        return "article";
    }

    @GetMapping("/create-article")
    public String getCreateArticle(Model model) {
        model.addAttribute("article", new NewOrUpdateArticleDto());
        return "create-article";
    }

    @PostMapping("/create-article")
    public String createArticle(@ModelAttribute("article") ArticleDto articleDto, Principal principal) {
        int id = articlesService.saveArticle(articleDto,principal).getId();
        return "redirect:" + MvcUriComponentsBuilder.fromMappingName("AC#getArticleById").arg(0, id).build();
    }

    @GetMapping("/articles/{id}/update")
    public String getUpdateArticle(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("article", articlesService.getArticleById(id));
        return "update-article";
    }

    @PostMapping("/articles/{id}/update")
    public String updateArticle(@PathVariable("id") Integer id, @ModelAttribute("article") NewOrUpdateArticleDto articleDto, Model model) {
        articlesService.updateArticleById(id, articleDto);
        return "redirect:"+MvcUriComponentsBuilder.fromMappingName("AC#getArticleById").arg(0,id).build();
    }

    @DeleteMapping("/articles/{id}")
    public void deleteArticle(@PathVariable("id") Integer id){
        articlesService.deleteArticleById(id);
    }
}
