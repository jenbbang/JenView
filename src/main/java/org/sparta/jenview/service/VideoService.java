package org.sparta.jenview.service;

import jakarta.transaction.Transactional;
import org.sparta.jenview.dto.VideoDTO;
import org.sparta.jenview.dto.VideoPlayDTO;
import org.sparta.jenview.entity.UserEntity;
import org.sparta.jenview.entity.VideoEntity;
import org.sparta.jenview.entity.VideoPlayEntity;
import org.sparta.jenview.mapper.VideoMapper;
import org.sparta.jenview.mapper.VideoPlayMapper;
import org.sparta.jenview.repository.UserRepository;
import org.sparta.jenview.repository.VideoPlayRepository;
import org.sparta.jenview.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VideoService {

    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final VideoMapper videoMapper;
    private final VideoPlayRepository videoPlayRepository;
    private final VideoPlayMapper videoPlayMapper;

    @Autowired
    public VideoService(VideoRepository videoRepository, UserRepository userRepository, VideoMapper videoMapper, VideoPlayRepository videoPlayRepository, VideoPlayMapper videoPlayMapper) {
        this.videoRepository = videoRepository;
        this.userRepository = userRepository;
        this.videoMapper = videoMapper;
        this.videoPlayRepository = videoPlayRepository;
        this.videoPlayMapper = videoPlayMapper;
    }


    // 모든 비디오 목록을 가져오는 메서드
    public List<VideoDTO> getVideoList() {
        List<VideoEntity> videos = videoRepository.findAll();
        return videos.stream().map(videoMapper::toDTO).toList();
    }

    // 특정 ID의 비디오 정보를 가져오는 메서드
    public VideoDTO getVideoById(Long id) {
        VideoEntity videoEntity = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found with id " + id));
        return videoMapper.toDTO(videoEntity);
    }

    // 새로운 비디오를 생성하는 메서드
    public Long createVideo(VideoDTO videoDTO) {
        UserEntity userEntity = userRepository.findById(videoDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id " + videoDTO.getUserId()));
        VideoEntity videoEntity = videoMapper.toEntity(videoDTO);
        videoEntity.setUserEntity(userEntity);
        videoRepository.save(videoEntity);
        return videoEntity.getId(); // 생성된 비디오의 ID를 반환

    }

    // 특정 ID의 비디오 정보를 업데이트하는 메서드
    public void updateVideo(Long id, VideoDTO videoDTO) {
        VideoEntity videoEntity = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found with id " + id));
        videoEntity.setTitle(videoDTO.getTitle());
        videoEntity.setDescription(videoDTO.getDescription());
        videoEntity.setDuration(videoDTO.getDuration());
        videoEntity.setViewCount(videoDTO.getViewCount());
        videoEntity.setPlayTime(videoDTO.getPlayTime());

        // userId를 통해 userEntity를 조회하고 설정
        UserEntity userEntity = userRepository.findById(videoDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id " + videoDTO.getUserId()));
        videoEntity.setUserEntity(userEntity);

        videoRepository.save(videoEntity);
    }

    @Transactional
    // 특정 User_ID의 비디오를 삭제하는 메서드
    public void deleteVideosByUserId(Long userId) {
        videoRepository.deleteByUserId(userId);
    }

    @Transactional
    // 특정 Video_ID의 비디오를 삭제하는 메서드
    public void deleteVideosByVideoId(Long videoId) {
        videoRepository.deleteByVideoId(videoId);
    }

    //비디오 재생
    @Transactional
    public void incrementViewCount(Long VideoId) {
        VideoEntity videoEntity = videoRepository.findById(VideoId)
                .orElseThrow(() -> new RuntimeException("비디오를 찾을 수 없습니다."));
        videoEntity.setViewCount(videoEntity.getViewCount() + 1);

        videoRepository.save(videoEntity);
    }

    @Transactional
    public void savePlayRecord(VideoPlayDTO videoPlayDTO) {
        UserEntity userEntity = userRepository.findById(videoPlayDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        VideoEntity videoEntity = videoRepository.findById(videoPlayDTO.getVideoId())
                .orElseThrow(() -> new RuntimeException("비디오를 찾을 수 없습니다."));

        VideoPlayEntity videoPlayEntity = videoPlayMapper.toEntity(videoPlayDTO, userEntity, videoEntity);
        videoPlayEntity.setCreatedAt(LocalDateTime.now());
        videoPlayEntity.setUpdatedAt(LocalDateTime.now());
        videoPlayRepository.save(videoPlayEntity);
    }

    public VideoEntity getVideoEntityById(Long id) {
        return videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found with id " + id));
    }

    //비디오 정지
    @Transactional
    public void stopVideo(VideoPlayDTO videoStopDTO) {
        Long videoId = videoStopDTO.getVideoId();
        Long userId = videoStopDTO.getUserId();
        Integer stopTime = videoStopDTO.getStopTime();

        // 비디오 엔티티를 찾습니다.
        VideoEntity videoEntity = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("비디오를 찾을 수 없습니다."));

        // 유저 엔티티를 찾습니다.
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 비디오 엔티티의 플레이 타임을 증가시킵니다.
        int newPlayTime = (int) (videoEntity.getPlayTime() + stopTime);
        videoEntity.setPlayTime(newPlayTime);

        videoRepository.save(videoEntity);

        // 비디오 정지 기록을 저장합니다.
        VideoPlayEntity videoPlayEntity = new VideoPlayEntity();
        videoPlayEntity.setVideoEntity(videoEntity);
        videoPlayEntity.setUserEntity(userEntity);
        videoPlayEntity.setStopTime(stopTime);
        videoPlayEntity.setCreatedAt(LocalDateTime.now());
        videoPlayEntity.setUpdatedAt(LocalDateTime.now());

        videoPlayRepository.save(videoPlayEntity);
    }


}