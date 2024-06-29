package org.sparta.jenview.controller;

import org.sparta.jenview.dto.VideoPlayDTO;
import org.sparta.jenview.entity.VideoPlayEntity;
import org.sparta.jenview.service.VideoPlayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/video-plays")
public class VideoPlayController {

    private final VideoPlayService videoPlayService;

    @Autowired
    public VideoPlayController(VideoPlayService videoPlayService) {
        this.videoPlayService = videoPlayService;
    }

    // 새로운 비디오 플레이 생성
    @PostMapping
    public ResponseEntity<Map<String, Object>> createvidoePlay(@RequestBody VideoPlayDTO videoPlayDTO) {
        Long id = videoPlayService.createvidoePlay(videoPlayDTO);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("user_id", videoPlayDTO.getUserId());
        response.put("videoId", id);
        response.put("last_played_time", videoPlayDTO.getLast_played_time());
        response.put("msg", "비디오플레이를 생성합니다. ");
        return ResponseEntity.ok(response);
    }

    // 모든 비디오플레이 정보를 가져옴
    @GetMapping
    public ResponseEntity<List<VideoPlayDTO>> vidoePlayList() {
        List<VideoPlayDTO> videoPlayDTOList = videoPlayService.getVideoList();
        return ResponseEntity.ok(videoPlayDTOList);
    }

    // 비디오 id 별 비디오플레이 정보를 가져옴
    @GetMapping("/{id}")
    public ResponseEntity<VideoPlayDTO> getVideoById(@PathVariable Long id) {
        VideoPlayDTO videoPlayDTO = videoPlayService.getVideoById(id);
        return ResponseEntity.ok(videoPlayDTO);

    }

    // 비디오 플레이 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deletevideoplayById(@PathVariable Long id) {
        // 삭제하기 전에 엔티티를 조회
        VideoPlayEntity videoPlayEntity = videoPlayService.findById(id);
        if (videoPlayEntity == null) {
            return ResponseEntity.status(404).body(Map.of("msg", "Video play record not found"));
        }

        // DTO로 변환
        VideoPlayDTO videoPlayDTO = new VideoPlayDTO();
        videoPlayDTO.setUserId(videoPlayEntity.getUserEntity().getId());
        videoPlayDTO.setVideoId(videoPlayEntity.getVideoEntity().getId());
        videoPlayDTO.setLast_played_time(videoPlayEntity.getLastPlayedTime());

        // 엔티티 삭제
        videoPlayService.deletevideoplayById(id);

        // 응답 생성
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("VideoPlayID", id);
        response.put("user_id", videoPlayDTO.getUserId());
        response.put("videoId", videoPlayDTO.getVideoId());
        response.put("last_played_time", videoPlayDTO.getLast_played_time());
        response.put("msg", "비디오플레이 기록을 삭제합니다.");
        return ResponseEntity.ok(response);
    }
}




