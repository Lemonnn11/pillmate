package com.example.drugoo.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = "drug")
@Setting(settingPath = "static/es-settings.json")
public class Drug {

    @Id
    @Field(type = FieldType.Keyword)
    private String id;
    @Field(type = FieldType.Text)
    private String name;
    @Field(type = FieldType.Text)
    private String action;
    @Field(type = FieldType.Text)
    private String formula;
    @Field(type = FieldType.Text)
    private String metabolism;

    public Drug(){

    }

    public Drug(String id, String name, String action, String formula, String metabolism){
        this.id = id;
        this.name = name;
        this.action = action;
        this.formula = formula;
        this.metabolism = metabolism;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAction() {
        return action;
    }

    public String getFormula() {
        return formula;
    }

    public String getMetabolism() {
        return metabolism;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public void setMetabolism(String metabolism) {
        this.metabolism = metabolism;
    }
}
