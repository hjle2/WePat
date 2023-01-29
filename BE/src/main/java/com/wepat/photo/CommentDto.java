package com.wepat.photo;


import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CommentDto {
    @ApiParam(value = "댓글 ID")
    private String commentId;
    @ApiParam(value = "작성자", required = true)
    private String writter;
    @ApiParam(value = "글 내용", required = true)
    private String content;
    @ApiParam(value = "글 작성일", required = true)
    private String date;
    @ApiParam(value = "글 작성한 시간 (PK, 글 변경 시 사용)", required = true)
    private String writtenDate;
}
