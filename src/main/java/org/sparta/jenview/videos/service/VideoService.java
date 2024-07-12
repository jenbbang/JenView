package org.sparta.jenview.videos.service;

import jakarta.transaction.Transactional;
import org.sparta.jenview.ad.service.AdService;
import org.sparta.jenview.plays.dto.VideoPlayDTO;
import org.sparta.jenview.plays.entity.VideoPlayEntity;
import org.sparta.jenview.plays.mapper.VideoPlayMapper;
import org.sparta.jenview.plays.repository.VideoPlayRepository;
import org.sparta.jenview.plays.service.VideoPlayService;
import org.sparta.jenview.users.entity.UnauthorizedException;
import org.sparta.jenview.users.entity.UserEntity;
import org.sparta.jenview.users.repository.UserRepository;
import org.sparta.jenview.videos.dto.VideoDTO;
import org.sparta.jenview.videos.dto.VideoRequestDTO;
import org.sparta.jenview.videos.entity.VideoEntity;
import org.sparta.jenview.videos.mapper.VideoMapper;
import org.sparta.jenview.videos.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class VideoService {

    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final VideoMapper videoMapper;
    private final VideoPlayRepository videoPlayRepository;
    private final VideoPlayMapper videoPlayMapper;
    private final VideoPlayService videoPlayService;
    private final AdService adService;

    @Autowired
    public VideoService(AdService adService, VideoRepository videoRepository, UserRepository userRepository, VideoMapper videoMapper, VideoPlayRepository videoPlayRepository, VideoPlayMapper videoPlayMapper, VideoPlayService videoPlayService) {
        this.videoPlayService = videoPlayService;
        this.videoRepository = videoRepository;
        this.userRepository = userRepository;
        this.videoMapper = videoMapper;
        this.videoPlayRepository = videoPlayRepository;
        this.videoPlayMapper = videoPlayMapper;
        this.adService = adService;
    }


    // 모든 비디오 목록을 가져오는 메서드
    public List<VideoDTO> getVideoList() {
        List<VideoEntity> videos = videoRepository.findAll();
        return videos.stream().map(videoMapper::toDTO).toList();
    }

    // 특정 ID의 비디오 정보를 가져오는 메서드
    public VideoDTO getVideoById(Long id) {
        VideoEntity videoEntity = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(id + "번 비디오가 존재하지 않습니다."));
        return videoMapper.toDTO(videoEntity);
    }

    // 새로운 비디오를 생성하는 메서드
    public Long createVideo(VideoDTO videoDTO) {
        try {
            // UserEntity 찾기
            UserEntity userEntity = userRepository.findById(videoDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id " + videoDTO.getUserId()));

            // 사용자 권한 확인
            String userRole = String.valueOf(userEntity.getRole());
            System.out.println("User Role: " + userRole);
            if (!"SELLER".equals(userRole)) {
                throw new UnauthorizedException("동영상을 업로드할 권한이 없습니다");
            }

            // VideoEntity 변환
            VideoEntity videoEntity = videoMapper.toEntity(videoDTO);
            if (videoEntity == null) {
                throw new RuntimeException("Failed to map VideoDTO to VideoEntity");
            }
            System.out.println("VideoEntity mapped successfully: " + videoEntity);

            // VideoEntity 설정
            videoEntity.setUserEntity(userEntity);
            videoEntity.setCreatedAt(LocalDateTime.now());

            // VideoEntity 저장
            videoRepository.save(videoEntity);
            System.out.println("VideoEntity saved successfully");

            return videoEntity.getId(); // 생성된 비디오의 ID를 반환
        } catch (Exception e) {
            e.printStackTrace(); // 예외 메시지 출력
            throw e; // 예외 다시 던지기
        }
    }


    // 특정 ID의 비디오 정보를 업데이트하는 메서드
    public void updateVideo(Long id, VideoRequestDTO videoDTO) {
        VideoEntity videoEntity = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found with id " + id));
        videoEntity.setTitle(videoDTO.getTitle());
        videoEntity.setDescription(videoDTO.getDescription());
        videoEntity.setViewCount(videoDTO.getViewCount());
        videoEntity.setPlayTime(videoDTO.getPlayTime());
        videoEntity.setUpdatedAt(LocalDateTime.now()); // 업데이트 시간 설정

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
    public void deleteVideosByVideoId(Long id) {
        videoRepository.deleteByVideoId(id);
    }

    //동영상 재생 저장하는 메서드
    public VideoPlayDTO playVideo(Long videoId, Long userId, Long stopTime) {
        VideoEntity videoEntity = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("비디오를 찾을 수 없습니다."));
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 동영상 게시자가 동영상을 시청하는 경우 조회수와 광고 시청 횟수를 증가시키지 않음
        if (videoEntity.getUserEntity().getId().equals(userId)) {
            // 동영상 게시자가 시청하는 경우 stopTime을 0으로 설정
            return mapToDTO(videoEntity, userEntity, 0L);
        }

        List<VideoPlayEntity> videoPlayEntities = videoPlayRepository.findByVideoEntity_IdAndUserEntity_Id(videoId, userId);
        VideoPlayEntity videoPlayEntity = new VideoPlayEntity();
        videoPlayEntity.setVideoEntity(videoEntity);
        videoPlayEntity.setUserEntity(userEntity);

        Long currentStopTime = (stopTime != null) ? stopTime : 0L;
        LocalDateTime now = LocalDateTime.now();
        boolean shouldUpdateVideoEntity = false;
        int initialViewCount = videoEntity.getViewCount(); // 초기 viewCount 저장

        if (!videoPlayEntities.isEmpty()) {
            VideoPlayEntity lastVideoPlayEntity = videoPlayEntities.get(videoPlayEntities.size() - 1); // 가장 최근 기록
            LocalDateTime lastPlayedAt = lastVideoPlayEntity.getCreatedAt();

            // 이전 기록이 비디오 전체 길이만큼 재생된 경우, 새로운 VideoPlayEntity 생성
            if (lastVideoPlayEntity.getLastPlayedTime() >= videoEntity.getPlayTime()) {
                videoPlayEntity.setStopTime(0L);
                videoPlayEntity.setLastPlayedTime(0); // 새로운 재생 기록이므로 lastPlayedTime을 0으로 설정
                videoPlayEntity.setCreatedAt(now);
                videoPlayRepository.save(videoPlayEntity);
                videoEntity.setViewCount(videoEntity.getViewCount() + 1);
                videoRepository.save(videoEntity);
                return mapToDTO(videoPlayEntity);
            }

            // 30초 이내의 다시 재생은 어뷰징으로 간주하여 조회수 증가 및 시청 횟수 증가를 막음
            if (ChronoUnit.SECONDS.between(lastPlayedAt, now) < 30) {
                // 30초 이내에 재생한 경우, 조회수와 VideoPlay 엔티티를 업데이트하지 않음
                return mapToDTO(lastVideoPlayEntity);
            } else {
                currentStopTime += lastVideoPlayEntity.getStopTime();
                videoPlayEntity.setLastPlayedTime(lastVideoPlayEntity.getLastPlayedTime()); // 이전 시청 기록의 lastPlayedTime을 설정
                shouldUpdateVideoEntity = true; // videoEntity를 업데이트해야 함을 표시
            }
        } else {
            shouldUpdateVideoEntity = true; // 새로운 재생 기록이므로 videoEntity를 업데이트해야 함을 표시
        }

        // 30초 이상 재생한 경우에만 조회수와 VideoPlay 엔티티를 업데이트함
        if (shouldUpdateVideoEntity) {
            videoEntity.setViewCount(videoEntity.getViewCount() + 1); // 조회수 증가
        }
        videoPlayEntity.setStopTime(currentStopTime);
        videoPlayEntity.setCreatedAt(now); // LocalDateTime으로 설정

        // videoEntity가 실제로 변경된 경우에만 저장
        if (shouldUpdateVideoEntity && initialViewCount != videoEntity.getViewCount()) {
            videoRepository.save(videoEntity);
        }
        videoPlayRepository.save(videoPlayEntity);

        // 광고 시청 횟수 증가
        videoPlayService.incrementAdViewCountsForVideo(videoId, Math.toIntExact(currentStopTime));

        return mapToDTO(videoPlayEntity);
    }

    private VideoPlayDTO mapToDTO(VideoEntity videoEntity, UserEntity userEntity, Long stopTime) {
        VideoPlayDTO dto = new VideoPlayDTO();
        dto.setVideoId(videoEntity.getId());
        dto.setUserId(userEntity.getId());
        dto.setStopTime(stopTime); // 변경된 필드
        dto.setCreatedAt(LocalDateTime.now());
        return dto;
    }

    private VideoPlayDTO mapToDTO(VideoPlayEntity videoPlayEntity) {
        VideoPlayDTO dto = new VideoPlayDTO();
        dto.setId(videoPlayEntity.getId());
        dto.setVideoId(videoPlayEntity.getVideoEntity().getId());
        dto.setUserId(videoPlayEntity.getUserEntity().getId());
        dto.setStopTime((videoPlayEntity.getStopTime() != 0) ? videoPlayEntity.getStopTime() : 0L); // 기본값 0으로 설정된 stopTime 사용
        dto.setLastPlayedTime(videoPlayEntity.getLastPlayedTime());
        dto.setCreatedAt(videoPlayEntity.getCreatedAt());
        return dto;
    }


    // 특정 ID의 비디오를 찾하는 메서드
    public VideoEntity getVideoEntityById(Long id) {
        return videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found with id " + id));
    }

    // 비디오 정지
    @Transactional
    public String stopVideo(VideoPlayDTO videoStopDTO) {
        Long videoId = videoStopDTO.getVideoId();
        Long userId = videoStopDTO.getUserId();
        Long stopTime = videoStopDTO.getStopTime();

        // 비디오 엔티티를 찾습니다.
        VideoEntity videoEntity = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("비디오를 찾을 수 없습니다."));

        // 유저 엔티티를 찾습니다.
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 이미 해당 비디오와 유저에 대한 정지 기록이 있는지 확인합니다.
        List<VideoPlayEntity> videoPlayEntities = videoPlayRepository.findByVideoEntity_IdAndUserEntity_Id(videoId, userId);
        VideoPlayEntity videoPlayEntity;
        Long totalPlayTime = stopTime;
        Long previousPlayTime = 0L;

        if (!videoPlayEntities.isEmpty()) {
            // 이미 존재하는 비디오 시청 기록이 있는 경우
            VideoPlayEntity lastVideoPlayEntity = videoPlayEntities.get(videoPlayEntities.size() - 1); // 가장 최근 기록을 가져옴

            // 디버그 로그 추가
            System.out.println("기존 시청 기록 존재: lastPlayedTime=" + lastVideoPlayEntity.getLastPlayedTime());

            previousPlayTime = (long) lastVideoPlayEntity.getLastPlayedTime();
            totalPlayTime = previousPlayTime + stopTime; // 이전 재생 시간에 현재 정지 시간 추가

            // 총 재생 시간이 비디오의 전체 길이를 초과하지 않도록 제한
            if (totalPlayTime > videoEntity.getPlayTime()) {
                Long remainingTime = videoEntity.getPlayTime() - lastVideoPlayEntity.getLastPlayedTime();
                System.out.println("해당 동영상의 길이가 넘었습니다. 남은 재생 가능 시간: " + remainingTime + "초");
                return "해당 동영상의 길이가 넘었습니다. 남은 재생 가능 시간: " + remainingTime + "초";
            }

            // 새로운 정지 시간을 저장
            lastVideoPlayEntity.setStopTime(stopTime + lastVideoPlayEntity.getStopTime());
            // 누적 재생 시간을 저장
            lastVideoPlayEntity.setLastPlayedTime(Math.toIntExact(totalPlayTime));

            videoPlayEntity = lastVideoPlayEntity;
            videoPlayRepository.save(videoPlayEntity);
        } else {
            // 새로운 비디오 시청 기록을 생성
            videoPlayEntity = new VideoPlayEntity();
            videoPlayEntity.setVideoEntity(videoEntity);
            videoPlayEntity.setUserEntity(userEntity);
            videoPlayEntity.setStopTime(stopTime);
            videoPlayEntity.setLastPlayedTime(Math.toIntExact(totalPlayTime));
            videoPlayRepository.save(videoPlayEntity);
        }

        // 광고 재생 기록을 저장
        adService.recordAdPlay(videoId, totalPlayTime, previousPlayTime);

        return "비디오 정지 기록이 저장되었습니다.";
    }
}