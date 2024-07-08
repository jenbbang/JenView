package org.sparta.jenview.plays.controller;

import org.sparta.jenview.plays.dto.AdPlayDTO;
import org.sparta.jenview.plays.service.AdPlayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/ad-plays")
public class AdPlayController {

    private final AdPlayService adPlayService;

    @Autowired
    public AdPlayController(AdPlayService adPlayService) {
        this.adPlayService = adPlayService;
    }

    @GetMapping
    public ResponseEntity<List<AdPlayDTO>> getAdPlayList() {
        List<AdPlayDTO> adPlayDTOList = adPlayService.getAdPlayList();
        return ResponseEntity.ok(adPlayDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdPlayDTO> getAdPlayById(@PathVariable Long id) {
        AdPlayDTO adPlayDTO = adPlayService.getAdPlayById(id);
        return ResponseEntity.ok(adPlayDTO);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createAdPlay(@RequestBody AdPlayDTO adPlayDTO) {
        adPlayService.createAdPlay(adPlayDTO);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Ad play created successfully");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateAdPlay(@PathVariable Long id, @RequestBody AdPlayDTO adPlayDTO) {
        adPlayService.updateAdPlay(id, adPlayDTO);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Ad play updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteAdPlay(@PathVariable Long id) {
        adPlayService.deleteAdPlay(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Ad play deleted successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/ad/{adId}")
    public ResponseEntity<Map<String, String>> deleteAdPlaysByAdId(@PathVariable Long adId) {
        adPlayService.deleteAdPlaysByAdId(adId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "All ad plays for ad ID " + adId + " deleted successfully");
        return ResponseEntity.ok(response);
    }
}
