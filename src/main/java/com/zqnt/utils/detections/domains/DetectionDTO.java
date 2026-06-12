package com.zqnt.utils.detections.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetectionDTO implements Serializable {

    private UUID id;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String modifiedFrom;

    private String assetSn;
    private String subAssetSn;
    private String taskId;

    private String objectId;
    private String objectType;
    private Float confidence;

    private Float boundingBoxX;
    private Float boundingBoxY;
    private Float boundingBoxWidth;
    private Float boundingBoxHeight;

    private String streamUrl;
    private LocalDateTime detectedAt;
}
