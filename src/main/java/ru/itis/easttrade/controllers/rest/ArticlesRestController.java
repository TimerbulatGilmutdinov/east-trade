package ru.itis.easttrade.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.easttrade.controllers.rest.api.ArticlesApi;
import ru.itis.easttrade.dto.ArticleDto;
import ru.itis.easttrade.dto.UpdateArticleDto;
import ru.itis.easttrade.services.ArticlesService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class ArticlesRestController implements ArticlesApi {
    private final ArticlesService articlesService;

    @Override
    public ResponseEntity<ArticleDto> getArticleById(Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(articlesService.getArticleById(id));
    }

    @Override
    public ResponseEntity<ArticleDto> createArticle(@RequestBody ArticleDto articleDto, Authentication authentication) {
        return null;
    }

    @Override
    public ResponseEntity<ArticleDto> updateArticleById(Integer id, @RequestBody UpdateArticleDto updatedArticle, Authentication authentication) {
        articlesService.updateArticleById(id, updatedArticle, authentication);
        return ResponseEntity.accepted()
                .body(articlesService.getArticleById(id));
    }

    @Override
    public ResponseEntity<?> deleteArticleById(Integer id, Authentication authentication) {
        return null;
    }
}
