package com.example.drugoo.service;

import com.example.drugoo.model.Drug;
import com.example.drugoo.repository.DrugElasticRepository;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Service
public class FirebaseListenerServiceimpl implements FirebaseListenerService{

    private final DrugElasticRepository repository;

    @Autowired
    public FirebaseListenerServiceimpl(DrugElasticRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    @Override
    public void initializeFirestoreListener() {
        Firestore db = FirestoreClient.getFirestore();
        db.collection("drugs")
                .addSnapshotListener(
                        new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(
                                    @Nullable QuerySnapshot snapshots, @Nullable FirestoreException e) {
                                if (e != null) {
                                    System.err.println("Listen failed: " + e);
                                    return;
                                }

                                for (DocumentChange dc : snapshots.getDocumentChanges()) {
                                    switch (dc.getType()) {
                                        case ADDED:
                                            System.out.println("New drug: " + dc.getDocument().getData());
                                            Drug drug1 = new Drug();
                                            drug1.setId(dc.getDocument().getString("id"));
                                            drug1.setName(dc.getDocument().getString("name"));
                                            drug1.setAction(dc.getDocument().getString("action"));
                                            drug1.setFormula(dc.getDocument().getString("formula"));
                                            drug1.setMetabolism(dc.getDocument().getString("metabolism"));
                                            repository.save(drug1);
                                            break;
                                        case MODIFIED:
                                            System.out.println("Modified drug: " + dc.getDocument().getData());
                                            Drug drug2 = new Drug();
                                            drug2.setId(dc.getDocument().getString("id"));
                                            drug2.setName(dc.getDocument().getString("name"));
                                            drug2.setAction(dc.getDocument().getString("action"));
                                            drug2.setFormula(dc.getDocument().getString("formula"));
                                            drug2.setMetabolism(dc.getDocument().getString("metabolism"));
                                            repository.save(drug2);
                                            break;
                                        case REMOVED:
                                            System.out.println("Removed drug: " + dc.getDocument().getData());
                                            repository.deleteById(Objects.requireNonNull(dc.getDocument().getString("id")));
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                        });
    }
}
