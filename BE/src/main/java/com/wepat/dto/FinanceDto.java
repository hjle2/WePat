package com.wepat.dto;

import java.sql.Timestamp;

public class FinanceDto {
    // 돈의 사용자
    private String nickName;
    // 돈
    private int money;
    // 사용처 
    private String category;
    // 입금인지 아닌지
    private boolean isIncome;
    // 입금 or 출금 날짜
    private Timestamp date;
    // 가계부 작성 날짜
    private Timestamp writtenDate;
    // 가계부 메모
    private String memo;
}
