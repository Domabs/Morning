package kr.co.ync.dao.factory;


import kr.co.ync.dao.MemberDao;

import java.sql.Connection;

public abstract class DaoFactory {
    public abstract Connection openConnection();

    public abstract MemberDao getMemberDao();

    public static DaoFactory getDatabase() {
        return new Mysql();
    }

}
