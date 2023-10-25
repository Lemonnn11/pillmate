package com.example.drugoo.util;

import com.example.drugoo.search.SearchRequestDTO;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class SearchUtil {

    public static SearchRequest buildSearchRequest(String indexName, SearchRequestDTO searchRequestDTO){
        try {
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                    .postFilter(getQueryBuilder(searchRequestDTO));

            SearchRequest searchRequest = new SearchRequest(indexName);
            searchRequest.source(sourceBuilder);

            return searchRequest;
        }catch (Exception e){
            e.printStackTrace();;
            return null;
        }
    }

    public static QueryBuilder getQueryBuilder(final SearchRequestDTO dto){
        assert dto != null;
        List<String> fields = dto.getFields();
        if (CollectionUtils.isEmpty(fields)){
            return null;
        }

        if(fields.size() > 1){
            MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(dto.getSearchTerm())
                    .type(MultiMatchQueryBuilder.Type.CROSS_FIELDS)
                    .operator(Operator.AND);

            fields.forEach(queryBuilder::field);

            return queryBuilder;
        }else {
            return fields.stream()
                    .findFirst()
                    .map(field ->
                            QueryBuilders.matchQuery(field, dto.getSearchTerm())
                                    .operator(Operator.AND))
                    .orElse(null);
        }
    }
}
