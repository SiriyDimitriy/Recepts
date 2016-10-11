package com.anna.recept.repository;

import com.anna.recept.persist.File;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface IFileRepository extends IJdbcTemplateCrudOperations<File>{

    @Override
    default File typeMapper(ResultSet rs) throws SQLException {
        File file = new File();
        file.setId(rs.getInt("id"));
        file.setFileContent(rs.getBytes("file_content"));
        return file;
    }

    @Override
    default String getTable(){
        return "file";
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
        return "file_content";
    }

    @Override
    default PreparedStatement preparedStatementSetter(PreparedStatement ps, File file) {
        try {
            ps.setBytes(1, file.getFileContent());
        } catch (SQLException e) {
            System.out.println("Repository error FILE");
        }
        return ps;
    }

    @Override
    default PreparedStatement updatePreparedStatementSetter(PreparedStatement ps, File file) {
        try {
            preparedStatementSetter(ps, file);
            ps.setInt(2, file.getId());
        } catch (SQLException e) {
            System.out.println("Repository error FILE_UPDATE");
        }
        return ps;
    }

    @Override
    default PreparedStatement deletePreparedStatementSetter(PreparedStatement ps, File file) {
        try {
            preparedStatementSetter(ps, file);
            ps.setInt(1, file.getId());
        } catch (SQLException e) {
            System.out.println("Repository error FILE_DELETE");
        }
        return ps;
    }

    @Override
    default String getUpdateColunmTemplate(){
        return "file_content = ?";
    }
}
