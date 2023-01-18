package com.wepat.dto;

import io.swagger.annotations.ApiParam;

import java.sql.Timestamp;

public class FinanceDto {
    @ApiParam(value = "사용자", required = true)
    private String nickName;
    @ApiParam(value = "돈")
    private int money;
    @ApiParam(value = "사용처")
    private String category;
    @ApiParam(value = "입금 or 출금", required = true)
    private boolean isIncome;
    @ApiParam(value = "입금 or 출금 날짜")
    private Timestamp date;
    @ApiParam(value = "가계부 작성 날짜(PK)", required = true)
    private Timestamp writtenDate;
    @ApiParam(value = "가계부 내용")
    private String memo;
}
