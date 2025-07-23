package kr.co.ync.model;


import kr.co.ync.dao.MemberDao;
import kr.co.ync.dao.factory.DaoFactory;

import java.sql.SQLException;
import java.util.List;

public class MemberModel {
    private static final MemberModel instance = new MemberModel();

    private MemberModel() {
    }

    public static MemberModel getInstance() {
        return instance;
    }

    public List<Member> allMembers() throws SQLException {
        return memberDao().all();
    }

    public void register(Member member) throws SQLException {
        memberDao().insert(member);
    }

    private static MemberDao memberDao() {
        return DaoFactory.getDatabase().getMemberDao();
    }

    public boolean isDuplicate(Member member) throws SQLException {
        return memberDao().isExistsEmail(member.getEmail()) ||
                memberDao().isExistsPhone(member.getPhone());
    }

    public void update(Member member) throws SQLException {
        memberDao().update(member);
    }

    public void delete(Member member) throws SQLException {
        memberDao().delete(member);
    }

}
