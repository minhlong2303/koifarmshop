package com.example.koifarm.service;

import com.example.koifarm.model.KoiResponse;
import com.example.koifarm.repository.KoiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class KoiPageService {

    @Autowired
    KoiRepository koiRepository;

    public KoiResponse getPageKoi(int page, int size){
        Page koiPage = koiRepository.findKoiByIsDeletedFalse(PageRequest.of(page, size));

        KoiResponse koiResponse = new KoiResponse();
        koiResponse.setTotalPages(koiPage.getTotalPages());
        koiResponse.setContent(koiPage.getContent());
        koiResponse.setPageNumber(koiPage.getNumber());
        koiResponse.setTotalElements(koiPage.getTotalElements());
        return koiResponse;
    }

}
