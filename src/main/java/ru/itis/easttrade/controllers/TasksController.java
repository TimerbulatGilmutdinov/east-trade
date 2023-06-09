package ru.itis.easttrade.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itis.easttrade.converters.StringToTopicConverter;
import ru.itis.easttrade.dto.AccountDto;
import ru.itis.easttrade.dto.TaskDto;
import ru.itis.easttrade.dto.NewUpdateTaskDto;
import ru.itis.easttrade.models.Topic;
import ru.itis.easttrade.services.AccountsService;
import ru.itis.easttrade.services.TaskRespondsService;
import ru.itis.easttrade.services.TasksService;
import ru.itis.easttrade.utils.CurrencyHelper;
import ru.itis.easttrade.utils.RightsResolver;
import ru.itis.easttrade.utils.RoleChecker;

import javax.validation.Valid;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TasksController {
    private final TasksService tasksService;
    private final AccountsService accountsService;
    private final TaskRespondsService taskRespondsService;
    private final RoleChecker roleChecker;
    private final RightsResolver rightsResolver;
    private final CurrencyHelper currencyHelper;


    @GetMapping("/tasks/{id}")
    public String getTaskById(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        TaskDto task = tasksService.getTaskById(id);
        model.addAttribute("authentication",authentication);
        model.addAttribute("responds", taskRespondsService.getAllRespondsByTasksId(id));
        model.addAttribute("hasEnoughAuthority", rightsResolver.resolveTaskAction(id, authentication));
        model.addAttribute("task", task);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String secondResult = decimalFormat.format(task.getPrice() / currencyHelper.getCurrency("USD"));
        model.addAttribute("USD", secondResult);
        return "task";
    }

    @PostMapping("/tasks/{id}")
    public String addRespond(@PathVariable("id") Integer taskId, Authentication authentication){
        Integer accountId = accountsService.getAccountByEmail(authentication.getName()).getId();
        taskRespondsService.addTaskRespond(taskId, accountId);
        return "redirect:"+MvcUriComponentsBuilder.fromMappingName("TC#getTaskById").arg(0,taskId).build();
    }

    @GetMapping("/tasks/today")
    public String getArticlesForToday(Model model) {
        model.addAttribute("tasks", tasksService.getAllTasksForToday());
        return "tasks";
    }

    @GetMapping("/create-task")
    public String getCreateTask(Model model) {
        model.addAttribute("task", new NewUpdateTaskDto());
        return "create-task";
    }

    @PostMapping("/create-task")
    public String createTask(@Valid @ModelAttribute("task") NewUpdateTaskDto taskDto,BindingResult bindingResult, Authentication authentication, RedirectAttributes redirectAttributes, Model model) {
        if(bindingResult.hasErrors()){
            StringBuilder stringBuilder = new StringBuilder();
            for(FieldError error:bindingResult.getFieldErrors()){
                stringBuilder.append(error.getDefaultMessage()).append(", ");
            }
            stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
            redirectAttributes.addFlashAttribute("validationErrors", stringBuilder.toString());
            model.addAttribute("task",taskDto);
            return "redirect:"+MvcUriComponentsBuilder.fromMappingName("TC#getCreateTask").build();
        }
        int id = tasksService.saveTask(taskDto, authentication).getId();
        return "redirect:" + MvcUriComponentsBuilder.fromMappingName("TC#getTaskById").arg(0, id).build();
    }

    @GetMapping("/my-tasks")
    public String myTasks(@RequestParam(name = "sort", defaultValue = "new") String sort, @RequestParam(name = "topic", required = false) String topic, Model model, Authentication authentication) {
        AccountDto accountDto = accountsService.getAccountByEmail(authentication.getName());
        List<TaskDto> tasks = tasksService.getTasksByAccount(accountDto);

        if (sort.equals("old")) {
            tasks.sort(Comparator.comparing(TaskDto::getPublishDate));
        } else {
            tasks.sort(Comparator.comparing(TaskDto::getPublishDate).reversed());
        }
        tasks = sortByTopic(sort, topic, model, tasks);

        model.addAttribute("tasks", tasks);
        return "my-tasks";
    }

    private List<TaskDto> sortByTopic(@RequestParam(name = "sort", defaultValue = "new") String sort, @RequestParam(name = "topic", required = false) String topic, Model model, List<TaskDto> tasks) {
        StringToTopicConverter converter = new StringToTopicConverter();
        if (topic == null || topic.equals("ALL")) {
            model.addAttribute("topic", "ALL");
        } else {
            Topic topic1 = (Topic) converter.convert(topic, TypeDescriptor.valueOf(String.class), TypeDescriptor.valueOf(Topic.class));
            tasks = tasksService.getSortedTasksByTopic(tasks, topic1);
            model.addAttribute("topic", topic);
        }
        model.addAttribute("sorted", sort);
        model.addAttribute("tasks", tasks);
        return tasks;
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable("id") Integer id, Authentication authentication) {
        boolean isAuthor = tasksService.getTaskById(id).getAccountEmail().equals(authentication.getName());
        return tasksService.deleteTaskById(id, authentication);
    }

    @GetMapping("/tasks/{id}/update")
    public String getUpdateTask(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        boolean isAuthor = tasksService.getTaskById(id).getAccountEmail().equals(authentication.getName());
        if (isAuthor || roleChecker.hasEnoughAuthority(authentication)) {
            TaskDto task = tasksService.getTaskById(id);
            model.addAttribute("task", task);
            return "update-task";
        } else {
            throw new AccessDeniedException("You don't have rights to do that");
        }
    }

    @PostMapping("/tasks/{id}/update")
    public String updateTask(@PathVariable("id") Integer id, @ModelAttribute NewUpdateTaskDto taskDto, Authentication authentication) {
        tasksService.updateTask(id, taskDto, authentication);
        return "redirect:" + MvcUriComponentsBuilder.fromMappingName("TC#getTaskById").arg(0, id).build();
    }

    @GetMapping(value = "/tasks")
    public String getAllTasks(@RequestParam(name = "sort", defaultValue = "new") String sort, @RequestParam(value = "topic", required = false) String topic, Model model) {
        List<TaskDto> tasks = tasksService.getAllTasksOrderByDateDesc();
        if (sort.equals("old")) {
            tasks.sort(Comparator.comparing(TaskDto::getPublishDate));
        }
        tasks = sortByTopic(sort, topic, model, tasks);
        return "tasks";
    }
}
