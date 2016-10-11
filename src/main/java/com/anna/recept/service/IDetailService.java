package com.anna.recept.service;

import com.anna.recept.dto.DetailDto;
import com.anna.recept.persist.Detail;

import java.util.List;

public interface IDetailService {
    List<DetailDto> findReceptsDetails(Integer receptId);
    void deleteDetails(Integer receptId);
    int saveDetail(DetailDto detail);
    void deleteDetail(Integer id);
    byte[] saveFile(byte[] file, Integer detailId);
    byte[] getFile(Integer detailId);
}
