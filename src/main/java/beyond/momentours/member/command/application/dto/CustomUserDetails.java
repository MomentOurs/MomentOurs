package beyond.momentours.member.command.application.dto;

import beyond.momentours.member.command.domain.aggregate.entity.Member;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

// security의 loadUserByUsername의 리턴 값을 바꿔주기 위한(토큰 생성을 위해) 커스텀 user
@Getter
@Setter
@AllArgsConstructor
@ToString
public class CustomUserDetails implements UserDetails {

    private final Member member;
    private LocalDateTime expiration;

    public CustomUserDetails(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {
                return member.getMemberRole().name();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return member.getMemberPassword();
    }

    @Override
    public String getUsername() {
        return member.getMemberEmail();
    }

    public Long getMemberId() {
        return member.getMemberId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
