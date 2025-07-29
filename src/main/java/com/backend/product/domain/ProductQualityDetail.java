package com.backend.product.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "results") // 기존과 동일한 컬렉션
public class ProductQualityDetail {

    @Id
    private String id;

    private String label;

    @Field("max_cluster")
    private int maxCluster;

    private double uniformity;

    @Field("n_spots")
    private int nSpots;

    @Field("min_nn_dist")
    private double minNnDist;

    @Field("nn_cv")
    private double nnCv;

    @Field("n_clusters")
    private int nClusters;

    @Field("uploadDate")
    private LocalDateTime uploadDate;

    @Field("img_url")
    private String imageUrl;

    @Field("img_file_id") // mongo
    private String imgFileId;
}
