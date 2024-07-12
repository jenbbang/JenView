package org.sparta.jenview.plays.service;

import org.sparta.jenview.ad.repository.AdRepository;
import org.sparta.jenview.plays.dto.AdPlayDTO;
import org.sparta.jenview.plays.entity.AdPlayEntity;
import org.sparta.jenview.plays.mapper.AdPlayMapper;
import org.sparta.jenview.plays.repository.AdPlayRepository;
import org.sparta.jenview.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}