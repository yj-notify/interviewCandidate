package com.mb.interviewcandidate.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InterviewCandidateTest {

    private Validator validator;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Initialize Validator and ObjectMapper
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
//        objectMapper = new ObjectMapper();
    }

    @Test
    void testValidInterviewCandidate() {
        InterviewCandidate candidate = new InterviewCandidate();
        candidate.setId(1L);
        candidate.setName("John Doe");
        candidate.setInterviewDate(LocalDateTime.of(2025, 1, 23, 15, 0)); // 2025-01-23T15:00:00

        // Validate no violations
        Set<ConstraintViolation<InterviewCandidate>> violations = validator.validate(candidate);
        assertTrue(violations.isEmpty(), "There should be no validation errors");

        // Check field values
        assertEquals(1L, candidate.getId());
        assertEquals("John Doe", candidate.getName());
        assertEquals(LocalDateTime.of(2025, 1, 23, 15, 0), candidate.getInterviewDate());
    }

    @Test
    void testInvalidName() {
        InterviewCandidate candidate = new InterviewCandidate();
        candidate.setId(2L);
        candidate.setName(""); // Invalid blank name
        candidate.setInterviewDate(LocalDateTime.of(2025, 1, 23, 15, 0));

        // Validate violations
        Set<ConstraintViolation<InterviewCandidate>> violations = validator.validate(candidate);
        assertFalse(violations.isEmpty(), "Validation should fail for blank name");

        // Check error message
        String errorMessage = violations.iterator().next().getMessage();
        assertEquals("Name is required", errorMessage);
    }


    @Test
    void testSerialization() throws Exception {
        // Create candidate
        InterviewCandidate candidate = new InterviewCandidate();
        candidate.setId(3L);
        candidate.setName("Jane Doe");
        candidate.setInterviewDate(LocalDateTime.of(2025, 1, 23, 15, 0));

        // Serialize to JSON
        String json = objectMapper.writeValueAsString(candidate);

        // Expected JSON string
        String expectedJson = """
                {
                    "id":3,
                    "name":"Jane Doe",
                    "interviewDate":"2025-01-23T15:00:00"
                }
                """.replaceAll("\\s+", "");
        expectedJson = "{\"id\":3,\"name\":\"Jane Doe\",\"interviewDate\":\"2025-01-23T15:00:00\"}";
        expectedJson = """
                {"id":3,"name":"Jane Doe","interviewDate":"2025-01-23T15:00:00"}""";



        assertEquals(expectedJson, json, "Serialized JSON does not match expected format");
    }

    @Test
    void testDeserialization() throws Exception {
        // JSON string
        String json = """
                {
                    "id":4,
                    "name":"Alex Smith",
                    "interviewDate":"2025-01-23T15:00:00"
                }
                """;

        // Deserialize to object
        InterviewCandidate candidate = objectMapper.readValue(json, InterviewCandidate.class);

        // Validate deserialization
        assertEquals(4L, candidate.getId());
        assertEquals("Alex Smith", candidate.getName());
        assertEquals(LocalDateTime.of(2025, 1, 23, 15, 0), candidate.getInterviewDate());
    }

    @Test
    void testInvalidInterviewDateSerialization() {
        InterviewCandidate candidate = new InterviewCandidate();
        candidate.setId(5L);
        candidate.setName("Chris Doe");
        candidate.setInterviewDate(null); // Invalid null date

        // Validate violations
        Set<ConstraintViolation<InterviewCandidate>> violations = validator.validate(candidate);
        assertFalse(violations.isEmpty(), "Validation should fail for null interview date");
    }
}
