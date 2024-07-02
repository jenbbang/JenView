package org.sparta.jenview.controller;

import org.sparta.jenview.dto.AdDTO;
import org.sparta.jenview.dto.AdPlayResponseDTO;
import org.sparta.jenview.dto.AdResponseDTO;
import org.sparta.jenview.dto.AdPlayRequestDTO;
import org.sparta.jenview.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ads")
public class AdController {

    private final AdService adService;


    @Autowired
    public AdController(AdService adService) {
        this.adService = adService;
    }

    //광고 생성
    @PostMapping
    public ResponseEntity<List<AdResponseDTO>> createAds(@RequestBody AdDTO adDTO) {
        List<AdResponseDTO> savedAds = adService.createAdsForVideo(adDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAds);
    }

    @PostMapping("/increment-view/{adId}")
    public ResponseEntity<Map<String, String>> incrementView(@PathVariable Long adId, @RequestBody AdPlayRequestDTO adPlayRequestDTO) {
        adService.incrementView(adId, adPlayRequestDTO.getUserId(), adPlayRequestDTO.getPlayTime());
        Map<String, String> response = new HashMap<>();
        response.put("message", "Ad view incremented successfully");
        return ResponseEntity.ok(response);
    }

    // 광고 조회수 증가 (비디오 기준)
    @PostMapping("/increment-view-by-video")
    public ResponseEntity<List<AdResponseDTO>> incrementAdViewCountsForVideo(@RequestBody AdPlayRequestDTO adPlayRequestDTO) {
        List<AdResponseDTO> adResponses = adService.incrementAdViewCountsForVideo(adPlayRequestDTO.getVideoId(), adPlayRequestDTO.getPlayTime());
        return ResponseEntity.status(HttpStatus.OK).body(adResponses);
    }
}

