package ru.itis.easttrade.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import ru.itis.easttrade.dto.AccountDto;
import ru.itis.easttrade.dto.ArticleDto;
import ru.itis.easttrade.dto.UpdateArticleDto;
import ru.itis.easttrade.services.AccountsService;
import ru.itis.easttrade.services.ArticlesService;
import ru.itis.easttrade.services.TasksService;
import ru.itis.easttrade.utils.RoleChecker;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ArticlesController {
    private final AccountsService accountsService;
    private final ArticlesService articlesService;
    private final RoleChecker roleChecker;

    @GetMapping("/articles")
    public String getAllArticles(@RequestParam(name = "sort",defaultValue = "new") String sort, Model model, Authentication authentication) {
        List<ArticleDto> articles = articlesService.getAllArticlesOrderByDateDesc();
        if (sort.equals("old")) {
            articles.sort(Comparator.comparing(ArticleDto::getPublishDate));
        }
        model.addAttribute("authentication",authentication);
        model.addAttribute("sorted",sort);
        model.addAttribute("articles", articles);
        return "articles";
    }

    @GetMapping("/my-articles")
    public String myArticles(Model model, Authentication authentication) {
        AccountDto accountDto = accountsService.getAccountByEmail(authentication.getName());
        List<ArticleDto> articles = articlesService.getArticlesByAccount(accountDto);
        model.addAttribute("articles", articles);
        return "my-articles";
    }
    @GetMapping("/articles/{id}")
    public String getArticleById(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        model.addAttribute("authentication",authentication);
        model.addAttribute("hasEnoughAuthority", roleChecker.hasEnoughAuthority(authentication));
        model.addAttribute("article", articlesService.getArticleById(id));
        return "article";
    }

    @GetMapping("/create-article")
    public String getCreateArticle(Model model) {
        model.addAttribute("article", new UpdateArticleDto());
        return "create-article";
    }

    @PostMapping("/create-article")
    public String createArticle(@ModelAttribute("article") ArticleDto articleDto, Authentication authentication) {
        int id = articlesService.saveArticle(articleDto,authentication).getId();
        return "redirect:" + MvcUriComponentsBuilder.fromMappingName("AC#getArticleById").arg(0, id).build();
    }

    @GetMapping("/articles/{id}/update")
    public String getUpdateArticle(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        model.addAttribute("article", articlesService.getArticleById(id));
        return "update-article";
    }

    @PostMapping("/articles/{id}/update")
    public String updateArticle(@PathVariable("id") Integer id, @ModelAttribute("article") UpdateArticleDto articleDto, Model model) {
        articlesService.updateArticleById(id, articleDto);
        return "redirect:"+MvcUriComponentsBuilder.fromMappingName("AC#getArticleById").arg(0,id).build();
    }

    @DeleteMapping("/articles/{id}")
    public void deleteArticle(@PathVariable("id") Integer id){
        articlesService.deleteArticleById(id);
    }
}
