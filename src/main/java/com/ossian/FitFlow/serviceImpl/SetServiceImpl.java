package com.ossian.FitFlow.serviceImpl;

import com.ossian.FitFlow.repository.SetRepository;
import com.ossian.FitFlow.service.SetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetServiceImpl implements SetService {
    @Autowired
    private SetRepository setRepository;


}
