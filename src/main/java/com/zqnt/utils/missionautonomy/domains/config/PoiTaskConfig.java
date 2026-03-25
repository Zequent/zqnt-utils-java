package com.zqnt.utils.missionautonomy.domains.config;

import com.zqnt.utils.missionautonomy.domains.TaskType;
import lombok.*;

/**
 * Configuration template for Point of Interest (POI) orbit actions.
 * Circles around a defined point while keeping camera focused on it.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PoiTaskConfig implements TaskConfigTemplate {

    @Builder.Default
    @NonNull
    private String configType = TaskType.TASK_TYPE_POI.name();

    /**
     * POI latitude (REQUIRED)
     */
    @NonNull
    private Double poiLatitude;

    /**
     * POI longitude (REQUIRED)
     */
    @NonNull
    private Double poiLongitude;

    /**
     * POI altitude/height in meters (REQUIRED)
     */
    @NonNull
    private Float poiAltitude;

    /**
     * Orbit radius in meters
     */
    @Builder.Default
    private Float orbitRadius = 20.0f;

    /**
     * Orbit speed in m/s
     */
    @Builder.Default
    private Float orbitSpeed = 3.0f;

    /**
     * Flight altitude during orbit in meters
     */
    @Builder.Default
    private Float flightAltitude = 30.0f;

    /**
     * Number of complete orbits
     */
    @Builder.Default
    private Integer numberOfOrbits = 1;

    /**
     * Orbit direction (clockwise, counterclockwise)
     */
    @Builder.Default
    private String orbitDirection = "clockwise";

    /**
     * Starting angle in degrees (0 = North, 90 = East)
     */
    @Builder.Default
    private Integer startAngle = 0;

    /**
     * Ending angle in degrees (360 for full orbit)
     */
    @Builder.Default
    private Integer endAngle = 360;

    /**
     * Enable photo/video capture during orbit
     */
    @Builder.Default
    private Boolean captureEnabled = true;

    /**
     * Capture interval in seconds (null for continuous video)
     */
    private Integer captureInterval;

    /**
     * Keep camera locked on POI
     */
    @Builder.Default
    private Boolean lockCameraOnPoi = true;

    @Override
    public TaskType getTaskType() {
        return TaskType.TASK_TYPE_POI;
    }

    @Override
    public void validate() {
        if (poiLatitude == null || poiLatitude < -90 || poiLatitude > 90) {
            throw new IllegalArgumentException("POI latitude must be between -90 and 90 degrees");
        }

        if (poiLongitude == null || poiLongitude < -180 || poiLongitude > 180) {
            throw new IllegalArgumentException("POI longitude must be between -180 and 180 degrees");
        }

        if (poiAltitude == null || poiAltitude < 0) {
            throw new IllegalArgumentException("POI altitude must be positive");
        }

        if (orbitRadius != null && (orbitRadius < 5 || orbitRadius > 500)) {
            throw new IllegalArgumentException("Orbit radius must be between 5 and 500 meters");
        }

        if (orbitSpeed != null && (orbitSpeed <= 0 || orbitSpeed > 15)) {
            throw new IllegalArgumentException("Orbit speed must be between 0 and 15 m/s");
        }

        if (flightAltitude != null && (flightAltitude <= 0 || flightAltitude > 500)) {
            throw new IllegalArgumentException("Flight altitude must be between 0 and 500 meters");
        }

        if (numberOfOrbits != null && (numberOfOrbits < 1 || numberOfOrbits > 10)) {
            throw new IllegalArgumentException("Number of orbits must be between 1 and 10");
        }

        if (startAngle != null && (startAngle < 0 || startAngle >= 360)) {
            throw new IllegalArgumentException("Start angle must be between 0 and 359 degrees");
        }

        if (endAngle != null && (endAngle < 1 || endAngle > 360)) {
            throw new IllegalArgumentException("End angle must be between 1 and 360 degrees");
        }
    }

    @Override
    public TaskConfigTemplate copy() {
        return this.toBuilder().build();
    }
}
