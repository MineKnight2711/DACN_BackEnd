package com.example.dacn.utils.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Configuration
public class FirebaseConfig {
    String firekey="src/main/java/com/example/dacn/utils/firebase/firebasekey.json";
    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        FileInputStream serviceAccount = new FileInputStream(firekey);
        FirebaseApp firebaseApp = null;
        List<FirebaseApp> firebaseApps = FirebaseApp.getApps();
        for(FirebaseApp app : firebaseApps){
            if(app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)){
                firebaseApp = app;
            }
        }
        if (firebaseApp == null) {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setProjectId("fooddeliveryv2-c6f80")
                    .build();
            firebaseApp = FirebaseApp.initializeApp(options, FirebaseApp.DEFAULT_APP_NAME);
            serviceAccount.close();
        }
        return firebaseApp;
    }
    @Bean
    public Storage firebaseStorage() throws IOException {
        FileInputStream serviceAccount = new FileInputStream(firekey);
        if (FirebaseApp.getApps().isEmpty()) {
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .setStorageBucket("fooddeliveryv2-c6f80.appspot.com")
                    .build();
            FirebaseApp.initializeApp(options);
        }
        FileInputStream storageAccount = new FileInputStream(firekey);
        return StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(storageAccount))
                .build()
                .getService();
    }
    @Bean
    public FirebaseAuth firebaseAuth() throws IOException {
        return FirebaseAuth.getInstance(firebaseApp());
    }
}
