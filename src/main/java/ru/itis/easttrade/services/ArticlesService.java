package ru.itis.easttrade.services;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.easttrade.dto.AccountDto;
import ru.itis.easttrade.dto.ArticleDto;
import ru.itis.easttrade.dto.UpdateArticleDto;

import java.security.Principal;
import java.util.List;

public interface ArticlesService {
    ArticleDto getArticleById(Integer id);


    ArticleDto getArticleByTitle(String title);

    List<ArticleDto> getAllArticles();

    List<ArticleDto> getAllArticlesOrderByDateAsc();

    List<ArticleDto> getAllArticlesOrderByDateDesc();

    void updateArticleById(@RequestParam("id") Integer id, UpdateArticleDto article, Authentication authentication);

    ArticleDto saveArticle(ArticleDto articleDto, Authentication authentication);

    void deleteArticleById(Integer id, Authentication authentication);

    List<ArticleDto> getArticlesByAccount(AccountDto accountDto);
    List<ArticleDto> getAllArticlesForToday();
}
