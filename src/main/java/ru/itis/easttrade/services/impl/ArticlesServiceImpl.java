package ru.itis.easttrade.services.impl;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.itis.easttrade.dto.AccountDto;
import ru.itis.easttrade.dto.ArticleDto;
import ru.itis.easttrade.dto.UpdateArticleDto;
import ru.itis.easttrade.exceptions.NotFoundException;
import ru.itis.easttrade.models.Account;
import ru.itis.easttrade.models.Article;
import ru.itis.easttrade.repositories.AccountsRepository;
import ru.itis.easttrade.repositories.ArticlesRepository;
import ru.itis.easttrade.services.ArticlesService;
import ru.itis.easttrade.utils.RightsResolver;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticlesServiceImpl implements ArticlesService {
    private final ArticlesRepository articlesRepository;
    private final AccountsRepository accountsRepository;
    private final RightsResolver rightsResolver;

    @Override
    public ArticleDto getArticleById(Integer id) {
        Article article = articlesRepository.findById(id).orElseThrow(() -> new NotFoundException("Article with id <" + id + "> not found"));
        return ArticleDto.from(article);
    }

    @Override
    public ArticleDto getArticleByTitle(String title) {
        Article article = articlesRepository.findByTitle(title).orElseThrow(() -> new NotFoundException("Article with title <" + title + "> not found"));
        return ArticleDto.from(article);
    }

    @Override
    public List<ArticleDto> getArticlesByAccount(@ModelAttribute AccountDto accountDto) {
        Account account = accountsRepository.findByEmail(accountDto.getEmail()).orElseThrow(() -> new NotFoundException("Account with email <" + accountDto.getEmail() + "> not found"));
        return ArticleDto.from(articlesRepository.findAllByAccount(account));
    }

    @Override
    public List<ArticleDto> getAllArticles() {
        return ArticleDto.from(articlesRepository.findAll());
    }

    @Override
    public List<ArticleDto> getAllArticlesOrderByDateAsc() {
        return ArticleDto.from(articlesRepository.findAllByOrderByPublishDateAsc());
    }

    @Override
    public List<ArticleDto> getAllArticlesOrderByDateDesc() {
        return ArticleDto.from(articlesRepository.findAllByOrderByPublishDateDesc());
    }

    @Transactional
    public void updateArticleById(Integer id, @ModelAttribute UpdateArticleDto article, Authentication authentication) {
        articlesRepository.findById(id).orElseThrow(() -> new NotFoundException("Article with id <" + id + "> not found"));
        if (rightsResolver.resolveArticleAction(id, authentication)) {
            String newTitle = Jsoup.clean(article.getTitle(), Safelist.basic());
            String newContent = Jsoup.clean(article.getContent(), Safelist.basic());
            articlesRepository.updateById(id, newTitle, newContent);
        } else {
            throw new AccessDeniedException("You don't have rights to do that");
        }
    }

    @Transactional
    public ArticleDto saveArticle(ArticleDto articleDto, Authentication authentication) {
        String email = authentication.getName();
        Account account = accountsRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Account with email <" + email + "> not found"));

        Article article = Article.builder()
                .title(articleDto.getTitle())
                .content(Jsoup.clean(articleDto.getContent(), Safelist.basic()))
                .account(account)
                .publishDate(new Date())
                .build();
        return ArticleDto.from(articlesRepository.save(article));
    }

    @Transactional
    public void deleteArticleById(Integer id, Authentication authentication) {
        articlesRepository.findById(id).orElseThrow(() -> new NotFoundException("Article with id <" + id + "> not found"));
        if (rightsResolver.resolveArticleAction(id,authentication)) {
            articlesRepository.deleteById(id);
        } else {
            throw new AccessDeniedException("You don't have rights to do that");
        }
    }

    @Override
    public List<ArticleDto> getAllArticlesForToday(){
        return ArticleDto.from(articlesRepository.findAllForToday());
    }
}
