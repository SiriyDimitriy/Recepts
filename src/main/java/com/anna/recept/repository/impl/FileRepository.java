package com.anna.recept.repository.impl;

import com.anna.recept.repository.IFileRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Getter
@Repository
public class FileRepository implements IFileRepository {

    @Autowired
    private JdbcTemplate jdbc;
}
