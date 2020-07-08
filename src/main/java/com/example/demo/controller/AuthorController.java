package com.example.demo.controller;

import com.example.demo.entity.Author;
import com.example.demo.locale.Translator;
import com.example.demo.model.APIResponse;
import com.example.demo.model.AuthorDTO;
import com.example.demo.model.PagingMetadata;
import com.example.demo.model.Status;
import com.example.demo.service.AuthorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/v1/authors")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    private static final Logger logger = LogManager.getLogger(AuthorController.class);

    ModelMapper mapper = new ModelMapper();

    /*
    search by Jpa querry
    */
    @GetMapping("")
    public ResponseEntity<Object> getAllAuthor(
            @RequestParam(name = "q", defaultValue = "", required = false) String key,
            @RequestParam(defaultValue = "1", required = false) Integer page,
            @RequestParam(defaultValue = "5", required = false) Integer size,
            @RequestParam(defaultValue = "id", required = false) String sort) {

        logger.info("========== Start request get all author==========");

        Page<Author> authorPage = authorService.findAllAuthor(page - 1, size, sort, key);

        PagingMetadata pagingMetadata = new PagingMetadata();
        pagingMetadata.setSize(authorPage.getSize());
        pagingMetadata.setTotalElement(authorPage.getTotalElements());
        pagingMetadata.setTotalPage(authorPage.getTotalPages());
        pagingMetadata.setCurrentPage(authorPage.getNumber() + 1);
        if (authorPage.hasNext())
            pagingMetadata.setNextPageURL("/v1/authors?q=" + key + "&page=" + (page + 1) + "&size=" + size);
        if (authorPage.hasPrevious())
            pagingMetadata.setPreviousPageURL("/v1/authors?q=" + key + "&page=" + (page - 1) + "&size=" + size);

        Status status = Status.builder()
                .code(HttpStatus.OK.value())
                .description(Translator.toLocale("msg.success"))
                .message(Translator.toLocale("description.success"))
                .build();

        APIResponse response = APIResponse.builder()
                .status(status)
                .data(authorPage.getContent())
                .pagingMetadata(pagingMetadata)
                .build();

        logger.info("========== Finish get all author ==========");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<Object> getAuthor(@PathVariable int authorId) {
        logger.info("========== Start request get author by id==========");
        Author author = authorService.getAuthor(authorId);

        Status status = Status.builder()
                .code(HttpStatus.OK.value())
                .description(Translator.toLocale("msg.success"))
                .message(Translator.toLocale("description.success"))
                .build();

        APIResponse response = APIResponse.builder()
                .status(status)
                .data(author)
                .build();
        logger.info("========== Finish get author by id ==========");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{authorId}")
    public ResponseEntity<Object> updateAuthor(@Valid @RequestBody AuthorDTO authorDTO, @PathVariable int authorId) {
        logger.info("========== Start update author ==========");
        Author author = authorService.updateAuthor(authorId, authorDTO);

        Status status = Status.builder()
                .code(HttpStatus.OK.value())
                .description(Translator.toLocale("msg.success"))
                .message(Translator.toLocale("description.success"))
                .build();

        APIResponse response = APIResponse.builder()
                .status(status)
                .data(author)
                .build();

        logger.info("========== Finish update author ==========");
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<Object> createAuthor(@Valid @RequestBody AuthorDTO authorDTO) {
        logger.info("========== Start create author ==========");
        Author author = authorService.createAuthor(authorDTO);

        Status status = Status.builder()
                .code(HttpStatus.OK.value())
                .description(Translator.toLocale("msg.success"))
                .message(Translator.toLocale("description.success"))
                .build();

        APIResponse response = APIResponse.builder()
                .status(status)
                .data(author)
                .build();

        logger.info("========== Finish create author ==========");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{authorId}")
    public ResponseEntity<Object> deleteAuthor(@PathVariable int authorId) {
        logger.info("========== Start request delete author ==========");
        authorService.deleteAuthor(authorId);

        Status status = Status.builder()
                .code(HttpStatus.OK.value())
                .description(Translator.toLocale("msg.success"))
                .message(Translator.toLocale("description.success"))
                .build();

        logger.info("========== Finish delete author ==========");
        return ResponseEntity.ok(status);
    }

    /*
    search by JpaSpecificationExecutor
     */
    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestParam(defaultValue = "0", required = false) Integer page,
                                    @RequestParam(defaultValue = "5", required = false) Integer size,
                                    @RequestParam(defaultValue = "id", required = false) String sort,
                                    @RequestParam Map<String, String> params) {
        params.remove("page");
        params.remove("size");
        params.remove("sort");
        Page<Author> authorPage = authorService.filter(page, size, sort, params);
        return ResponseEntity.ok(authorPage.getContent());
    }
}
