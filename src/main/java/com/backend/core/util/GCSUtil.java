package com.backend.core.util;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Component
public class GCSUtil {

    private final Storage storage;
    private final String bucketName = "zezeone_images"; // 실제 버킷명

    public GCSUtil() throws Exception {
        FileInputStream serviceAccountStream = new FileInputStream("/app/service-account.json");
        this.storage = StorageOptions.newBuilder()
                .setCredentials(ServiceAccountCredentials.fromStream(serviceAccountStream))
                .build()
                .getService();
    }

    // signed url 생성
    public String getSignedUrl(String objectName) {
        BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(bucketName, objectName)).build();
        URL url = storage.signUrl(blobInfo, 10, TimeUnit.MINUTES); // 10분 유효
        return url.toString();
    }
}
