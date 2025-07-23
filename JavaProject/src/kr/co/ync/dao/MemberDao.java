package kr.co.ync.dao;


import kr.co.ync.model.Member;

import java.sql.SQLException;
import java.util.List;

public interface MemberDao {

    List<Member> all() throws SQLException;

    Member insert(Member member) throws SQLException;

    // 이메일, 전번 중복확인
    boolean isExistsEmail(String email) throws SQLException;
    boolean isExistsPhone(String phone) throws SQLException;

    void delete(Member member) throws SQLException;

    void update(Member member) throws SQLException;
}
