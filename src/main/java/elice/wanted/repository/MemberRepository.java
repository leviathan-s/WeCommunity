package elice.wanted.repository;

import elice.wanted.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByNickname(String nickname);
    Optional<Member> findByEncryptedPassword(String nickname);

    Optional<Member> findByNicknameAndEncryptedPassword(String nickname, String encryptedPassword);
}
