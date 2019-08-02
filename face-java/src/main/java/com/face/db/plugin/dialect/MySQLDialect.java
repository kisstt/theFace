package com.face.db.plugin.dialect;

public class MySQLDialect extends Dialect {
    @Override
    public boolean supportsLimit() {
        return true;
    }

    @Override
    public boolean supportsLimitOffset() {
        return super.supportsLimitOffset();
    }

    @Override
    public String getLimitString(String sql, int pageNo, int pageSize) {
        return new StringBuffer(sql.length() + 20).append(sql)
                .append(" LIMIT " + pageSize + " OFFSET " +((pageNo-1)* pageSize)).toString();
    }
}
