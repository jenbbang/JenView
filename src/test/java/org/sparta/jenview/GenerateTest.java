package org.sparta.jenview;

import org.junit.jupiter.api.Test;
import org.sparta.jenview.ad.entity.AdEntity;
import org.sparta.jenview.ad.repository.AdRepository;
import org.sparta.jenview.plays.entity.AdPlayEntity;
import org.sparta.jenview.plays.entity.VideoPlayEntity;
import org.sparta.jenview.plays.repository.AdPlayRepository;
import org.sparta.jenview.plays.repository.VideoPlayRepository;
import org.sparta.jenview.users.entity.UserEntity;
import org.sparta.jenview.users.repository.UserRepository;
import org.sparta.jenview.videos.entity.VideoEntity;
import org.sparta.jenview.videos.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@SpringBootTest
public class GenerateTest {

    @Autowired
    private AdPlayRepository adPlayRepository;

    @Autowired
    private VideoPlayRepository videoPlayRepository;

    @Autowired
    private AdRepository adsRepository;

    @Autowired
    private VideoRepository videosRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void generateData(int numRecords) {
        Random random = new Random();
        List<UserEntity> users = userRepository.findAll().stream()
                .filter(user -> user.getId() != 1L && user.getId() != 10L)
                .collect(Collectors.toList());
        List<VideoEntity> videos = videosRepository.findAll();
        List<AdEntity> ads = adsRepository.findAll();

        for (VideoEntity video : videos) {
            for (int i = 0; i < numRecords; i++) {
                UserEntity user = users.get(random.nextInt(users.size()));
                int playTime = random.nextInt(1800) + 300; // 300초에서 1800초 사이의 랜덤 시간
                VideoPlayEntity videoPlay = new VideoPlayEntity();
                LocalDateTime startTime = LocalDateTime.now();
                videoPlay.setCreatedAt(startTime);
                videoPlay.setStopTime((long) playTime); // stopTime을 초 단위로 설정
                videoPlay.setLastPlayedTime(playTime); // 처음에는 playTime과 동일하게 설정
                videoPlay.setUserEntity(user);
                videoPlay.setVideoEntity(video);
                videoPlayRepository.save(videoPlay);

                int[] adTimes = {300, 600, 900}; // 광고 실행 시간

                for (int adTime : adTimes) {
                    if (playTime >= adTime) {
                        AdEntity ad = ads.get(random.nextInt(ads.size()));
                        AdPlayEntity adPlay = new AdPlayEntity();
                        adPlay.setCreatedAt(LocalDateTime.now());
                        adPlay.setVideoId(video);
                        adPlay.setAdId(ad);
                        adPlay.setPlayTime(adTime); // 광고 실행 시간 설정
                        adPlayRepository.save(adPlay);
                    }
                }
            }
        }
    }

    @Test
    public void testGenerateData() {
        generateData(1000); // 각 비디오에 대해 10개의 시청 기록을 생성
    }
}
