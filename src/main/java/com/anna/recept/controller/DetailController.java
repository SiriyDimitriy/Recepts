package com.anna.recept.controller;

import com.anna.recept.dto.DetailDto;
import com.anna.recept.service.IDetailService;
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
public class DetailController {

    @Autowired
    private IDetailService detailService;

    @RequestMapping(value = {"/detail.req"}, method = RequestMethod.POST,
            headers = "Accept=application/json")
    @ResponseBody
    public Integer saveDetail(@RequestBody DetailDto detail) {
        return detailService.saveDetail(detail);
    }

    @RequestMapping(value = {"/detail.req"}, method = RequestMethod.DELETE,
            headers = "Accept=application/json")
    @ResponseBody
    public void deleteDetail(@RequestParam("detId") Integer detId) {
        detailService.deleteDetail(detId);
    }

    @RequestMapping(value = {"/details.req"}, method = RequestMethod.GET,
            headers = "Accept=application/json")
    @ResponseBody
    public List<DetailDto> fetchDetails(@RequestParam("receptId") Integer receptId) {
        return detailService.findReceptsDetails(receptId);
    }

    @RequestMapping(value = {"/detail_file.req"}, method = RequestMethod.POST)
    @ResponseBody
    public void saveDetailFile(Integer detailId, MultipartFile file, HttpServletResponse response) throws IOException {
        byte[] savedFile = detailService.saveFile(file.getBytes(), detailId);
        OutputStream outStream = response.getOutputStream();
        outStream.write(savedFile);
        outStream.close();
    }

    @RequestMapping(value = {"/detail_file.req"}, method = RequestMethod.GET)
    @ResponseBody
    public void getDetailFile(@RequestParam("detailId") Integer detailId, HttpServletResponse response) throws IOException {
        byte[] file = detailService.getFile(detailId);
        if (file != null) {
            OutputStream outStream = response.getOutputStream();
            outStream.write(file);
            outStream.close();
        }
    }

}
