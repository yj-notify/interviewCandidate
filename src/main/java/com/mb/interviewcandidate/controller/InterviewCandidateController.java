package com.mb.interviewcandidate.controller;

import com.mb.interviewcandidate.model.InterviewCandidate;
import com.mb.interviewcandidate.service.InterviewCandidateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.util.List;

@RestController
@RequestMapping("/api/candidates")
public class InterviewCandidateController {

    private final InterviewCandidateService service;
//    private final WebClient webClient;


    public InterviewCandidateController(InterviewCandidateService service) {
        this.service = service;
    }

//    public InterviewCandidateController(InterviewCandidateService service, WebClient.Builder webClientBuilder) {
//        this.service = service;
//        this.webClient = webClientBuilder.baseUrl("https://catfact.ninja").build();
//    }

    @PostMapping
    public ResponseEntity<InterviewCandidate> insertCandidate(@RequestBody InterviewCandidate candidate) {
        return ResponseEntity.ok(service.save(candidate));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InterviewCandidate> updateCandidate(@PathVariable Long id, @RequestBody InterviewCandidate candidate) {
        InterviewCandidate updatedCandidate = service.update(id, candidate);
        return ResponseEntity.ok(updatedCandidate);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterviewCandidate> getCandidateById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<InterviewCandidate>> getCandidates(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.findAll(page, size));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/nestedApi")
    public ResponseEntity<String> realNestedApiCall() {
        // External API URL (you can replace this with any public API)
        String apiUrl = "https://catfact.ninja/fact";

        // Create RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Call the external API
        String externalApiResponse = restTemplate.getForObject(apiUrl, String.class);

        return ResponseEntity.ok("Nested API Call: " + externalApiResponse);
    }

//    @GetMapping("/nestedApiReactive")
//    public ResponseEntity<String> realNestedApiCall_reactive() {
//        String response = webClient
//                .get()
//                .uri("/fact")
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
//
//        return ResponseEntity.ok("Nested API Call: " + response);
//    }
}
