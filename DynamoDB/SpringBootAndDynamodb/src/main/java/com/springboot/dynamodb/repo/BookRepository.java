package com.springboot.dynamodb.repo;

import com.springboot.dynamodb.entity.Book;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.EnableScanCount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableScan
//spring annotation
@Repository
public interface BookRepository extends CrudRepository<Book, String> {

    @EnableScanCount
    long countByGenre(String genre);

    List<Book> findAllByGenre(String genre);
}
