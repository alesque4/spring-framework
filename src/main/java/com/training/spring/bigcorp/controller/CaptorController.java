package com.training.spring.bigcorp.controller;

import com.training.spring.bigcorp.controller.dto.CaptorDto;
import com.training.spring.bigcorp.exception.NotFoundException;
import com.training.spring.bigcorp.model.*;
import com.training.spring.bigcorp.repository.CaptorDao;
import com.training.spring.bigcorp.repository.MeasureDao;
import com.training.spring.bigcorp.repository.SiteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/sites/{siteId}/captors")
@Transactional
public class CaptorController {

    @Autowired
    private CaptorDao captorDao;

    @Autowired
    private SiteDao siteDao;

    @Autowired
    private MeasureDao measureDao;

    public CaptorController(CaptorDao captorDao, SiteDao siteDao) {
        this.captorDao = captorDao;
        this.siteDao = siteDao;
    }

    private CaptorDto toDto(Captor captor) {
        if (captor instanceof FixedCaptor) {
            return new CaptorDto(captor.getSite(), (FixedCaptor) captor);
        }
        if (captor instanceof SimulatedCaptor) {
            return new CaptorDto(captor.getSite(), (SimulatedCaptor) captor);
        }
        if (captor instanceof RealCaptor) {
            return new CaptorDto(captor.getSite(), (RealCaptor) captor);
        }
        throw new IllegalStateException("Captor type not managed by app");
    }

    private List<CaptorDto> toDtos(List<Captor> captors) {
        return captors.stream()
                .map(this::toDto)
                .sorted(Comparator.comparing(CaptorDto::getName))
                .collect(Collectors.toList());
    }

    @GetMapping
    public ModelAndView findAll(@PathVariable String siteId) {
        return new ModelAndView("captors")
                .addObject("captors", toDtos(captorDao.findBySiteId(siteId)));
    }

    @GetMapping("/{id}")
    public ModelAndView findById(@PathVariable String siteId, @PathVariable String id) {
        Captor captor =
                captorDao.findById(id).orElseThrow(NotFoundException::new);
        return new ModelAndView("captor").addObject("captor", toDto(captor));
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/create")
    public ModelAndView create(@PathVariable String siteId) {
        Site site =
                siteDao.findById(siteId).orElseThrow(NotFoundException::new);
        return new ModelAndView("captor")
                .addObject("captor",
                        new CaptorDto(site, new FixedCaptor(null, site, null)));
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView save(CaptorDto captorDto) {
        Site site = siteDao.findById(captorDto.getSiteId()).orElseThrow(NotFoundException::new);
        Captor captor = captorDto.toCaptor(site);
        captorDao.save(captor);
        return new ModelAndView("captors")
                .addObject("captors", toDtos(captorDao.findBySiteId(captorDto.getSiteId())));
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable String siteId, @PathVariable String id) {
        measureDao.deleteByCaptorId(id);
        captorDao.deleteById(id);
        return new ModelAndView("site")
                .addObject("site",
                        siteDao.findById(siteId).orElseThrow(NotFoundException::new));
    }
}