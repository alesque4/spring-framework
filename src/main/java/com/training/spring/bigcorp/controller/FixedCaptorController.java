package com.training.spring.bigcorp.controller;

import com.training.spring.bigcorp.model.Captor;
import com.training.spring.bigcorp.model.FixedCaptor;
import com.training.spring.bigcorp.model.Site;
import com.training.spring.bigcorp.repository.CaptorDao;
import com.training.spring.bigcorp.repository.MeasureDao;
import com.training.spring.bigcorp.repository.SiteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
        Captor newCaptor = new FixedCaptor("Nouveau capteur", site, null);
        return new ModelAndView("captor")
                .addObject("site", site)
                .addObject("captor",newCaptor);
    }

    @GetMapping("/{captorId}")
    public ModelAndView update(@PathVariable String siteId, @PathVariable String captorId){
        Site site = siteDao.findById(siteId).orElseThrow(IllegalArgumentException::new);
        Captor captor = captorDao.findById(captorId).orElseThrow(IllegalArgumentException::new);
        return new ModelAndView("captor")
                .addObject("site", site)
                .addObject("captor", captor);
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView save(@PathVariable String siteId, FixedCaptor captor) {
        Site site = siteDao.findById(siteId).orElseThrow(IllegalArgumentException::new);
        FixedCaptor captorToPersist;
        if (captor.getId() == null) {
            captorToPersist = new FixedCaptor(captor.getName(), site,
                    captor.getDefaultPowerInWatt());
        } else {
            captorToPersist = (FixedCaptor) captorDao.findById(captor.getId())
                    .orElseThrow(IllegalArgumentException::new);
            captorToPersist.setName(captor.getName());
            captorToPersist.setDefaultPowerInWatt(captor.getDefaultPowerInWatt());
        }
        captorDao.save(captorToPersist);
        return new ModelAndView("sitecreate").addObject("site", site);
    }

    @PostMapping("/{captorId}/delete")
    public ModelAndView delete(@PathVariable String siteId, @PathVariable String captorId) {
        // Il faut supprimer les mesures avant de supprimer le capteur
        Site site = siteDao.findById(siteId).orElseThrow(IllegalArgumentException::new);
        Captor captor = captorDao.findById(captorId).orElseThrow(IllegalArgumentException::new);
        measureDao.deleteByCaptorId(captorId);
        captorDao.deleteById(captorId);
        return new ModelAndView("sitecreate").addObject("site", site);
    }

}
