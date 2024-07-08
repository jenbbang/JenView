package org.sparta.jenview.plays.service;

import org.sparta.jenview.plays.dto.VideoPlayDTO;
import org.sparta.jenview.users.entity.UserEntity;
import org.sparta.jenview.videos.entity.VideoEntity;
import org.sparta.jenview.plays.entity.VideoPlayEntity;
import org.sparta.jenview.plays.mapper.VideoPlayMapper;
import org.sparta.jenview.users.repository.UserRepository;
import org.sparta.jenview.plays.repository.VideoPlayRepository;
import org.sparta.jenview.videos.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VideoPlayService {

    private final UserRepository userRepository;
    private final VideoRepository videoRepository;
    private final VideoPlayRepository videoPlayRepository;
    private final VideoPlayMapper videoPlayMapper;

    @Autowired
    public VideoPlayService(UserRepository userRepository, VideoPlayRepository videoPlayRepository, VideoPlayMapper videoPlayMapper, VideoRepository videoRepository) {
        this.userRepository = userRepository;
        this.videoPlayRepository = videoPlayRepository;
        this.videoPlayMapper = videoPlayMapper;
        this.videoRepository = videoRepository;
    }


    // 모든 비디오 플레이 목록을 가져오는 메서드
    public List<VideoPlayDTO> getVideoList() {
        List<VideoPlayEntity> videoPlayList = videoPlayRepository.findAll();
        return videoPlayList.stream().map(videoPlayMapper::toDTO).toList();
    }

    // 특정 ID 비디오 플레이 목록을 가져오는 메서드
    public VideoPlayDTO getVideoById(Long id) {
        VideoPlayEntity videoPlayEntity = videoPlayRepository.findById(id).orElseThrow(() -> new RuntimeException("Video not found with id " + id));
        return videoPlayMapper.toDTO(videoPlayEntity);
    }

    // 새로운 비디오 플레이 생성 메서드
    public Long createVideoPlay(VideoPlayDTO videoPlayDTO) {
        UserEntity userEntity = userRepository.findById(videoPlayDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found with id " + videoPlayDTO.getUserId()));
        VideoEntity videoEntity = videoRepository.findById(videoPlayDTO.getVideoId()).orElseThrow(() -> new RuntimeException("Video not found with id " + videoPlayDTO.getVideoId()));

        VideoPlayEntity videoPlayEntity = videoPlayMapper.toEntity(videoPlayDTO, userEntity, videoEntity);
        videoPlayEntity.setUserEntity(userEntity);
        videoPlayEntity.setVideoEntity(videoEntity);

        videoPlayRepository.save(videoPlayEntity);
        return videoPlayEntity.getId(); // 생성된 비디오플레이의 ID를 반환
    }

    @Transactional(readOnly = true)
    public VideoPlayEntity findById(Long id) {
        return videoPlayRepository.findById(id).orElse(null);
    }

    // 특정 비디오플레이 기록 삭제
    @Transactional
    public void deleteVideoPlayById(Long id) {
        videoPlayRepository.deleteById(id);
    }

    public void incrementAdViewCountsForVideo(Long videoId, int newPlayTime) {
    }
}

