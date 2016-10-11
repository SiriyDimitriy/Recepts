package com.anna.recept.repository.impl;

import com.anna.recept.persist.Recept;
import com.anna.recept.repository.IReceptRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Getter
@Repository
public class ReceptRepository implements IReceptRepository {

    @Autowired
    private JdbcTemplate jdbc;

    private static final String FIND_BY_DEPART = "SELECT * FROM recept WHERE depart_id = ?";
    private static final String FIND_BY_NAME = "SELECT * FROM recept WHERE name = ?";
    private static final String FIND_FILE = "SELECT file FROM recept WHERE id = ?";
    private static final String UPDATE_FILE = "UPDATE recept SET file = ? WHERE id = ?";
    private static final String FIND_BY_TAG = "SELECT * FROM recept r, category c WHERE c.recept_id=r.id AND c.tag_id=?";


    @Override
    public List<Recept> findByDepart(final Integer departId) {
        return getFoundList(FIND_BY_DEPART, ps -> ps.setInt(1, departId));
    }

    @Override
    public Recept findByName(final String name) {
        return getFoundEntity(FIND_BY_NAME, ps -> ps.setString(1, name));
    }

    @Override
    public byte[] findFile(Integer receptId) {
        return getFile(FIND_FILE, ps -> ps.setInt(1, receptId), "file");
    }

    @Override
    public void updateFile(byte[] file, Integer receptId) {
        getJdbc().update(UPDATE_FILE, ps -> {
            ps.setBytes(1, file);
            ps.setInt(2, receptId);
        });
    }

    @Override
    public List<Recept> findByTag(Integer tagId) {
        return getFoundList(FIND_BY_TAG, ps -> ps.setInt(1, tagId));
    }

}
