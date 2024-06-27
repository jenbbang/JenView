package org.sparta.jenview.controller;

import org.sparta.jenview.dto.VideoDTO;
import org.sparta.jenview.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

    // 새로운 비디오를 생성
    @PostMapping
    public ResponseEntity<Void> createVideo(@RequestBody VideoDTO videoDTO) {
        videoService.createVideo(videoDTO);
        return ResponseEntity.ok().build();
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
    public ResponseEntity<Void> updateVideo(@PathVariable Long id, @RequestBody VideoDTO videoDTO) {
        videoService.updateVideo(id, videoDTO);
        return ResponseEntity.ok().build();
    }

    // 특정 User_ID의 비디오를 삭제
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Map<String, String>> deleteVideosByUserId(@PathVariable Long userId) {
        videoService.deleteVideosByUserId(userId);
        Map<String, String> response = new HashMap<>();
        response.put("msg", "삭제완료");
        return ResponseEntity.ok(response);
    }

    //video_id로 특정 비디오를 삭제
    @DeleteMapping("/{VideoId}")
    public ResponseEntity<Map<String, String>> deleteVideosByVideoId(@PathVariable Long VideoId) {
        videoService.deleteVideosByVideoId(VideoId);
        Map<String, String> response = new HashMap<>();
        response.put("msg", "삭제완료");
        return ResponseEntity.ok(response);
    }

    //제목에 따라서 삭제
//    @DeleteMapping("/by-title")
//    public ResponseEntity<String> deleteVideoByTitle(@RequestParam String title) {
//        videoService.deleteVideoByTitle(title);
//        return ResponseEntity.ok("{\"msg\": \"삭제완료\"}");
//    }

}
