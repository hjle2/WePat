package com.wepat.finance;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
public class FinanceDto {
    @ApiParam(value = "가계부ID")
    private String financeId;
    @ApiParam(value = "사용자", required = true)
    private String nickName;
    @ApiParam(value = "돈")
    private int money;
    @ApiParam(value = "사용처")
    private String category;
    @ApiParam(value = "입금 or 출금", required = true)
    private boolean income;
    @ApiParam(value = "입금 or 출금 날짜")
    private String date;
    @ApiParam(value = "가계부 내용")
    private String memo;
}
