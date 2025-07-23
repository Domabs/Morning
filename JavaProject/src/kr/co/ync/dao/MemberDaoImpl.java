package kr.co.ync.dao;

import kr.co.ync.dao.factory.DaoFactory;
import kr.co.ync.model.Member;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MemberDaoImpl implements MemberDao {
    private static final String ALL = "SELECT * FROM tb_members";
    private static final String INSERT = "INSERT INTO tb_members (email, name, phone, birth, reg_date) VALUES (?, ?, ?, ?, ?)";

    // 중복확인 쿼리문
    private static final String EXISTS_EMAIL = "SELECT COUNT(*) FROM tb_members WHERE email = ?";
    private static final String EXISTS_PHONE = "SELECT COUNT(*) FROM tb_members WHERE phone = ?";

    private static final String DELETE = "DELETE FROM tb_members WHERE id = ?";

    private static final String UPDATE = "UPDATE tb_members SET email = ?, name = ?, phone = ?, birth = ? WHERE id = ?";

    @Override
    public List<Member> all() throws SQLException {
        List<Member> members = new ArrayList<>();

        Connection connection = DaoFactory.getDatabase().openConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(ALL);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            members.add(createMember(resultSet));
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return members;
    }

    @Override
    public Member insert(Member member) throws SQLException {

        LocalDateTime now = LocalDateTime.now();
        Connection c = DaoFactory.getDatabase().openConnection();
        PreparedStatement pstmt = c.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, member.getEmail());
        pstmt.setString(2, member.getName());
        pstmt.setString(3, member.getPhone());
        pstmt.setDate(4, Date.valueOf(member.getBirth()));
        pstmt.setTimestamp(5, Timestamp.valueOf(now));

        pstmt.executeUpdate();

        ResultSet rset = pstmt.getGeneratedKeys();

        rset.next();
        Long idGenerated = rset.getLong(1);

        member.setId(idGenerated);
        member.setRegDate(now);

        rset.close();
        pstmt.close();
        c.close();

        return member;
    }

    @Override
    public boolean isExistsEmail(String email) throws SQLException {
        try (Connection conn = DaoFactory.getDatabase().openConnection();
             PreparedStatement pstmt = conn.prepareStatement(EXISTS_EMAIL)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }

    public boolean isExistsPhone(String phone) throws SQLException {

        try (Connection conn = DaoFactory.getDatabase().openConnection();
             PreparedStatement pstmt = conn.prepareStatement(EXISTS_PHONE)) {
            pstmt.setString(1, phone);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }

    }


    private Member createMember(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String email = resultSet.getString("email");
        String name = resultSet.getString("name");
        String phone = resultSet.getString("phone");
        LocalDate birth = resultSet.getObject("birth", LocalDate.class);
        LocalDateTime regDate = resultSet.getObject("reg_date", LocalDateTime.class);
        return new Member(id, email, name, phone, birth, regDate);
    }

    @Override
    public void delete(Member member) throws SQLException {
        try (Connection c = DaoFactory.getDatabase().openConnection();
             PreparedStatement pstmt = c.prepareStatement(DELETE)) {
            pstmt.setLong(1, member.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void update(Member member) throws SQLException {
        try (Connection c = DaoFactory.getDatabase().openConnection();
             PreparedStatement pstmt = c.prepareStatement(UPDATE)) {

//            "UPDATE tb_members SET email = ?1, name = ?2, phone = ?3, birth = 4? WHERE id = 5?";
            pstmt.setString(1, member.getEmail());
            pstmt.setString(2, member.getName());
            pstmt.setString(3, member.getPhone());
            pstmt.setDate(4, Date.valueOf(member.getBirth()));
            pstmt.setLong(5, member.getId());

            pstmt.executeUpdate();
        }
    }


}
