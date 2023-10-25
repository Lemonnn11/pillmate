package com.example.drugoo.service;

import com.example.drugoo.model.Drug;
import com.example.drugoo.repository.DrugElasticRepository;
import com.example.drugoo.repository.DrugRepository;
import com.example.drugoo.search.SearchRequestDTO;
import com.example.drugoo.util.SearchUtil;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;



@Service
public class DrugServiceimpl implements DrugService{

    private final DrugRepository drugRepository;


    private final DrugElasticRepository drugElasticRepository;

    @Autowired
    public DrugServiceimpl(DrugRepository drugRepository, DrugElasticRepository repository, DrugElasticRepository drugElasticRepository, RestClient client) {
        this.drugRepository = drugRepository;
        this.drugElasticRepository = drugElasticRepository;
    }

    @Override
    public List<Drug> getDrugs() throws ExecutionException, InterruptedException {
        return drugRepository.getAllDrugs();
    }

    @Override
    public boolean addDrug(Drug drug) throws ExecutionException, InterruptedException {
        String res = drugRepository.createDrug(drug);
        return res != null;
    }

    @Override
    public boolean updateDrug(Drug drug) throws ExecutionException, InterruptedException {
        String res = drugRepository.updateDrug(drug);
        return res != null;
    }

    @Override
    public boolean deleteDrug(String id) throws ExecutionException, InterruptedException {
        List<Drug> tmp = drugRepository.getAllDrugs();
        for(Drug d: tmp){
            if(d.getId().equals(id)){
                drugRepository.deleteDrug(id);
                return true;
            }
        }
        return false;
    }

    @Override
    public Drug findById(String id) {
        return drugElasticRepository.findById(id).orElse(null);
    }

    public List<Drug> search(SearchRequestDTO searchRequestDTO){
        final SearchRequest searchRequest = SearchUtil.buildSearchRequest("drug", searchRequestDTO);

        if(searchRequest == null){
            System.out.println("Failed to build search request");
            return Collections.emptyList();
        }

        try {
            RestHighLevelClient client = new RestHighLevelClient(
                    RestClient.builder(new HttpHost("localhost", 9200, "http"))
            );
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHit[] searchHits = searchResponse.getHits().getHits();
            List<Drug> drugs = new ArrayList<>(searchHits.length);
            for (SearchHit hit: searchHits){
                Map<String, Object> map = hit.getSourceAsMap();
                String id = hit.getId();
                String name = (String) map.get("name");
                String action = (String) map.get("action");
                String formula = (String) map.get("formula");
                String metabolism = (String) map.get("metabolism");
                Drug drug = new Drug(id, name, action, formula, metabolism);
                drugs.add(drug);
            }
            return drugs;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


}
