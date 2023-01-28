package com.wepat.photo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class PhotoEntity {
    public PhotoEntity(PhotoDto photo) {
        memberId = photo.getMemberId();
        url = photo.getUrl();
        sns = photo.getSns();
        date = photo.getDate();
        commentList = new ArrayList<>();
        reportIdList = new ArrayList<>();
    }
    @ApiParam(value = "업로드한 사용자ID")
    private String memberId;
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
    private LocalDate date;
}
