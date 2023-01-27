package com.wepat.photo;

import com.wepat.photo.CommentDto;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class PhotoDto {

    @ApiParam(value = "업로드한 사용자ID")
    private String memberId;
    @ApiParam(value = "사진 url", required = true)
    private String url;
    @ApiParam(value = "댓글Dto 리스트")
    private List<CommentDto> commentList;
    @ApiParam(value = "SNS 등록 여부")
    private boolean sns;
    @ApiParam(value = "좋아요 수")
    private int like;
    @ApiParam(value = "신고자 명단")
    private List<String> reportIdList;
    @ApiParam(value = "등록일")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    public boolean getSns() {
        return sns;
    }
}
