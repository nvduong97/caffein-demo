package com.example.demo.service;

import com.example.demo.entity.Author;
import com.example.demo.exception.RecordNotFoundException;
import com.example.demo.locale.Translator;
import com.example.demo.model.AuthorDTO;
import com.example.demo.repository.AuthorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class AuthorService {

    ModelMapper mapper = new ModelMapper();

    @Autowired
    AuthorRepository authorRepository;

    @Cacheable(value = "authors", key = "{#page, #size, #key, #sort}")
    public Page<Author> findAllAuthor(Integer page, Integer size, String sort, String key) {
        Pageable pageable = PageRequest.of(page, size, sort(sort));
        return authorRepository.findAllAuthor(key, pageable);
    }

    @CachePut(value = "author", key = "#result.id")
    @CacheEvict(value = "authors", allEntries = true)
    public Author createAuthor(AuthorDTO authorDTO) {
        return authorRepository.save(mapper.map(authorDTO, Author.class));
    }

    @Cacheable(value = "author", key = "#authorId")
    public Author getAuthor(int authorId) {
        return authorRepository.findById(authorId).orElseThrow(()
                -> new RecordNotFoundException(Translator.toLocale("error.msg.record.not_found")));
    }

    @CachePut(value = "author", key = "#authorId")
    @CacheEvict(value = "authors", allEntries = true)
    public Author updateAuthor(int authorId, AuthorDTO authorDTO) {
        Author author = authorRepository.findById(authorId).orElseThrow(()
                -> new RecordNotFoundException(Translator.toLocale("error.msg.record.not_found")));

        author.setEmail(authorDTO.getEmail());
        author.setAddress(authorDTO.getAddress());
        author.setName(authorDTO.getName());
        return authorRepository.save(author);
    }

    @CacheEvict(value = "author", key = "#authorId")
    public void deleteAuthor(int authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(()
                -> new RecordNotFoundException(Translator.toLocale("error.msg.record.not_found")));
        authorRepository.delete(author);
    }

    private Sort sort(String paramSort) {
        Sort sort = null;
        String[] sortParams = paramSort.split(",");
        for (String s : sortParams) {
            if (s.contains("-")) {
                s = s.replaceFirst("-", "");
                sort = (sort == null) ? Sort.by(s).descending() : sort.and(Sort.by(s).descending());
            }
            else {
                sort = (sort == null) ? Sort.by(s).ascending() : sort.and(Sort.by(s).ascending());
            }
        }
        return sort;
    }

    /*
    search by JpaSpecificationExecutor
     */
    public Page<Author> filter(Integer page, Integer size, String sort, Map<String, String> params) {
        Pageable pageable = PageRequest.of(page, size, sort(sort));
        return authorRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            params.forEach((k, v) -> predicates.add(criteriaBuilder.and(
                    criteriaBuilder.like(root.get(k).as(String.class), "%" + v + "%"))));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        }, pageable);
    }
}
