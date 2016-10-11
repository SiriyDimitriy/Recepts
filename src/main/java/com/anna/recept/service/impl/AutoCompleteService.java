package com.anna.recept.service.impl;

import com.anna.recept.autocomplete.IPrefixMatcher;
import com.anna.recept.autocomplete.PrefixMatcher;
import com.anna.recept.service.IAutoCompleteService;
import com.anna.recept.service.IIngridientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutoCompleteService implements IAutoCompleteService {

    private IPrefixMatcher matcher = new PrefixMatcher();
    @Autowired
    private IIngridientService ingService;

    @Override
    public List<String> showIngridients(String prefix) {
        //if it no order use sorted list
        List<String> words = new ArrayList<>();
        Iterator<String> iterator = matcher.wordsWithPrefix(prefix, 40).iterator();
        while(iterator.hasNext()) {
            words.add(iterator.next().replace('1', ' '));
        }
        return words;
    }

    @Override
    public void addIngridient(String ingridient) {
        matcher.add(ingridient);
    }

    @PostConstruct
    private void fillVocabulary() {
        matcher.add(ingService.showAllIngridients().stream()
                .map((ing)->ing.getName())
                .map((name)->name.replace(' ', '1'))
                .collect(Collectors.toList()));
    }
}
