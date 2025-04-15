package com.main.prevoyancehrm.service.serviceInterface;

import java.util.List;

import com.main.prevoyancehrm.entities.BalanceLeaves;

public interface BalanceLeavesService {
    BalanceLeaves addBalanceLeaves(BalanceLeaves balanceLeaves);
    List<BalanceLeaves> getAllBalanceLeaves(String id);
}
