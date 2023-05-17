package ru.itis.easttrade.services.impl;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.itis.easttrade.dto.AccountDto;
import ru.itis.easttrade.dto.ArticleDto;
import ru.itis.easttrade.dto.NewOrUpdateArticleDto;
import ru.itis.easttrade.exceptions.NotFoundException;
import ru.itis.easttrade.models.Account;
import ru.itis.easttrade.models.Article;
import ru.itis.easttrade.repositories.AccountsRepository;
import ru.itis.easttrade.repositories.ArticlesRepository;
import ru.itis.easttrade.services.ArticlesService;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ArticlesServiceImpl implements ArticlesService {
    private final ArticlesRepository articlesRepository;
    private final AccountsRepository accountsRepository;

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
    public void updateArticleById(Integer id, @ModelAttribute NewOrUpdateArticleDto article) {
        articlesRepository.findById(id).orElseThrow(()->new NotFoundException("Article with id <"+id+"> not found"));
        String newTitle = Jsoup.clean(article.getTitle(),Safelist.basic());
        String newContent = Jsoup.clean(article.getContent(),Safelist.basic());
        articlesRepository.updateById(id, newTitle, newContent);
    }

    @Transactional
    public ArticleDto saveArticle(ArticleDto articleDto, Principal principal) {
        String email = principal.getName();
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
    public void deleteArticleById(Integer id) {
        if (articlesRepository.findById(id).isPresent()) {
            articlesRepository.deleteById(id);
        } else {
            throw new NotFoundException("Article with id <" + id + "> does not exist");
        }
    }
}
