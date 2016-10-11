package com.anna.recept.repository;

import com.anna.recept.persist.TestTableEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface ITestingRepository extends IJdbcTemplateCrudOperations<TestTableEntity> {

    @Override
    default TestTableEntity typeMapper(ResultSet rs) throws SQLException {
        TestTableEntity table = new TestTableEntity();
        table.setId(rs.getInt("id"));
        table.setText(rs.getString("text"));
        return table;
    }

    @Override
    default String getTable(){
        return "testtable";
    }

    @Override
    default String getPkAsId(){
        return "id";
    }

    @Override
    default String getFieldTemplateAsString() {
        return "?";
    }

    @Override
    default String getFieldNamesAsString() {
        return "text";
    }

    @Override
    default PreparedStatement preparedStatementSetter(PreparedStatement ps, TestTableEntity table) {
        try {
            ps.setString(1, table.getText());
        } catch (SQLException e) {
            System.out.println("Repository error TEST");
        }
        return ps;
    }

    @Override
    default PreparedStatement updatePreparedStatementSetter(PreparedStatement ps, TestTableEntity table) {
        try {
            preparedStatementSetter(ps, table);
            ps.setInt(2, table.getId());
        } catch (SQLException e) {
            System.out.println("Repository error TEST");
        }
        return ps;
    }

    @Override
    default PreparedStatement deletePreparedStatementSetter(PreparedStatement ps, TestTableEntity table) {
        try {
            preparedStatementSetter(ps, table);
            ps.setInt(1, table.getId());
        } catch (SQLException e) {
            System.out.println("Repository error TEST");
        }
        return ps;
    }

    @Override
    default String getUpdateColunmTemplate(){
        return "text =?";
    }

}
