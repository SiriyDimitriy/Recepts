package com.anna.recept.facade.impl;

import com.anna.recept.dto.DepartDto;
import com.anna.recept.dto.ReceptDto;
import com.anna.recept.persist.Recept;
import com.anna.recept.service.ICategoryService;
import com.anna.recept.service.IDepartService;
import com.anna.recept.service.IDetailService;
import com.anna.recept.service.IProportionService;
import com.anna.recept.service.IReceptService;
import com.anna.recept.service.IReferenceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReceptFullFacadeTest {

    @InjectMocks
    private ReceptFullFacade sut;
    @Mock
    private IReceptService receptServ;
    @Mock
    private IProportionService propServ;
    @Mock
    private IDetailService detServ;
    @Mock
    private IReferenceService refServ;
    @Mock
    private ICategoryService categoryServ;
    @Mock
    private IDepartService departServ;

    @Before
    public void setUp() {
        when(receptServ.getRecept(anyInt())).thenReturn(new ReceptDto());
        when(propServ.findReceptsProportions(anyInt())).thenReturn(new ArrayList<>());
        when(detServ.findReceptsDetails(anyInt())).thenReturn(new ArrayList<>());
        when(refServ.findReceptsReferences(anyInt())).thenReturn(new ArrayList<>());
        when(departServ.findDepartmentByReceptId(anyInt())).thenReturn(new DepartDto());
        when(categoryServ.findTagsByRecept(anyInt())).thenReturn(new ArrayList<>());
    }

    @Test
    public void shouldShowRecept() {
        int id = 20;
        ReceptDto recept = sut.showRecept(id);

        assertNotNull(recept);
        assertNotNull(recept.getDetails());
        assertNotNull(recept.getProportions());
        assertNotNull(recept.getReferences());
        assertNotNull(recept.getDepartId());
        assertNotNull(recept.getTags());
    }

    @Test
    public void shouldDeleteRecept() {
        int id = 20;
        sut.deleteRecept(id);

        verify(propServ).deleteProportions(id);
        verify(detServ).deleteDetails(id);
        verify(refServ).deleteReceptReferences(id);
        verify(categoryServ).deleteReceptsCategory(id);
        verify(receptServ).deleteRecept(id);
    }

    private List<Recept> constructReceptList() {
        List<Recept> receptList = new ArrayList<>();
        receptList.add(new Recept());
        receptList.add(new Recept());
        return receptList;
    }

}