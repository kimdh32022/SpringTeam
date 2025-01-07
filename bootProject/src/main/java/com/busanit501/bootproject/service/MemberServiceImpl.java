package com.busanit501.bootproject.service;

import com.busanit501.bootproject.domain.Member;
import com.busanit501.bootproject.domain.MemberRole;
import com.busanit501.bootproject.dto.MemberJoinDTO;
import com.busanit501.bootproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void join(MemberJoinDTO memberJoinDTO) throws MidExistException {
        String mid = memberJoinDTO.getMid();
         boolean exist = memberRepository.existsById(mid);
         if (exist) {
             throw new MidExistException();
         }
         // 중복 아이디가 없다. 회원 가입 진행하기.
      Member member = modelMapper.map(memberJoinDTO, Member.class);
         // 평문 패스워드, 암호화
        member.changePassword(passwordEncoder.encode(memberJoinDTO.getMpw()));
        // 일반 유저 권한 추가.
        member.addRole(MemberRole.USER);
         memberRepository.save(member);
    }

    @Override
    public void update(MemberJoinDTO memberJoinDTO) {
        Optional<Member> result = memberRepository.findById(memberJoinDTO.getMid());
        Member member = result.orElseThrow();
        member.changeMember(
                memberJoinDTO.getMpw(),
                memberJoinDTO.getEmail(),
                memberJoinDTO.getName(),
                memberJoinDTO.getGender(),
                memberJoinDTO.getBirthday(),
                memberJoinDTO.getPhone(),
                memberJoinDTO.getAddress()
        );

        memberRepository.save(member);
    }
}
