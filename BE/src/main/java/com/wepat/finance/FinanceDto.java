package com.wepat.finance;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
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
    private LocalDate date;
    @ApiParam(value = "가계부 작성 날짜(PK)", required = true)
    private LocalDate writtenDate;
    @ApiParam(value = "가계부 내용")
    private String memo;
}
