package com.wepat.photo;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhotoDto {
    public PhotoDto(PhotoEntity photo) {
        this.memberId = photo.getMemberId();
        this.photoId = photo.getPhotoId();
        this.photoUrl = photo.getPhotoUrl();
        this.commentList = photo.getCommentList();
        this.sns = photo.isSns();
        this.like = photo.getLike();
        this.date = photo.getDate();
    }
    @ApiParam(value = "업로드한 사용자ID", required = true)
    private String memberId;
    @ApiParam(value = "사진ID") //추후 sns에서도 사용 가능
    private String photoId;
    @ApiParam(value = "사진 url", required = true)
    private String photoUrl;
    @ApiParam(value = "댓글Dto 리스트")
    private List<CommentDto> commentList;
    @ApiParam(value = "SNS 등록 여부")
    private boolean sns;
    @ApiParam(value = "좋아요 수")
    private int like;
    @ApiParam(value = "등록일", required = true)
    private String date;
}
