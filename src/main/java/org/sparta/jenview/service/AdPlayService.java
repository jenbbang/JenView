package org.sparta.jenview.service;

import org.sparta.jenview.dto.AdPlayDTO;
import org.sparta.jenview.entity.AdEntity;
import org.sparta.jenview.entity.AdPlayEntity;
import org.sparta.jenview.entity.UserEntity;
import org.sparta.jenview.mapper.AdPlayMapper;
import org.sparta.jenview.repository.AdPlayRepository;
import org.sparta.jenview.repository.AdRepository;
import org.sparta.jenview.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        AdEntity adEntity = adRepository.findById(adPlayDTO.getAdId())
                .orElseThrow(() -> new RuntimeException("Ad not found with id " + adPlayDTO.getAdId()));
        UserEntity userEntity = userRepository.findById(adPlayDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id " + adPlayDTO.getUserId()));

        AdPlayEntity adPlayEntity = adPlayMapper.toEntity(adPlayDTO, adEntity, userEntity);
        adPlayRepository.save(adPlayEntity);

        adEntity.setAdCount(adEntity.getAdCount() + 1);
        adEntity.setPlayTime(adEntity.getPlayTime() + adPlayDTO.getPlayTime());
        adRepository.save(adEntity);
    }

    public void updateAdPlay(Long id, AdPlayDTO adPlayDTO) {
        AdPlayEntity adPlayEntity = adPlayRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AdPlay not found with id " + id));
        adPlayEntity.setPlayTime(adPlayDTO.getPlayTime());
        adPlayRepository.save(adPlayEntity);
    }

    public void deleteAdPlay(Long id) {
        adPlayRepository.deleteById(id);
    }

    public void deleteAdPlaysByAdId(Long adId) {
        List<AdPlayEntity> adPlays = adPlayRepository.findAll().stream()
                .filter(adPlay -> adPlay.getAd().getId().equals(adId))
                .collect(Collectors.toList());
        adPlayRepository.deleteAll(adPlays);
    }
}
