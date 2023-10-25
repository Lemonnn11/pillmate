package com.example.drugoo.repository;

import com.example.drugoo.model.Drug;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface DrugElasticRepository extends ElasticsearchRepository<Drug, String> {
}
