package com.wepat.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class PhotoDto {
    private String userId;
    private String url;
    private List<CommentDto> commentList;
    private boolean isOnSNS;
    private int like;
    private List<String> reportIdList;
    private Timestamp date;
}
