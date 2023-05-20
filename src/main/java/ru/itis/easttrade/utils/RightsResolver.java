package ru.itis.easttrade.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.itis.easttrade.repositories.ArticlesRepository;
import ru.itis.easttrade.repositories.TasksRepository;

@Component
@RequiredArgsConstructor
public class RightsResolver {
    private final RoleChecker roleChecker;
    private final TasksRepository tasksRepository;
    private final ArticlesRepository articlesRepository;
    public boolean resolveTaskAction(Integer taskId, Authentication authentication){
        if(authentication ==null){
            return false;
        }
        boolean isAuthor = tasksRepository.findById(taskId).get().getAccount().getEmail().equals(authentication.getName());
        return isAuthor||roleChecker.hasEnoughAuthority(authentication);
    }

    public boolean resolveArticleAction(Integer articleId, Authentication authentication){
        if(authentication == null){
            return false;
        }
        boolean isAuthor = articlesRepository.findById(articleId).get().getAccount().getEmail().equals(authentication.getName());
        return isAuthor||roleChecker.hasEnoughAuthority(authentication);
    }
}
