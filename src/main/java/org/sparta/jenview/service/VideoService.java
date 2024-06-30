package org.sparta.jenview.service;

import jakarta.transaction.Transactional;
import org.sparta.jenview.dto.VideoDTO;
import org.sparta.jenview.entity.UserEntity;
import org.sparta.jenview.entity.VideoEntity;
import org.sparta.jenview.mapper.VideoMapper;
import org.sparta.jenview.repository.UserRepository;
import org.sparta.jenview.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService {

    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final VideoMapper videoMapper;

    @Autowired
    public VideoService(VideoRepository videoRepository, UserRepository userRepository, VideoMapper videoMapper) {
        this.videoRepository = videoRepository;
        this.userRepository = userRepository;
        this.videoMapper = videoMapper;
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

}
