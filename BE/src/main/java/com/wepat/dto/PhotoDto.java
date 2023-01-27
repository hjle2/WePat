package com.wepat.dto;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhotoDto {

    @ApiParam(value = "업로드한 사용자ID", required = true)
    private String memberId;
    @ApiParam(value = "캘린더ID", required = true)
    private String calendarId;
    @ApiParam(value = "사진ID") //추후 sns에서도 사용 가능
    private String photoId;
    @ApiParam(value = "사진 url", required = true)
    private String url;
    @ApiParam(value = "댓글Dto 리스트")
    private List<CommentDto> commentList;
    @ApiParam(value = "SNS 등록 여부")
    private boolean sns;
    @ApiParam(value = "좋아요 수")
    private int like;
//    @ApiParam(value = "신고자 명단")
//    private List<String> reportIdList;
    @ApiParam(value = "등록일", required = true)
    private String date;
}
