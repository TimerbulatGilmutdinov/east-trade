package ru.itis.easttrade.services;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.easttrade.dto.ArticleDto;
import ru.itis.easttrade.dto.NewOrUpdateArticleDto;
import ru.itis.easttrade.exceptions.AlreadyExistsException;
import ru.itis.easttrade.exceptions.NotFoundException;
import ru.itis.easttrade.models.Article;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ArticlesService {
    ArticleDto findArticleById(Integer id);


    ArticleDto findArticleByTitle(String title);

    List<ArticleDto> getAllArticles();

    List<ArticleDto> getAllArticlesOrderByDateAsc();

    List<ArticleDto> getAllArticlesOrderByDateDesc();

    ArticleDto updateArticleById(@RequestParam("id") Integer id, NewOrUpdateArticleDto article);

    ArticleDto saveArticle(ArticleDto articleDto);

    void deleteArticleById(Integer id);
}
