package beyond.momentours.member.command.application.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokenDTO {
    private String accessToken;
    private String refreshToken;
    private int[] expiration;
    private String memberEmail;
    private String provider;  // KAKAO, GOOGLE 등 OAuth 제공자 구분

    // 일반 로그인용 생성자
    public JwtTokenDTO(String accessToken, String refreshToken, int[] expiration, String memberEmail) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiration = expiration;
        this.memberEmail = memberEmail;
    }

    // OAuth 로그인용 생성자
    public JwtTokenDTO(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    // OAuth 응답을 위한 DTO 내부 클래스
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OAuthResponse {
        private String accessToken;
        private String tokenType;
        private String refreshToken;
        private String expiresIn;
        private String scope;
    }

    // 토큰 갱신을 위한 DTO 내부 클래스
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TokenRefreshRequest {
        private String refreshToken;
    }

    // 카카오 사용자 정보를 위한 DTO 내부 클래스
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KakaoUserResponse {
        private Long id;
        private String connected_at;
        private Properties properties;
        private KakaoAccount kakao_account;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Properties {
            private String nickname;
            private String profile_image;
            private String thumbnail_image;
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class KakaoAccount {
            private Boolean profile_nickname_needs_agreement;
            private Boolean profile_image_needs_agreement;
            private Profile profile;
            private Boolean has_email;
            private Boolean email_needs_agreement;
            private Boolean is_email_valid;
            private Boolean is_email_verified;
            private String email;

            @Getter
            @Setter
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Profile {
                private String nickname;
                private String thumbnail_image_url;
                private String profile_image_url;
                private Boolean is_default_image;
            }
        }
    }
}
