package com.example.drugoo.service;

import com.example.drugoo.model.Drug;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface DrugService {
    List<Drug> getDrugs() throws ExecutionException, InterruptedException;

    boolean addDrug(Drug drug) throws ExecutionException, InterruptedException;

    boolean updateDrug(Drug drug) throws ExecutionException, InterruptedException;

    boolean deleteDrug(String id) throws ExecutionException, InterruptedException;

    Drug findById(String id);

}
