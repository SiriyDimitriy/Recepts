package com.anna.recept.service;

import java.util.List;

public interface IAutoCompleteService {
    List<String> showIngridients(String prefix);
    void addIngridient(String ingridient);
}
