package kr.co.ync.controller;

import kr.co.ync.controller.enums.MemberEventType;
import kr.co.ync.controller.listeners.MemberEvent;
import kr.co.ync.controller.listeners.MemberListener;
import kr.co.ync.model.Member;
import kr.co.ync.model.MemberModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberController {
    private List<MemberListener> memberListeners = new ArrayList<>();
    private static final MemberController instance = new MemberController();

    private MemberController() {

    }

    public static MemberController getInstance() {
        return instance;
    }

    public synchronized void addMemberListener(MemberListener memberListener) {
        if (!memberListeners.contains(memberListener)) {
            memberListeners.add(memberListener);
        }
    }

    public List<Member> allMembers() throws SQLException {
        return MemberModel.getInstance().allMembers();
    }

    public Member save(Member member) {
        if (member != null) {
            try {
                MemberModel.getInstance().register(member);
                notifyListeners(
                        new MemberEvent<>(member, MemberEventType.REGISTER)
                );
            } catch (SQLException e) {

            }
        }
        return member;
    }

    private void notifyListeners(MemberEvent<Member> memberEvent) {
        memberListeners.forEach(listener -> {
            switch (memberEvent.getType()) {
                case REGISTER -> listener.register(memberEvent);
            }
        });
    }

    // 이메일/휴대전화 중복확인 메서드
    public boolean isDuplicate(Member member) throws SQLException {
        return MemberModel.getInstance().isDuplicate(member);
    }

    // row 삭제
    public void delete(Member member) {
        try {
            MemberModel.getInstance().delete(member);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Member member) throws SQLException {
        MemberModel.getInstance().update(member);  // 수정된 라인
    }

}
