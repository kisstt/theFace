package com.face.db.plugin.dialect;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Dialect {
    public boolean supportsLimit() {
        return false;
    }

    public boolean supportsLimitOffset() {
        return supportsLimit();
    }

    public abstract String getLimitString(String sql, int pageNo, int pageSize);
}
