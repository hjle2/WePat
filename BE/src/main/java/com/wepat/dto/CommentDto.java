package com.wepat.dto;


import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
public class CommentDto {
    private String writter;
    private String content;
    private Timestamp date;
    private Timestamp writtenDate;
}
