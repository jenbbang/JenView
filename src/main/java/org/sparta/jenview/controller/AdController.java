package org.sparta.jenview.controller;

import org.sparta.jenview.dto.AdDTO;
import org.sparta.jenview.dto.AdResponseDTO;
import org.sparta.jenview.dto.AdViewRequestDTO;
import org.sparta.jenview.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
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

    // 광고 생성
    @PostMapping
    public ResponseEntity<List<AdResponseDTO>> createAds(@RequestBody AdDTO adDTO) {
        List<AdResponseDTO> savedAds = adService.createAdsForVideo(adDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAds);
    }

    // 광고 조회수 증가 (단일 광고)
    @PostMapping("/increment-view/{adId}")
    public ResponseEntity<AdResponseDTO> incrementAdViewCount(@PathVariable Long adId) {
        AdResponseDTO adResponse = adService.incrementAdViewCount(adId);
        return ResponseEntity.status(HttpStatus.OK).body(adResponse);
    }

    // 광고 조회수 증가 (비디오 기준)
    @PostMapping("/increment-view-by-video")
    public ResponseEntity<List<AdResponseDTO>> incrementAdViewCountsForVideo(@RequestBody AdViewRequestDTO adViewRequestDTO) {
        List<AdResponseDTO> adResponses = adService.incrementAdViewCountsForVideo(adViewRequestDTO.getVideoId(), adViewRequestDTO.getWatchedDuration());
        return ResponseEntity.status(HttpStatus.OK).body(adResponses);
    }

    // 특정 Video_ID의 광고를 삭제
    @DeleteMapping("/{videoId}")
    public ResponseEntity<Map<String, Object>> deleteAdsByVideoId(@PathVariable Long videoId) {
        adService.deleteAdsByVideoId(videoId);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("videoId", videoId);
        response.put("msg", "광고가 삭제되었습니다.");
        return ResponseEntity.ok(response);
    }
}