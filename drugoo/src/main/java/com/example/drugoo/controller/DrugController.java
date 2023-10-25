package com.example.drugoo.controller;

import com.example.drugoo.model.Drug;
import com.example.drugoo.search.SearchRequestDTO;
import com.example.drugoo.service.DrugServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/drugs")
public class DrugController {


    private final DrugServiceimpl drugService;

    @Autowired
    public DrugController(DrugServiceimpl drugService) {
        this.drugService = drugService;
    }

    @CrossOrigin
    @GetMapping("/")
    public ResponseEntity<List<Drug>> getAllDrugs() throws ExecutionException, InterruptedException {
        return new ResponseEntity<>(drugService.getDrugs(), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addDrug(@RequestBody Drug drug) throws ExecutionException, InterruptedException {
        boolean res =  drugService.addDrug(drug);
        if(res){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @CrossOrigin
    @PutMapping("/update")
    public ResponseEntity<HttpStatus> updateDrug(@RequestBody Drug drug) throws ExecutionException, InterruptedException {
        boolean res =  drugService.updateDrug(drug);
        if(res){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @CrossOrigin
    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteDrug(@RequestParam("id") String id) throws ExecutionException, InterruptedException {
        boolean res =  drugService.deleteDrug(id);
        if(res){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @CrossOrigin
    @GetMapping("/searchById")
    public ResponseEntity<Drug> searchById(@RequestParam("id") String id){
        return new ResponseEntity<>(drugService.findById(id), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/search")
    public ResponseEntity<List<Drug>> searchByAll(@RequestParam("query") String searchTerm){
        SearchRequestDTO searchRequestDTO = new SearchRequestDTO();
        List<String> fields = Arrays.asList("id", "name", "action", "formula", "metabolism");
        searchRequestDTO.setSearchTerm(searchTerm);
        searchRequestDTO.setFields(fields);
        return new ResponseEntity<>(drugService.search(searchRequestDTO), HttpStatus.OK);
    }

}
