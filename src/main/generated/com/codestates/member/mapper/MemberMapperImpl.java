package com.codestates.member.mapper;

import com.codestates.member.dto.MemberDto;
import com.codestates.member.entity.Member;
import com.codestates.stamp.Stamp;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-06T15:22:22+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 11.0.17 (Azul Systems, Inc.)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public Member memberPostToMember(MemberDto.Post requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Member member = new Member();

        member.setEmail( requestBody.getEmail() );
        member.setName( requestBody.getName() );
        member.setPhone( requestBody.getPhone() );

        return member;
    }

    @Override
    public Member memberPatchToMember(MemberDto.Patch requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Member member = new Member();

        member.setMemberId( requestBody.getMemberId() );
        member.setName( requestBody.getName() );
        member.setPhone( requestBody.getPhone() );
        member.setMemberStatus( requestBody.getMemberStatus() );

        return member;
    }

    @Override
    public MemberDto.response memberToMemberResponse(Member member) {
        if ( member == null ) {
            return null;
        }

        long memberId = 0L;
        String email = null;
        String name = null;
        String phone = null;
        Member.MemberStatus memberStatus = null;
        Stamp stamp = null;

        if ( member.getMemberId() != null ) {
            memberId = member.getMemberId();
        }
        email = member.getEmail();
        name = member.getName();
        phone = member.getPhone();
        memberStatus = member.getMemberStatus();
        stamp = member.getStamp();

        MemberDto.response response = new MemberDto.response( memberId, email, name, phone, memberStatus, stamp );

        return response;
    }

    @Override
    public List<MemberDto.response> membersToMemberResponses(List<Member> members) {
        if ( members == null ) {
            return null;
        }

        List<MemberDto.response> list = new ArrayList<MemberDto.response>( members.size() );
        for ( Member member : members ) {
            list.add( memberToMemberResponse( member ) );
        }

        return list;
    }
}
