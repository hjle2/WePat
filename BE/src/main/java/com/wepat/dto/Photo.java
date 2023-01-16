package com.wepat.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class Photo {
    private String url;
    private List<Comment> commentList;
    private boolean isOnSNS;
    private int like;
    private List<String> reportIdList;
    private Timestamp date;
}
