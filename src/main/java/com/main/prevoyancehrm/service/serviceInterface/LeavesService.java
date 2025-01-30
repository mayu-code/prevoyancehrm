package com.main.prevoyancehrm.service.serviceInterface;

import java.util.List;

import com.main.prevoyancehrm.entities.Leaves;

public interface LeavesService {
    Leaves addLeaves(Leaves leaves);
    List<Leaves> getAllLeaves();
}
