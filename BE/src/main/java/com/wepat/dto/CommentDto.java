package com.wepat.dto;


import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
public class CommentDto {
    @ApiParam(value = "작성자", required = true)
    private String writter;
    @ApiParam(value = "글 내용", required = true)
    private String content;
    @ApiParam(value = "글 작성일", required = true)
    private Timestamp date;
    @ApiParam(value = "글 작성한 시간 (PK, 글 변경 시 사용)", required = true)
    private Timestamp writtenDate;
}
