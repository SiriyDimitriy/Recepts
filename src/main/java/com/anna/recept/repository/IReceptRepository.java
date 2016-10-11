package com.anna.recept.repository;

import com.anna.recept.persist.Recept;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface IReceptRepository extends IJdbcTemplateCrudOperations<Recept> {

    @Override
    default Recept typeMapper(ResultSet rs) throws SQLException {
        Recept recept = new Recept();
        recept.setId(rs.getInt("id"));
        recept.setName(rs.getString("name"));
        recept.setText(rs.getString("text"));
        recept.setDepartId(rs.getInt("depart_id"));
        return recept;
    }

    @Override
    default String getTable() {
        return "recept";
    }

    @Override
    default String getPkAsId() {
        return "id";
    }

    @Override
    default String getFieldTemplateAsString() {
        return "?,?,?";
    }

    @Override
    default String getFieldNamesAsString() {
        return "text, name, depart_id";
    }

    @Override
    default PreparedStatement preparedStatementSetter(PreparedStatement ps, Recept recept) {
        try {
            ps.setString(1, recept.getText());
            ps.setString(2, recept.getName());
            ps.setInt(3, recept.getDepartId());
        } catch (SQLException e) {
            System.out.println("Repository error RECEPT");
        }
        return ps;
    }

    @Override
    default PreparedStatement updatePreparedStatementSetter(PreparedStatement ps, Recept recept) {
        try {
            preparedStatementSetter(ps, recept);
            ps.setInt(4, recept.getId());
        } catch (SQLException e) {
            System.out.println("Repository error RECEPT_UPDATE");
        }
        return ps;
    }

    @Override
    default PreparedStatement deletePreparedStatementSetter(PreparedStatement ps, Recept recept) {
        try {
            preparedStatementSetter(ps, recept);
            ps.setInt(1, recept.getId());
        } catch (SQLException e) {
            System.out.println("Repository error RECEPT_DELETE");
        }
        return ps;
    }

    @Override
    default String getUpdateColunmTemplate(){
        return "text = ?, name = ?, depart_id = ?";
    }

    List<Recept> findByDepart(final Integer departId);
    Recept findByName(final String name);
    byte[] findFile(final Integer receptId);
    void updateFile(final byte[] file, final Integer receptId);
    List<Recept> findByTag(final Integer tagId);
}
