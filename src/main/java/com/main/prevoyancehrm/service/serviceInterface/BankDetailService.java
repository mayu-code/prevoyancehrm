package com.main.prevoyancehrm.service.serviceInterface;

import java.util.List;

import com.main.prevoyancehrm.dto.ResponseDto.BankDetailsResponse;
import com.main.prevoyancehrm.entities.BankDetails;

public interface BankDetailService {
    BankDetails addBankDetails(BankDetails bankDetails);
    BankDetails getBankDetailsById(long id);
    List<BankDetailsResponse> getBankDetailByUser(String id);
}
