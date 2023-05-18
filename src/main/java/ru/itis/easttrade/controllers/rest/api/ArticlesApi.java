package ru.itis.easttrade.controllers.rest.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.easttrade.dto.ArticleDto;
import ru.itis.easttrade.dto.ExceptionDto;
import ru.itis.easttrade.dto.UpdateArticleDto;

import java.security.Principal;

@Tags(value = {
        @Tag(name = "Articles")
})
public interface ArticlesApi {
    @Operation(summary = "Get article's info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article's info",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ArticleDto.class))
                    }
            ),
            @ApiResponse(responseCode = "404", description = "Exception info",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class))
                    }
            )
    })
    @GetMapping("/api/articles/{id}")
    ResponseEntity<ArticleDto> getArticleById(@Parameter(description = "Article's id", example = "123")
                                                @PathVariable("id") Integer id);

    @Operation(summary = "Creating article")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Article created",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ArticleDto.class))
                    }
            ),
            @ApiResponse(responseCode = "404", description = "Exception info",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class))
                    }
            )
    })
    @GetMapping("/api/create-article")
    ResponseEntity<ArticleDto> createArticle(@RequestBody ArticleDto articleDto, Principal principal);


    @Operation(summary = "Updating article")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Article updated",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ArticleDto.class))
                    }
            ),
            @ApiResponse(responseCode = "404", description = "Error info",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class))
                    }
            )
    })
    @PostMapping("/api/articles/{id}/update")
    ResponseEntity<ArticleDto> updateArticleById(
            @Parameter(description = "Article's id", example = "123") @PathVariable("id") Integer id,
            @RequestBody UpdateArticleDto updatedArticle);

    @Operation(summary = "Removing article")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Article removed"),
            @ApiResponse(responseCode = "404", description = "Error info",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class))
                    }
            )
    })
    @PostMapping("/api/articles/{id}/delete")
    ResponseEntity<?> deleteArticleById(
            @Parameter(description = "Article's id", example = "123") @PathVariable("id") Integer id);
}
