package com.training.spring.bigcorp.service;

import com.training.spring.bigcorp.model.Captor;
import com.training.spring.bigcorp.repository.CaptorDao;
import com.training.spring.bigcorp.service.measure.MeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CaptorServiceImpl implements CaptorService {

    @Autowired
    private CaptorDao captorDao;

    private MeasureService measureService;

    @Autowired
    public CaptorServiceImpl(@Qualifier("FixedMeasureService") MeasureService measureService) {
        this.measureService = measureService;
    }

    @Override
    public Set<Captor> findBySite(String siteId) {
        if (captorDao == null) {
            return new HashSet<>();
        }
        return captorDao.findBySiteId(siteId).stream().collect(Collectors.toSet());
    }

    public MeasureService getMeasureService() {
        return measureService;
    }

    public void setMeasureService(MeasureService measureService) {
        this.measureService = measureService;
    }

    public CaptorDao getCaptorDao() {
        return captorDao;
    }

    public void setCaptorDao(CaptorDao captorDao) {
        this.captorDao = captorDao;
    }
}
