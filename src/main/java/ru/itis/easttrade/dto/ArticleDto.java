package ru.itis.easttrade.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.easttrade.models.Article;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {

    private Integer id;
    private String title;
    private String content;
    private Date publishDate;
    private String accountEmail;
    private String accountInfo;

    public static ArticleDto from(Article article) {
        return ArticleDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .publishDate(article.getPublishDate())
                .accountEmail(article.getAccount().getEmail())
                .accountInfo(article.getAccount().toString())
                .build();
    }

    public static List<ArticleDto> from(List<Article> articles) {
        return articles.stream()
                .map(ArticleDto::from)
                .collect(Collectors.toList());
    }
}