package ru.itis.easttrade.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.itis.easttrade.exceptions.NotFoundException;
import ru.itis.easttrade.repositories.ArticlesRepository;
import ru.itis.easttrade.repositories.ProductsRepository;
import ru.itis.easttrade.repositories.TasksRepository;

@Component
@RequiredArgsConstructor
public class RightsResolver {
    private final RoleChecker roleChecker;
    private final TasksRepository tasksRepository;
    private final ArticlesRepository articlesRepository;
    private final ProductsRepository productsRepository;

    public boolean resolveTaskAction(Integer taskId, Authentication authentication) {
        if (authentication == null) {
            return false;
        }
        boolean isAuthor = tasksRepository.findById(taskId).orElseThrow(() -> new NotFoundException("Task with id <" + taskId + "> not found")).getAccount().getEmail().equals(authentication.getName());
        return isAuthor || roleChecker.hasEnoughAuthority(authentication);
    }

    public boolean resolveArticleAction(Integer articleId, Authentication authentication) {
        if (authentication == null) {
            return false;
        }
        boolean isAuthor = articlesRepository.findById(articleId).orElseThrow(() -> new NotFoundException("Article with id <" + articleId + "> not found")).getAccount().getEmail().equals(authentication.getName());
        return isAuthor || roleChecker.hasEnoughAuthority(authentication);
    }

    public boolean resolveProductAction(Integer id, Authentication authentication) {
        if (authentication == null) {
            return false;
        }
        boolean isAuthor = productsRepository.findById(id).orElseThrow(() -> new NotFoundException("Product with id <" + id + "> not found")).getAccount().getEmail().equals(authentication.getName());
        return isAuthor || roleChecker.hasEnoughAuthority(authentication);
    }
}
