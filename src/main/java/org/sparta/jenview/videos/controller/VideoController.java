package org.sparta.jenview.videos.controller;

import org.sparta.jenview.videos.dto.VideoDTO;
import org.sparta.jenview.plays.dto.VideoPlayDTO;
import org.sparta.jenview.videos.dto.VideoRequestDTO;
import org.sparta.jenview.videos.dto.VideoResponseDTO;
import org.sparta.jenview.videos.entity.VideoEntity;
import org.sparta.jenview.videos.mapper.VideoMapper;
import org.sparta.jenview.videos.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/videos")
public class VideoController {

    private final VideoService videoService;
    private final VideoMapper videoMapper;

    // 생성자 주입을 통해 VideoService를 주입받습니다.
    @Autowired
    public VideoController(VideoService videoService, VideoMapper videoMapper) {
        this.videoService = videoService;
        this.videoMapper = videoMapper;
    }

    // 새로운 비디오를 생성하고 생성된 비디오의 ID와 메시지를 반환
    @PostMapping
    public ResponseEntity<Map<String, Object>> createVideo(@RequestBody VideoDTO videoDTO) {
        Long videoId = videoService.createVideo(videoDTO);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("시청자 id", videoDTO.getUserId());
        response.put("동영상 관리 번호", videoId);
        response.put("동영상 제목", videoDTO.getTitle());
        response.put("동영상 내용", videoDTO.getDescription());
        response.put("현재 조회수", videoDTO.getViewCount());
        response.put("현재 재생 시간", videoDTO.getPlayTime());
        response.put("msg", "비디오가 생성되었습니다. ");

        return ResponseEntity.ok(response);

    }

    // 모든 비디오 정보를 가져옴
    @GetMapping
    public ResponseEntity<List<VideoDTO>> getVideoList() {
        List<VideoDTO> videoDTOList = videoService.getVideoList();
        return ResponseEntity.ok(videoDTOList);
    }

    // 특정 ID의 비디오 정보를 가져옴
    @GetMapping("/{id}")
    public ResponseEntity<?> getVideoById(@PathVariable("id") Long id) {
        try {
            VideoDTO videoDTO = videoService.getVideoById(id);
            return ResponseEntity.ok(videoDTO);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    // 특정 ID의 비디오 정보를 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateVideo(@PathVariable("id") Long id, @RequestBody VideoRequestDTO videoRequestDTO) {
        videoService.updateVideo(id, videoRequestDTO);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("videoId", id);
        response.put("title", videoRequestDTO.getTitle());
        response.put("description", videoRequestDTO.getDescription());
        response.put("viewCount", videoRequestDTO.getViewCount());
        response.put("playTime", videoRequestDTO.getPlayTime());
        response.put("msg", "비디오가 업데이트되었습니다.");
        return ResponseEntity.ok(response);
    }

    // 특정 User_ID의 비디오를 삭제
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> deleteVideosByUserId(@PathVariable("userId") Long userId) {
        videoService.deleteVideosByUserId(userId);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("userId", userId);
        response.put("msg", "비디오가 전체 삭제 완료 되었습니다.");
        return ResponseEntity.ok(response);
    }

    //video_id로 특정 비디오를 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteVideosByVideoId(@PathVariable("id") Long id) {
        videoService.deleteVideosByVideoId(id);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("VideoId", id);
        response.put("msg", "삭제완료");
        return ResponseEntity.ok(response);
    }

    // 비디오 시청 엔드포인트 추가
    @PostMapping("/watch/{id}")
    public ResponseEntity<Map<String, Object>> watchVideo(@PathVariable("id") Long id, @RequestBody VideoPlayDTO videoPlayDTO) {
        // 요청 본문에 있는 videoId를 경로 변수 id로 설정
        videoPlayDTO.setVideoId(id);

        // 비디오 시청 로직 실행
        VideoPlayDTO result = videoService.playVideo(id, videoPlayDTO.getUserId(), videoPlayDTO.getStopTime());

        // 비디오 엔티티를 가져와서 응답 DTO로 변환
        VideoEntity videoEntity = videoService.getVideoEntityById(id);
        Long stopTime = (result != null) ? result.getStopTime() : 0L;
        VideoResponseDTO response = videoMapper.toResponseDTO(videoEntity, stopTime, videoPlayDTO.getUserId());

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("video", response);

        return ResponseEntity.ok(responseBody);
    }

    // 비디오 중지
    @PostMapping("/stop/{id}")
    public ResponseEntity<Map<String, Object>> stopVideo(@PathVariable("id") Long id, @RequestBody VideoPlayDTO videoStopDTO) {
        videoStopDTO.setVideoId(id); // videoId를 DTO에 설정합니다.
        String message = videoService.stopVideo(videoStopDTO);

        VideoEntity videoEntity = videoService.getVideoEntityById(id); // 비디오 엔티티를 가져옵니다.
        Long stopTime = (videoStopDTO.getStopTime() != null) ? videoStopDTO.getStopTime() : 0L; // stopTime 설정
        VideoResponseDTO response = videoMapper.toResponseDTO(videoEntity, stopTime, videoStopDTO.getUserId());

        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("message", message);
        responseBody.put("video", response);

        return ResponseEntity.ok(responseBody);
    }
}