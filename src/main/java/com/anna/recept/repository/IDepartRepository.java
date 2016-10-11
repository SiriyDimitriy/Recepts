package com.anna.recept.repository;

import com.anna.recept.persist.Dapart;
import com.anna.recept.persist.Detail;
import com.anna.recept.persist.Recept;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface IDepartRepository extends IJdbcTemplateCrudOperations<Dapart>{
    
    @Override
    default Dapart typeMapper(ResultSet rs) throws SQLException{
        Dapart dapart = new Dapart();
        dapart.setId(rs.getInt("id"));
        dapart.setName(rs.getString("name"));
        return dapart;
    }
    
    @Override
    default String getTable(){
        return "dapart";
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
        return "name";
    }

    @Override
    default PreparedStatement preparedStatementSetter(PreparedStatement ps, Dapart dapart) {
        try {
            ps.setString(1, dapart.getName());
        } catch (SQLException e) {
            System.out.println("Repository error DEPART");
        }
        return ps;
    }

    @Override
    default PreparedStatement updatePreparedStatementSetter(PreparedStatement ps, Dapart dapart) {
        try {
            preparedStatementSetter(ps, dapart);
            ps.setInt(2, dapart.getId());
        } catch (SQLException e) {
            System.out.println("Repository error DEPART_UPDATE");
        }
        return ps;
    }

    @Override
    default PreparedStatement deletePreparedStatementSetter(PreparedStatement ps, Dapart dapart) {
        try {
            preparedStatementSetter(ps, dapart);
            ps.setInt(1, dapart.getId());
        } catch (SQLException e) {
            System.out.println("Repository error DEPART_DELETE");
        }
        return ps;
    }

    @Override
    default String getUpdateColunmTemplate(){
        return "name = ?";
    }

    Dapart findByRecept(Integer receptId);
}
