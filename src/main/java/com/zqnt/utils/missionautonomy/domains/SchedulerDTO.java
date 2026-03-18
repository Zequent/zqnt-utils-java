package com.zqnt.utils.missionautonomy.domains;

import com.zqnt.utils.common.proto.SchedulerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link Scheduler}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SchedulerDTO implements Serializable {
    private UUID id;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String modifiedFrom;
    private String name;
    private UUID missionId;
    private UUID taskId;
    private String cronExpression;
    private Boolean active;
    private SchedulerType type;
    private String clientTimeZone;
}
