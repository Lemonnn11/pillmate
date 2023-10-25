package com.example.drugoo.repository;

import com.example.drugoo.model.Drug;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.annotations.Nullable;
import com.google.protobuf.Api;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Repository
public class DrugRepository {

    public List<Drug> getAllDrugs() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        List<Drug> drugs = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = db.collection("drugs").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for(QueryDocumentSnapshot doc: documents){
            drugs.add(doc.toObject(Drug.class));
        }
        return drugs;
    }

    public String createDrug(Drug drug) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<DocumentReference> addedDocRef = db.collection("drugs").add(drug);
        DocumentReference docRef = db.collection("drugs").document(addedDocRef.get().getId());
        ApiFuture<WriteResult> future = docRef.update("id", addedDocRef.get().getId());
        return addedDocRef.get().getId();
    }

    public String updateDrug(Drug drug) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection("drugs").document(drug.getId());

        Map<String, Object> updateData = new HashMap<>();
        updateData.put("id", drug.getId());
        updateData.put("name", drug.getName());
        updateData.put("action", drug.getAction());
        updateData.put("formula", drug.getFormula());
        updateData.put("metabolism", drug.getMetabolism());

        ApiFuture<WriteResult> future = docRef.update(updateData);
        return future.get().getUpdateTime().toString();
    }

    public void deleteDrug(String id){
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = db.collection("drugs").document(id).delete();
    }

}
