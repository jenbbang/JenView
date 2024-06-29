package org.sparta.jenview.controller;

import org.sparta.jenview.dto.VideoDTO;
import org.sparta.jenview.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/videos")
public class VideoController {

    private final VideoService videoService;

    // 생성자 주입을 통해 VideoService를 주입받습니다.
    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    // 새로운 비디오를 생성하고 생성된 비디오의 ID와 메시지를 반환
    @PostMapping
    public ResponseEntity<Map<String, Object>> createVideo(@RequestBody VideoDTO videoDTO) {
        Long videoId = videoService.createVideo(videoDTO);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("user_id", videoDTO.getUserId());
        response.put("videoId", videoId);
        response.put("title", videoDTO.getTitle());
        response.put("description", videoDTO.getDescription());
        response.put("duration", videoDTO.getDuration());
        response.put("view_count", videoDTO.getViewCount());
        response.put("play_time", videoDTO.getPlayTime());
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
    public ResponseEntity<VideoDTO> getVideo(@PathVariable Long id) {
        VideoDTO videoDTO = videoService.getVideoById(id);
        return ResponseEntity.ok(videoDTO);
    }

    // 특정 ID의 비디오 정보를 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateVideo(@PathVariable Long id, @RequestBody VideoDTO videoDTO) {
        videoService.updateVideo(id, videoDTO);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("videoId", id);
        response.put("title", videoDTO.getTitle());
        response.put("description", videoDTO.getDescription());
        response.put("duration", videoDTO.getDuration());
        response.put("view_count", videoDTO.getViewCount());
        response.put("play_time", videoDTO.getPlayTime());
        response.put("msg", "비디오가 업데이트됩니다. ");
        return ResponseEntity.ok(response);
    }

    // 특정 User_ID의 비디오를 삭제
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> deleteVideosByUserId(@PathVariable Long userId) {
        videoService.deleteVideosByUserId(userId);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("userId", userId);
        response.put("msg", "비디오가 전체 삭제 완료 되었습니다.");
        return ResponseEntity.ok(response);
    }

    //video_id로 특정 비디오를 삭제
    @DeleteMapping("/{VideoId}")
    public ResponseEntity<Map<String, Object>> deleteVideosByVideoId(@PathVariable Long VideoId) {
        videoService.deleteVideosByVideoId(VideoId);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("VideoId", VideoId);
        response.put("msg", "삭제완료");
        return ResponseEntity.ok(response);
    }

}
