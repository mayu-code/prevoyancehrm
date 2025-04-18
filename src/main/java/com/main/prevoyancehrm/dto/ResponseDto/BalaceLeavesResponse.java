package com.main.prevoyancehrm.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalaceLeavesResponse {
    private float balanceLeaves;
    private float leavesTaken;
    private String name;
    private int maxAllowed;
    private String detail;
}
