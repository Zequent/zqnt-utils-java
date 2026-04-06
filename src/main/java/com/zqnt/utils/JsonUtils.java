package com.zqnt.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zqnt.utils.missionautonomy.domains.config.TaskConfigTemplate;
import com.zqnt.utils.missionautonomy.domains.config.WaypointTaskConfig;

import java.io.IOException;

/**
 * Centralized JSON utility with pre-configured ObjectMapper
 * <p>
 * Configuration:
 * - JavaTimeModule for LocalDateTime, Instant, etc.
 * - Ignores unknown properties during deserialization
 * - Excludes null values from serialization
 * - Writes dates as ISO-8601 strings (not timestamps)
 */
public final class JsonUtils {

    private static final ObjectMapper MAPPER = createObjectMapper();

    private JsonUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // Time module for LocalDateTime, Instant, etc.
        mapper.registerModule(new JavaTimeModule());

        // Ignore unknown properties during deserialization
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Don't include null values in JSON
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // Write dates as ISO-8601 strings, not timestamps
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Handle polymorphic deserialization edge cases for TaskConfigTemplate:
        // 1. Missing "configType" discriminator → default to WaypointTaskConfig
        // 2. Config delivered as double-serialized JSON string → re-parse transparently
        mapper.addHandler(new DeserializationProblemHandler() {
            @Override
            public JavaType handleMissingTypeId(DeserializationContext ctxt,
                    JavaType baseType, TypeIdResolver idResolver, String failureMsg) throws IOException {
                if (baseType.isTypeOrSubTypeOf(TaskConfigTemplate.class)) {
                    return ctxt.constructType(WaypointTaskConfig.class);
                }
                return super.handleMissingTypeId(ctxt, baseType, idResolver, failureMsg);
            }

            @Override
            public Object handleMissingInstantiator(DeserializationContext ctxt,
                    Class<?> instClass, com.fasterxml.jackson.databind.deser.ValueInstantiator vi,
                    JsonParser p, String msg) throws IOException {
                if (TaskConfigTemplate.class.isAssignableFrom(instClass)
                        && p.currentToken() == JsonToken.VALUE_STRING) {
                    return MAPPER.readValue(p.getText(), instClass);
                }
                return super.handleMissingInstantiator(ctxt, instClass, vi, p, msg);
            }
        });

        return mapper;
    }

    /**
     * Serialize object to JSON string
     * @param object Object to serialize
     * @return JSON string or null if object is null
     */
    public static String toJson(Object object) {
        if (object == null) {
            return null;
        }
        try {
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to serialize object to JSON", e);
        }
    }

    /**
     * Deserialize JSON string to object
     * @param json JSON string
     * @param clazz Target class
     * @return Deserialized object or null if json is null
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        if (json == null || json.isBlank()) {
            return null;
        }
        try {
            return MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to deserialize JSON to " + clazz.getName(), e);
        }
    }

    /**
     * Get the configured ObjectMapper instance
     * @return Pre-configured ObjectMapper
     */
    public static ObjectMapper getMapper() {
        return MAPPER;
    }
}
