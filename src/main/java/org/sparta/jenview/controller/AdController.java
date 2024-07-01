package org.sparta.jenview.controller;

import org.sparta.jenview.dto.AdDTO;
import org.sparta.jenview.dto.AdResponseDTO;
import org.sparta.jenview.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<AdResponseDTO> incrementAdViewCount(@PathVariable Long adId) {
        AdResponseDTO adResponse = adService.incrementAdViewCount(adId);
        return ResponseEntity.status(HttpStatus.OK).body(adResponse);
    }

    @PostMapping("/increment-view-by-video/{videoId}")
    public ResponseEntity<List<AdResponseDTO>> incrementAdViewCountsForVideo(
            @PathVariable Long videoId,
            @RequestParam(name = "watchedDuration", required = true) Integer watchedDuration) {
        List<AdResponseDTO> adResponses = adService.incrementAdViewCountsForVideo(videoId, watchedDuration);
        return ResponseEntity.status(HttpStatus.OK).body(adResponses);
    }
}