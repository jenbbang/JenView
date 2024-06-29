package org.sparta.jenview.service;

import org.sparta.jenview.dto.VideoPlayDTO;
import org.sparta.jenview.entity.UserEntity;
import org.sparta.jenview.entity.VideoPlayEntity;
import org.sparta.jenview.repository.UserRepository;
import org.sparta.jenview.repository.VideoPlayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VideoPlayService {

    private UserRepository userRepository;
    private VideoPlayRepository videoPlayRepository;

    @Autowired
    public VideoPlayService(UserRepository userRepository, VideoPlayRepository videoPlayRepository) {
        this.userRepository = userRepository;
        this.videoPlayRepository = videoPlayRepository;
    }

    private VideoPlayDTO toDTO(VideoPlayEntity videoplayEntity) {
        VideoPlayDTO videoDTO = new VideoPlayDTO();
        videoDTO.setUserId(videoplayEntity.getUserEntity().getId()); // userId 설정
        videoDTO.setVideoId(videoplayEntity.getId());
        videoDTO.setLast_played_time(videoplayEntity.getLastPlayedTime());
        return videoDTO;
    }

    private VideoPlayEntity toEntity(VideoPlayDTO videoplayDTO) {
        VideoPlayEntity videoPlayEntity = new VideoPlayEntity();
        videoPlayEntity.setId(videoplayDTO.getVideoId());
        videoPlayEntity.setLastPlayedTime(videoplayDTO.getLast_played_time());

        // userEntity 설정
        UserEntity userEntity = userRepository.findById(videoplayDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id " + videoplayDTO.getUserId()));
        videoPlayEntity.setUserEntity(userEntity);
        return videoPlayEntity;
    }

    // 모든 비디오 플레이 목록을 가져오는 메서드
    public List<VideoPlayDTO> getVideoList() {
        List<VideoPlayEntity> vidoePlayList = videoPlayRepository.findAll();
        return vidoePlayList.stream().map(this::toDTO).toList();
    }

    // 특정 ID 비디오 플레이 목록을 가져오는 메서드
    public VideoPlayDTO getVideoById(Long id) {
        VideoPlayEntity videoPlayEntity = videoPlayRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found with id " + id));
        return toDTO(videoPlayEntity);
    }

    // 새로운 비디오 플레이 생성 메서드
    public Long createvidoePlay(VideoPlayDTO videoPlayDTO) {
        VideoPlayEntity videoPlayEntity = toEntity(videoPlayDTO);
        videoPlayRepository.save(videoPlayEntity);
        return videoPlayEntity.getId(); // 생성된 비디오플레이의 ID를 반환
    }

    @Transactional(readOnly = true)
    public VideoPlayEntity findById(Long id) {
        return videoPlayRepository.findById(id).orElse(null);
    }

    // 특정 비디오플레이 기록 삭제
    @Transactional
    public void deletevideoplayById(Long id) {
        videoPlayRepository.deleteById(id);
    }
}

