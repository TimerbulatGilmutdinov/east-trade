package ru.itis.easttrade.services.impl;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.easttrade.dto.ArticleDto;
import ru.itis.easttrade.dto.NewOrUpdateArticleDto;
import ru.itis.easttrade.exceptions.AlreadyExistsException;
import ru.itis.easttrade.exceptions.NotFoundException;
import ru.itis.easttrade.models.Article;
import ru.itis.easttrade.repositories.ArticlesRepository;
import ru.itis.easttrade.services.ArticlesService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ArticlesServiceImpl implements ArticlesService {
    private final ArticlesRepository articlesRepository;

    public ArticleDto findArticleById(Integer id) {
        Article article = articlesRepository.findById(id).orElseThrow(() -> new NotFoundException("Article with id <" + id + "> not found"));
        return ArticleDto.from(article);
    }

    public ArticleDto findArticleByTitle(String title) {
        Article article = articlesRepository.findByTitle(title).orElseThrow(() -> new NotFoundException("Article with title <" + title + "> not found"));
        return ArticleDto.from(article);
    }

    public List<ArticleDto> getAllArticles() {
        return ArticleDto.from(articlesRepository.findAll());
    }

    public List<ArticleDto> getAllArticlesOrderByDateAsc(){
        return ArticleDto.from(articlesRepository.findAllByOrderByPublishDateAsc());
    }

    public List<ArticleDto> getAllArticlesOrderByDateDesc(){
        return ArticleDto.from(articlesRepository.findAllByOrderByPublishDateDesc());
    }

    @Transactional
    public ArticleDto updateArticleById(@RequestParam("id") Integer id, NewOrUpdateArticleDto article) {
        Optional<Article> articleDB = articlesRepository.findById(id);
        if (articleDB.isEmpty()) {
            throw new NotFoundException("Article with id <" + id + "> does not exist");
        }
        article.setContent(Jsoup.clean(article.getContent(), Safelist.basic()));
        return ArticleDto.from(articlesRepository.updateById(id, article.getTitle(), article.getContent()));
    }

    @Transactional
    public ArticleDto saveArticle(ArticleDto articleDto) {
        Optional<Article> articleDB = articlesRepository.findById(articleDto.getId());
        if (articleDB.isPresent()) {
            throw new AlreadyExistsException("Article with id <" + articleDB.get().getId() + "> already exists");
        }
        Article article = Article.builder()
                .title(articleDto.getTitle())
                .content(Jsoup.clean(articleDto.getContent(), Safelist.basic()))
                .build();
        return ArticleDto.from(articlesRepository.save(article));
    }

    @Transactional
    public void deleteArticleById(Integer id) {
        if (articlesRepository.findById(id).isPresent()) {
            articlesRepository.deleteById(id);
        } else {
            throw new NotFoundException("Article with id <" + id + "> does not exist");
        }
    }
}
