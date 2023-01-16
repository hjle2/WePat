package com.wepat.dto;


import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
public class Comment {
    private String writter;
    private String content;
    private Timestamp date;
    private LocalDate writtenDate;
}
