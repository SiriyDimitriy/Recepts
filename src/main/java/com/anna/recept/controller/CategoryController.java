package com.anna.recept.controller;

import com.anna.recept.dto.TagDto;
import com.anna.recept.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @RequestMapping(value = {"/category_save.req"}, method = RequestMethod.GET,
            headers = "Accept=application/json")
    @ResponseBody
    public Integer saveTag(@RequestParam("receptId") Integer receptId,
                           @RequestParam("tagId") Integer tagId) {
        return categoryService.saveCategory(receptId, tagId);
    }

    @RequestMapping(value = {"/category_del.req"}, method = RequestMethod.GET,
            headers = "Accept=application/json")
    @ResponseBody
    public void deleteTag(@RequestParam("receptId") Integer receptId,
                          @RequestParam("tagId") Integer tagId) {
        categoryService.deleteTagFromRecept(receptId, tagId);
    }

    @RequestMapping(value = {"/recept_tags.req"}, method = RequestMethod.GET,
            headers = "Accept=application/json")
    @ResponseBody
    public List<TagDto> fetchTags(@RequestParam("receptId") Integer receptId) {
        return categoryService.findTagsByRecept(receptId);
    }
}
