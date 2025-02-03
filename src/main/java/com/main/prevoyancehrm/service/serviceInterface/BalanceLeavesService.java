package com.main.prevoyancehrm.service.serviceInterface;

import com.main.prevoyancehrm.entities.BalanceLeaves;

public interface BalanceLeavesService {
    BalanceLeaves addBalanceLeaves(BalanceLeaves balanceLeaves);
    BalanceLeaves getAllBalanceLeaves(long id);
}
