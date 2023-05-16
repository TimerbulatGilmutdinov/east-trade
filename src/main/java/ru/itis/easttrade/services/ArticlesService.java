package ru.itis.easttrade.services;

import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.easttrade.dto.AccountDto;
import ru.itis.easttrade.dto.ArticleDto;
import ru.itis.easttrade.dto.NewOrUpdateArticleDto;

import java.security.Principal;
import java.util.List;

public interface ArticlesService {
    ArticleDto getArticleById(Integer id);


    ArticleDto getArticleByTitle(String title);

    List<ArticleDto> getAllArticles();

    List<ArticleDto> getAllArticlesOrderByDateAsc();

    List<ArticleDto> getAllArticlesOrderByDateDesc();

    void updateArticleById(@RequestParam("id") Integer id, NewOrUpdateArticleDto article);

    ArticleDto saveArticle(ArticleDto articleDto, Principal principal);

    void deleteArticleById(Integer id);

    List<ArticleDto> getArticlesByAccount(AccountDto accountDto);
}
