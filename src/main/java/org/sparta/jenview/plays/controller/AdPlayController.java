package org.sparta.jenview.plays.controller;

import org.sparta.jenview.ad.dto.AdResponseDTO;
import org.sparta.jenview.ad.entity.AdEntity;
import org.sparta.jenview.plays.dto.AdPlayDTO;
import org.sparta.jenview.plays.mapper.AdPlayMapper;
import org.sparta.jenview.plays.repository.AdPlayRepository;
import org.sparta.jenview.plays.service.AdPlayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ad-plays")
public class AdPlayController {

    private final AdPlayService adPlayService;
    private final AdPlayRepository adPlayRepository;
    private final AdPlayMapper adPlayMapper;

    @Autowired
    public AdPlayController(AdPlayService adPlayService, AdPlayRepository adPlayRepository, AdPlayMapper adPlayMapper) {
        this.adPlayService = adPlayService;
        this.adPlayRepository = adPlayRepository;
        this.adPlayMapper = adPlayMapper;
    }

 @GetMapping
    public ResponseEntity<List<AdPlayDTO>> getAdPlayList() {
        List<AdPlayDTO> adPlayDTOList = adPlayService.getAdPlayList();
        return ResponseEntity.ok(adPlayDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdPlayDTO> getAdPlayById(@PathVariable("id") Long id) {
        AdPlayDTO adPlayDTO = adPlayService.getAdPlayById(id);
        return ResponseEntity.ok(adPlayDTO);
    }
}