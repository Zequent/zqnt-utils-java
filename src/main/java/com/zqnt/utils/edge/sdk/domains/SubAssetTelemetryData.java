package com.zqnt.utils.edge.sdk.domains;

import com.zqnt.utils.common.proto.SubAssetMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * POJO representation of SubAsset (Drone) Telemetry data.
 * Maps to SubAssetTelemetry proto message.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubAssetTelemetryData {

	private String id;
	private LocalDateTime timestamp;
	private Float latitude;
	private Float longitude;
	private Float absoluteAltitude;
	private Float relativeAltitude;
	private Float horizontalSpeed;
	private Float verticalSpeed;
	private Float windSpeed;
	private String windDirection;
	private Float heading;
	private Integer gear;
	private PayloadTelemetry payloadTelemetry;
	private BatteryInformation batteryInformation;
	private Integer heightLimit;
	private Float homeDistance;
	private Double totalMovementDistance;
	private Double totalMovementTime;
	private SubAssetMode mode;
	private String country;

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class BatteryInformation {
		private String percentage;
		private Integer remainingTime;
		private String returnToHomePower;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PayloadTelemetry {
		private String id;
		private LocalDateTime timestamp;
		private CameraData cameraData;
		private RangeFinderData rangeFinderData;
		private SensorData sensorData;
		private String name;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class CameraData {
		private String currentLens;
		private Float gimbalPitch;
		private Float gimbalYaw;
		private Float gimbalRoll;
		private Float zoomFactor;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class RangeFinderData {
		private Float targetLatitude;
		private Float targetLongitude;
		private Float targetDistance;
		private Float targetAltitude;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class SensorData {
		private Float targetTemperature;
	}

}
