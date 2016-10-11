package com.anna.recept.service.impl;

import com.anna.recept.dto.DetailDto;
import com.anna.recept.persist.Detail;
import com.anna.recept.repository.IDetailRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DetailServiceTest {

    @InjectMocks
    private DetailService sut;

    @Mock
    private IDetailRepository detRep;


    private static Integer DETAIL_LIST_SIZE = 1;
    private static Integer RECEPT_ID = 34;
    private static Integer DETAIL_ID = 4;

    @Before
    public void setUp() {
        when(detRep.findByRecept(anyInt())).thenReturn(createDetailList(DETAIL_LIST_SIZE));
        when(detRep.save(any())).thenReturn(DETAIL_ID);
    }

    @Test
    public void shouldFindReceptsDetails() {
        List<DetailDto> detailsList = sut.findReceptsDetails(RECEPT_ID);
        assertNotNull(detailsList);

        verify(detRep).findByRecept(RECEPT_ID);
    }

    @Test
    public void shouldDeleteDetails() {
        sut.deleteDetails(RECEPT_ID);

        verify(detRep).findByRecept(RECEPT_ID);
        verify(detRep, times(DETAIL_LIST_SIZE)).delete(any());
    }

    @Test
    public void shouldSaveDetail() {
        Integer detId = sut.saveDetail(new DetailDto());

        verify(detRep).save(any());
        assertThat(detId, is(DETAIL_ID));
    }

    @Test
    public void shouldUpdateProportion() {
        Integer detId = sut.saveDetail(new DetailDto());

        verify(detRep).save(any());
        assertThat(detId, is(DETAIL_ID));
    }

    @Test
    public void shouldDeleteProportion() {
        sut.deleteDetail(DETAIL_ID);

        verify(detRep).findById(DETAIL_ID);
        verify(detRep).delete(any());
    }

    @Test
    public void shouldSaveFile() {
        byte[] mas = {};
        sut.saveFile(mas, DETAIL_ID);
        verify(detRep).updateFile(mas, DETAIL_ID);
        verify(detRep).findFile(DETAIL_ID);
    }

    @Test
    public void shouldGetFile() {
        sut.getFile(DETAIL_ID);

        verify(detRep).findFile(DETAIL_ID);
    }

    private List<Detail> createDetailList(int detailNumber) {
        List<Detail> detailList = new ArrayList<>();
        for (int i = 0; i < detailNumber; i++) {
            detailList.add(createDetail());
        }
        return detailList;
    }

    private Detail createDetail() {
        Detail detail = new Detail();
        detail.setReceptId(RECEPT_ID);
        return detail;
    }

}