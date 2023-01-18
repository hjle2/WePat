package com.wepat.entity;

import com.wepat.dto.CommentDto;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class PhotoEntity {
    private String docId;
    private String memberId;
    private String url;
    private List<CommentDto> commentList;
    private boolean isOnSNS;
    private int like;
    private List<String> reportIdList;
    private Timestamp date;
}
