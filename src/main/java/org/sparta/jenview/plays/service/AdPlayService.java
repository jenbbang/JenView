package org.sparta.jenview.plays.service;

import org.sparta.jenview.ad.entity.AdsId;
import org.sparta.jenview.plays.dto.AdPlayDTO;
import org.sparta.jenview.ad.entity.AdEntity;
import org.sparta.jenview.plays.entity.AdPlayEntity;
import org.sparta.jenview.users.entity.UserEntity;
import org.sparta.jenview.plays.mapper.AdPlayMapper;
import org.sparta.jenview.plays.repository.AdPlayRepository;
import org.sparta.jenview.ad.repository.AdRepository;
import org.sparta.jenview.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdPlayService {

    private final AdPlayRepository adPlayRepository;
    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final AdPlayMapper adPlayMapper;

    @Autowired
    public AdPlayService(AdPlayRepository adPlayRepository, AdRepository adRepository, UserRepository userRepository, AdPlayMapper adPlayMapper) {
        this.adPlayRepository = adPlayRepository;
        this.adRepository = adRepository;
        this.userRepository = userRepository;
        this.adPlayMapper = adPlayMapper;
    }

    public List<AdPlayDTO> getAdPlayList() {
        List<AdPlayEntity> adPlayEntities = adPlayRepository.findAll();
        return adPlayEntities.stream().map(adPlayMapper::toDTO).collect(Collectors.toList());
    }

    public AdPlayDTO getAdPlayById(Long id) {
        AdPlayEntity adPlayEntity = adPlayRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AdPlay not found with id " + id));
        return adPlayMapper.toDTO(adPlayEntity);
    }

    @Transactional
    public void createAdPlay(AdPlayDTO adPlayDTO) {
        // 복합키 객체 생성 및 값 설정
        AdsId adsId = new AdsId();
        adsId.setId(adPlayDTO.getAdId());
        adsId.setVideoId(adPlayDTO.getVideoId());

        // 복합키를 사용하여 AdEntity 조회
        AdEntity adEntity = adRepository.findById(adsId)
                .orElseThrow(() -> new RuntimeException("Ad not found with id " + adPlayDTO.getAdId() + " and videoId " + adPlayDTO.getVideoId()));

        // UserEntity 조회
        UserEntity userEntity = userRepository.findById(adPlayDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id " + adPlayDTO.getUserId()));

        // 광고 시청 기록 추가
        AdPlayEntity adPlayEntity = new AdPlayEntity();
        adPlayEntity.setAdId(adPlayDTO.getAdId());
        adPlayEntity.setVideoId(adPlayDTO.getVideoId());
        adPlayEntity.setPlayTime(adPlayDTO.getPlayTime());
        adPlayEntity.setCreatedAt(LocalDate.now());

        adPlayRepository.save(adPlayEntity);

        // 광고 조회수 및 재생 시간 업데이트
        adEntity.setAdCount(adEntity.getAdCount() + 1);
        adEntity.setPlayTime(adEntity.getPlayTime() + adPlayDTO.getPlayTime());
        adRepository.save(adEntity);
    }

    public void deleteAdPlay(Long id) {
        adPlayRepository.deleteById(id);
    }

    public void deleteAdPlaysByAdId(Long adId) {
        List<AdPlayEntity> adPlays = adPlayRepository.findAll().stream()
                .filter(adPlay -> adPlay.getAd().equals(adId))
                .collect(Collectors.toList());
        adPlayRepository.deleteAll(adPlays);
    }
}
