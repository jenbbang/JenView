package org.sparta.jenview.ad.controller;

import org.sparta.jenview.ad.dto.AdResponseDTO;
import org.sparta.jenview.ad.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public ResponseEntity<List<AdResponseDTO>> createAds(@RequestBody(required = false) Integer numberOfAds) {
        List<AdResponseDTO> createdAds = adService.createRandomAds(numberOfAds);
        return ResponseEntity.ok(createdAds);
    }

//    @GetMapping("/random/{videoId}")
//    public ResponseEntity<AdEntity> getRandomAd(@PathVariable("videoId") Long videoId) {
//        AdEntity randomAd = adService.getRandomAdForVideo(videoId);
//        return ResponseEntity.ok(randomAd);
}
