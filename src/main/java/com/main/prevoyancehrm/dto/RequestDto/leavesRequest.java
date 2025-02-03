package com.main.prevoyancehrm.dto.RequestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class leavesRequest {
    private long userId;

    private String leaveType;
    private String startDate;
    private String endDate;
    private float totalDays;

}
