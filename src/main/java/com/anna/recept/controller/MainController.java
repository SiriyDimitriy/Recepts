package com.anna.recept.controller;

import com.anna.recept.dto.DepartDto;
import com.anna.recept.dto.IngridientDto;
import com.anna.recept.dto.ProportionDto;
import com.anna.recept.dto.ReceptDto;
import com.anna.recept.facade.IReceptFullFacade;
import com.anna.recept.service.IDepartService;
import com.anna.recept.service.IIngridientService;
import com.anna.recept.service.IProportionService;
import com.anna.recept.service.IReceptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private IReceptService receptService;

    @Autowired
    private IDepartService departServ;

    @Autowired
    private IReceptFullFacade receptFacade;

    @RequestMapping(value = {"/departs.req"}, method = RequestMethod.GET,
            headers = "Accept=application/json")
    @ResponseBody
    public List<DepartDto> departsList() {
        return departServ.findAllDepartments();
    }


    @RequestMapping(value = {"/recept_list.req"}, method = RequestMethod.GET,
            headers = "Accept=application/json")
    @ResponseBody
    public List<ReceptDto> receptList(@RequestParam("departId") Integer departId) {
        return receptService.showReceptDtos(departId);
    }

    @RequestMapping(value = {"/recept.req"}, method = RequestMethod.GET,
            headers = "Accept=application/json")
    @ResponseBody
    public ReceptDto recept(@RequestParam("receptId") Integer receptId) {
        return receptFacade.showRecept(receptId);
    }

    @RequestMapping(value = {"/recept.req"}, method = RequestMethod.DELETE,
            headers = "Accept=application/json")
    @ResponseBody
    public void receptDelete(@RequestParam("receptId") Integer receptId) {
        receptFacade.deleteRecept(receptId);
    }


    @RequestMapping(value = {"/recept_unique.req"}, method = RequestMethod.POST,
            headers = "Accept=application/json")
    @ResponseBody
    public Integer saveUniqueRecept(@RequestBody ReceptDto recept) {
        return receptService.saveWithUniqueName(recept);
    }

    @RequestMapping(value = {"/recept.req"}, method = RequestMethod.POST,
            headers = "Accept=application/json")
    @ResponseBody
    public Integer saveRecept(@RequestBody ReceptDto recept) {
        return receptService.saveRecept(recept);
    }

    @RequestMapping(value = {"/file.req"}, method = RequestMethod.POST)
    @ResponseBody
    public void saveReceptFile(Integer receptId, MultipartFile file, HttpServletResponse response) throws IOException {
        byte[] savedFile = receptService.saveFile(file.getBytes(), receptId);
        OutputStream outStream = response.getOutputStream();
        outStream.write(savedFile);
        outStream.close();
    }

    @RequestMapping(value = {"/file.req"}, method = RequestMethod.GET)
    @ResponseBody
    public void getReceptFile(@RequestParam("receptId") Integer receptId, HttpServletResponse response) throws IOException {
        byte[] file = receptService.getFile(receptId);
        if (file != null) {
            OutputStream outStream = response.getOutputStream();
            outStream.write(file);
            outStream.close();
        }
    }

    @RequestMapping(value = {"/recept_list_tag.req"}, method = RequestMethod.GET,
            headers = "Accept=application/json")
    @ResponseBody
    public List<ReceptDto> receptListByTag(@RequestParam("tagId") Integer tagId) {
        return receptService.showReceptsByTag(tagId);
    }

}
