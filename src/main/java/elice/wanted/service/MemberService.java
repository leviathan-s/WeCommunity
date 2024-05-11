package elice.wanted.service;

import elice.wanted.entity.Member;
import elice.wanted.exception.LoginFailByEncryptedPasswordException;
import elice.wanted.exception.LoginFailByNicknameException;
import elice.wanted.exception.LoginFailException;
import elice.wanted.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member save(Member member) {
        Member savedMember = memberRepository.save(member);
        return savedMember;
    }

    public Member findByNickname(String nickname) {
        Member member = memberRepository.findByNickname(nickname).orElseThrow(() -> new LoginFailByNicknameException("해당하는 아이디가 없습니다."));
        return member;
    }

    public Member findByEncryptedPassword(String encryptedPassword) {
        Member member = memberRepository.findByEncryptedPassword(encryptedPassword).orElseThrow(() -> new LoginFailByEncryptedPasswordException("해당하는 비밀번호가 없습니다."));
        return member;
    }

    public Member findByNameAndEncryptedPassword(String nickname, String encryptedPassword) {
        Member member = memberRepository.findByNicknameAndEncryptedPassword(nickname, encryptedPassword).orElseThrow(() -> new LoginFailByEncryptedPasswordException("해당하는 비밀번호가 없습니다."));
        return member;
    }
}
