package com.anna.recept.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public interface IJdbcTemplateCrudOperations<T> {

    default List<T> findAll() {
        final String sql = "SELECT * FROM " + getTable();
        return getJdbc().query(sql, (rs, rowNum) -> typeMapper(rs));
    }

    default T findById(final int id) {
        final String sql = "SELECT * FROM " + getTable() + " WHERE " + getPkAsId() + " =?";
        return getJdbc().query(sql, ps -> ps.setInt(1, id), entityTypeMapper());
    }

    default int save(T entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final String sql = "INSERT INTO " + getTable() + " (" + getFieldNamesAsString() +
                ") VALUES (" + getFieldTemplateAsString() + ")";
        getJdbc().update(con -> preparedStatementSetter(con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS), entity), keyHolder);
        return (int) keyHolder.getKeys().get(getPkAsId());
    }

    default void update(T entity) {
        final String sql = "UPDATE " + getTable() + " SET " + getUpdateColunmTemplate() + " WHERE " + getPkAsId() + " =?";
        getJdbc().update(con -> updatePreparedStatementSetter(con.prepareStatement(sql), entity));
    }

    default void delete(T entity) {
        final String sql = "DELETE FROM " + getTable() + " WHERE " + getPkAsId() + " =?";
        getJdbc().update(con -> deletePreparedStatementSetter(con.prepareStatement(sql), entity));
    }

    default void deleteAll() {
        final String sql = "DELETE FROM " + getTable();
        getJdbc().update(sql);
    }

    default ResultSetExtractor<T> entityTypeMapper() {
        return rs -> {
            try {
                rs.next();
                return typeMapper(rs);
            } catch (Exception ex) {
                return null;
            }
        };
    }

    T typeMapper(ResultSet rs) throws SQLException;

    JdbcTemplate getJdbc();

    String getTable();

    String getPkAsId();

    String getFieldTemplateAsString();

    String getFieldNamesAsString();

    PreparedStatement preparedStatementSetter(PreparedStatement ps, T entity);

    PreparedStatement updatePreparedStatementSetter(PreparedStatement ps, T entity);

    String getUpdateColunmTemplate();

    PreparedStatement deletePreparedStatementSetter(PreparedStatement ps, T entity);

    // some methods for custom queries building

    default List<T> getFoundList(String sql, PreparedStatementSetter psSetter) {
        return getJdbc().query(sql, psSetter,(rs, rowNum)->typeMapper(rs));
    }

    default T getFoundEntity(String sql, PreparedStatementSetter psSetter) {
        return getJdbc().query(sql, psSetter, entityTypeMapper());
    }

    default byte[] getFile(String sql, PreparedStatementSetter psSetter, String fileColumn) {
        return getJdbc().query(sql, psSetter, rs -> {
            try {
                rs.next();
                return rs.getBytes(fileColumn);
            } catch (Exception ex) {
                return null;
            }
        });
    }
}
