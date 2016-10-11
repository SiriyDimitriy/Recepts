/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.recept.facade;

import com.anna.recept.dto.ReceptDto;
import java.util.List;

public interface IReceptFullFacade {
    ReceptDto showRecept(Integer receptId);
    void deleteRecept(Integer receptId);
}
