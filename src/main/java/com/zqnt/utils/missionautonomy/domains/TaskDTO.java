package com.zqnt.utils.missionautonomy.domains;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.zqnt.utils.common.proto.*;
import com.zqnt.utils.missionautonomy.domains.config.TaskConfigTemplate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link Task}
 * <p>
 * This DTO uses a polymorphic configuration approach where task-specific
 * parameters are encapsulated in the {@link TaskConfigTemplate} implementation.
 * <p>
 * Usage example:
 * <pre>{@code
 * // Create a waypoint task
 * WaypointTaskConfig config = WaypointTaskConfig.builder()
 *     .flightId("FLIGHT-123")
 *     .waypoints(Arrays.asList(wp1, wp2, wp3))
 *     .globalSpeed(7.5f)
 *     .build();
 *
 * TaskDTO task = TaskDTO.builder()
 *     .name("Inspection Mission")
 *     .taskType(TaskType.WAYPOINT)
 *     .config(config)
 *     .assetId("DRONE-001")
 *     .build();
 * }</pre>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "ZequentTaskDTO")
public class TaskDTO implements Serializable {

    // ============= CORE METADATA =============

    /**
     * Unique task identifier
     */
    private UUID id;

    /**
     * Task creation timestamp
     */
    private LocalDateTime createdAt;

    /**
     * Last modification timestamp
     */
    private LocalDateTime modifiedAt;

    /**
     * User/system who last modified this task
     */
    private String modifiedFrom;

    /**
     * Parent mission identifier
     */
    private UUID missionId;

    /**
     * Task name
     */
    private String name;

    /**
     * Task description
     */
    private String description;

    // ============= TASK TYPE & CONFIGURATION =============

    /**
     * Task type (determines which config implementation is used)
     */
    private TaskType taskType;

    /**
     * Type-specific configuration (polymorphic based on taskType)
     * Can be WaypointTaskConfig, LiveStreamTaskConfig, etc.
     */
    private TaskConfigTemplate config;

    // ============= EXECUTION CONTEXT =============

    /**
     * Current task status
     */
    private TaskStatus status;

    /**
     * Asset/drone identifier assigned to this task
     */
    private String assetId;

    /**
     * Serial number of the asset
     */
    private String snNumber;

    // ============= RUNTIME STATE =============

    /**
     * Current progress percentage (0-100)
     */
    private Integer currentProgress;

    /**
     * Current step/phase description
     */
    private String currentStep;

    /**
     * Reason if task was interrupted/broken
     */
    private FlighttaskBreakReasonEnumProto breakReason;

    // ============= VALIDATION =============

    /**
     * Validates this task and its configuration
     * @throws IllegalArgumentException if task is invalid
     */
    public void validate() {
        if (taskType == null) {
            throw new IllegalArgumentException("Task type must be specified");
        }

        if (config == null) {
            throw new IllegalArgumentException("Task configuration must be provided");
        }

        if (config.getTaskType() != taskType) {
            throw new IllegalArgumentException(
                    String.format("Config type mismatch: expected %s, got %s",
                            taskType, config.getTaskType())
            );
        }

        config.validate();
    }
}
