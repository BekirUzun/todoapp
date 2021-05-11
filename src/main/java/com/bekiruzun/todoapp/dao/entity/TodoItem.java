package com.bekiruzun.todoapp.dao.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.util.Date;

@Data
@Document
public class TodoItem {
    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    private String id;

    private String content;
    private boolean isCompleted;
    private boolean isDeleted;
    private Date createDate;
    private Date completeDate;
}
