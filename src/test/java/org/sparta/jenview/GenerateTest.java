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
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Transactional
    public void generateData(int numRecords) {
        Random random = new Random();
        List<UserEntity> users = userRepository.findAll().stream()
                .filter(user -> user.getId() != 1L && user.getId() != 10L)
                .collect(Collectors.toList());
        List<VideoEntity> videos = videosRepository.findAll();
        List<AdEntity> ads = adsRepository.findAll();

        List<VideoPlayEntity> videoPlays = new ArrayList<>();
        List<AdPlayEntity> adPlays = new ArrayList<>();

        for (int i = 0; i < numRecords; i++) {
            VideoEntity video = videos.get(random.nextInt(videos.size()));
            UserEntity user = users.get(random.nextInt(users.size()));
            int playTime = random.nextInt(1800) + 300; // 300초에서 1800초 사이의 랜덤 시간
            VideoPlayEntity videoPlay = new VideoPlayEntity();
            LocalDateTime startTime = LocalDateTime.now();
            videoPlay.setCreatedAt(startTime);
            videoPlay.setStopTime((long) playTime); // stopTime을 초 단위로 설정
            videoPlay.setLastPlayedTime(playTime); // 처음에는 playTime과 동일하게 설정
            videoPlay.setUserEntity(user);
            videoPlay.setVideoEntity(video);
            videoPlays.add(videoPlay);

            // 광고를 동영상 길이에 따라 300초 간격으로 삽입
            for (int adTime = 300; adTime <= playTime; adTime += 300) {
                AdEntity ad = ads.get(random.nextInt(ads.size()));
                AdPlayEntity adPlay = new AdPlayEntity();
                adPlay.setCreatedAt(LocalDateTime.now());
                adPlay.setVideoId(video);
                adPlay.setAdId(ad);
                adPlay.setPlayTime(adTime); // 광고 실행 시간 설정
                adPlays.add(adPlay);
            }

            // 배치 크기마다 데이터를 저장하고 리스트를 비웁니다.
            if (videoPlays.size() >= 1000) {
                saveBatch(videoPlays, adPlays);
                videoPlays.clear();
                adPlays.clear();
            }
        }

        // 남은 데이터를 저장합니다.
        if (!videoPlays.isEmpty()) {
            saveBatch(videoPlays, adPlays);
        }
    }

    @Transactional
    public void saveBatch(List<VideoPlayEntity> videoPlays, List<AdPlayEntity> adPlays) {
        videoPlayRepository.saveAll(videoPlays);
        adPlayRepository.saveAll(adPlays);
    }

    @Test
    public void testGenerateData() {
        transactionTemplate.execute(status -> {
            generateData(200000); // 각 비디오에 대해 지정된 수의 시청 기록을 생성
            return null;
        });
    }
}
