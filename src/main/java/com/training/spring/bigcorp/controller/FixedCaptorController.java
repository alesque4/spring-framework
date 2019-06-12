package com.training.spring.bigcorp.controller;

import com.training.spring.bigcorp.model.Captor;
import com.training.spring.bigcorp.model.Site;
import com.training.spring.bigcorp.repository.CaptorDao;
import com.training.spring.bigcorp.repository.MeasureDao;
import com.training.spring.bigcorp.repository.SiteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/sites/{siteId}/captors/FIXED")
@Transactional
public class FixedCaptorController {

    @Autowired
    private SiteDao siteDao;

    @Autowired
    private CaptorDao captorDao;

    @Autowired
    private MeasureDao measureDao;

    @GetMapping("/create")
    public ModelAndView create(@PathVariable String siteId){
        Site site = siteDao.findById(siteId).orElseThrow(IllegalArgumentException::new);
        return new ModelAndView(); //TODO
    }

    @GetMapping("/{captorId}")
    public ModelAndView update(@PathVariable String siteId, @PathVariable String captorId){
        Site site = siteDao.findById(siteId).orElseThrow(IllegalArgumentException::new);
        Captor captor = captorDao.findById(captorId).orElseThrow(IllegalArgumentException::new);
        return new ModelAndView(); //TODO
    }

}
