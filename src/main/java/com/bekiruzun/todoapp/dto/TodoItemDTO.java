package com.bekiruzun.todoapp.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TodoItemDTO {
    private String id;
    private String title;
    private String content;
    private boolean isCompleted;
    private Date createDate;
    private Date completeDate;
}
