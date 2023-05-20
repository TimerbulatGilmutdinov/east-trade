package ru.itis.easttrade.aspects.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @Before("within(ru.itis.easttrade.aspects.ExceptionsHandler)")
    public void logError(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Annotation[] annotations = method.getAnnotations();
        String name = joinPoint.getSignature().getName();
        ExceptionHandler handler = null;
        ResponseStatus status = null;
        for (Annotation annotation : annotations) {
            if (annotation instanceof ExceptionHandler) {
                handler = (ExceptionHandler) annotation;
            }
            if (annotation instanceof ResponseStatus) {
                status = (ResponseStatus) annotation;
            }
        }
        HttpStatus code = status != null ? status.value() : null;
        Class<? extends Throwable>[] throwableList = handler.value();
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("Handling occurred exceptions with ").append(name).append(" with args [");
        for (Object arg : args) {
            errorMessage.append(arg).append(", ");
        }
        int index = errorMessage.lastIndexOf(",");
        errorMessage.deleteCharAt(index);
        errorMessage.append("]. ").append("Exceptions are: ");
        for (Class<? extends Throwable> throwable : throwableList) {
            errorMessage.append(throwable.getName()).append(" ");
        }
        errorMessage.append(", status code : ").append(code);

        log.info(errorMessage.toString());
    }


}