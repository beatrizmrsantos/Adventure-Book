package com.beatrizsantos.adventurebook.backend.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import com.beatrizsantos.adventurebook.backend.model.Book;
import com.beatrizsantos.adventurebook.backend.model.Option;
import com.beatrizsantos.adventurebook.backend.model.Section;
import com.beatrizsantos.adventurebook.backend.model.SectionType;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Service
public class BookService {
    private static Logger logger = LoggerFactory.getLogger(BookService.class); 

    private final List<Book> books;

    public BookService(ObjectMapper objectMapper, ResourcePatternResolver resourcePatternResolver, Validator validator){
        this.books = loadFiles(objectMapper, resourcePatternResolver, validator);
    }

    private List<Book> loadFiles(ObjectMapper objectMapper, ResourcePatternResolver resourcePatternResolver,  Validator validator){
        Resource[] resources;
        List<Book> booksLoaded = new ArrayList<>();
        List<Book> booksValidated = new ArrayList<>();
        
        //check json files format
        try {
            resources = resourcePatternResolver.getResources("classpath:books/*.json");
            for(Resource resource: resources){
                try(InputStream is = resource.getInputStream()){
                    booksLoaded.add(objectMapper.readValue(is, Book.class));
                } catch (JacksonException e) {
                    logger.warn("Invalid JSON in {}.", resource.getFilename(), e);
                    continue;
                } catch (IOException e) {
                    logger.warn("Could not read {}.", resource.getFilename(), e);
                    continue;
                }
            }
        } catch (IOException e) {
            logger.error("Failed to load book resources");
            throw new IllegalStateException("Could not load books", e);
        }

        //check json files atributes
        for(Book book: booksLoaded){
            Set<ConstraintViolation<Book>> violations = validator.validate(book);
            if(violations.isEmpty()){
                if(validate(book)) {
                    booksValidated.add(book);
                } 
            } else {
                String violationSummary = violations.stream()
                    .map(v -> v.getPropertyPath() + " - " + v.getMessage())
                    .collect(Collectors.joining(", "));

                logger.warn("Failed to load book with id {} because of the violations: {}", book.id(), violationSummary);
            }
        }

        return List.copyOf(booksValidated);
    }

    private boolean validate(Book book){
        List<Section> sections = book.sections();
        Set<Long> sectionIds = sections.stream()
            .map(Section::id)
            .collect(Collectors.toSet());

        int begin = 0;
        int end = 0;
        for(Section section: sections){
            if(section.type() == SectionType.BEGIN){
                begin++;
            }
            if(section.type() == SectionType.END){
                end++;
            }
            if(section.type() == SectionType.BEGIN || section.type() == SectionType.NODE){
                List<Option> options = section.options();
                if(options == null || options.isEmpty()){
                    logger.warn("Failed to load book with id {} because of section Begin or Node doesnt have options", book.id());
                    return false;
                }
                for(Option option: options){
                    if(!sectionIds.contains(option.gotoId())){
                        logger.warn("Failed to load book with id {} because has invalid next section id ({})", book.id(), option.gotoId());
                        return false;
                    }
                }
            }
        }

        if(begin == 0 || begin > 1 || end == 0){
            logger.warn("Failed to load book with id {} because has none or more then one beginning or no ending", book.id());
            return false;
        }

        return true;
    }
    
    public List<Book> getAllBooks(){
        return books;
    }

    
}
