package org.sparta.jenview.service;


import org.sparta.jenview.dto.GoogleResponse;
import org.sparta.jenview.dto.OAuth2Response;
import org.sparta.jenview.dto.UserDTO;
import org.sparta.jenview.entity.CustomOAuth2User;
import org.sparta.jenview.entity.Role;
import org.sparta.jenview.entity.Users;
import org.sparta.jenview.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

//사용자 정보를 로드하고 처리하는 서비스
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    //userRepository 사용자 정보를 저장하고 조회하기 위한 Repository
    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // 상위 클래스의 loadUser 메서드를 호출하여 OAuth2 사용자 정보를 로드
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 클라이언트 등록 ID를 가져옴
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        // Google 로그인인 경우 GoogleResponse 객체를 생성
        if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }
        System.out.println(oAuth2Response);

        // 사용자 이름을 제공자와 제공자 ID를 조합하여 생성
        String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
        // 데이터베이스에서 사용자 이름으로 기존 데이터를 조회
        Users existData = userRepository.findByUsername(username);

        // 데이터베이스에 사용자 데이터가 없는 경우 새 사용자 데이터를 저장
        if (existData == null) {
            System.out.println(existData);

            Users user = new Users();
            user.setUsername(username);
            user.setEmail(oAuth2Response.getEmail());
            user.setName(oAuth2Response.getName());
            user.setRole(Role.USER);

            userRepository.save(user);

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(username);
            userDTO.setName(oAuth2Response.getName());
            user.setRole(Role.USER);

            return new CustomOAuth2User(userDTO);
        }
        else {
            // 기존 사용자의 이메일과 이름을 업데이트
            existData.setEmail(oAuth2Response.getEmail());
            existData.setName(oAuth2Response.getName());

            userRepository.save(existData);

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(existData.getUsername());
            userDTO.setName(oAuth2Response.getName());
            userDTO.setRole(existData.getRole().toString());

            return new CustomOAuth2User(userDTO);
        }
    }
}