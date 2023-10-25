package com.example.drugoo.search;

import java.util.List;

public class SearchRequestDTO {
    private List<String> fields;
    private String searchTerm;

    public List<String> getFields() {
        return fields;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }
    
}
