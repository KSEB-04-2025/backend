package com.backend.core.util;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Component
public class GCSUtil {

    private final Storage storage;
    private final String bucketName = "zezeone_images"; // 실제 버킷명

    public GCSUtil() {
        // 리소스 누수 방지 및 예외 핸들링 개선
        try (FileInputStream serviceAccountStream = new FileInputStream("/app/service-account.json")) {
            this.storage = StorageOptions.newBuilder()
                    .setCredentials(ServiceAccountCredentials.fromStream(serviceAccountStream))
                    .build()
                    .getService();
        } catch (IOException e) {
            throw new IllegalStateException("GCS 인증 초기화에 실패했습니다: " + e.getMessage(), e);
        }
    }

    // signed url 생성 (입력값, 예외 모두 명확하게 처리)
    public String getSignedUrl(String objectName) {
        if (!StringUtils.hasText(objectName)) {
            throw new IllegalArgumentException("Object name은 null이거나 빈 값일 수 없습니다");
        }
        try {
            BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(bucketName, objectName)).build();
            URL url = storage.signUrl(blobInfo, 10, TimeUnit.MINUTES); // 10분 유효
            return url.toString();
        } catch (Exception e) {
            throw new RuntimeException("Signed URL 생성에 실패했습니다: " + objectName, e);
        }
    }
}
