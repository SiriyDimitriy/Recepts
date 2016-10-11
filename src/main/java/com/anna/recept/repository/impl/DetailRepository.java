package com.anna.recept.repository.impl;

import com.anna.recept.persist.Detail;
import com.anna.recept.repository.IDetailRepository;
import java.util.List;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Getter
@Repository
public class DetailRepository implements IDetailRepository{
    
    @Autowired
    private JdbcTemplate jdbc;

    private static final String FIND_BY_RECEPT = "SELECT * FROM detail WHERE recept_id =?";
    private static final String FIND_FILE = "SELECT file FROM detail WHERE id = ?";
    private static final String UPDATE_FILE = "UPDATE detail SET file = ? WHERE id = ?";

    @Override
    public List<Detail> findByRecept(final Integer receptId) {
        return getFoundList(FIND_BY_RECEPT, ps->ps.setInt(1, receptId));
    }

    @Override
    public byte[] findFile(Integer detailId) {
        return getFile(FIND_FILE, ps  -> ps.setInt(1, detailId), "file");
    }

    @Override
    public void updateFile(byte[] file, Integer detailId) {
        getJdbc().update(UPDATE_FILE, ps -> {
            ps.setBytes(1, file);
            ps.setInt(2, detailId);
        });
    }

}
