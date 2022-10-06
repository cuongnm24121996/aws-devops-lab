package com.springboot.dynamodb.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private String title;
    private String author;
    private String genre;
    private String publisher;
    private int quantity;
}
