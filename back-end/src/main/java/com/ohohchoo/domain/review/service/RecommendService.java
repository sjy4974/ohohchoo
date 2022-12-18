package com.ohohchoo.domain.review.service;

import com.ohohchoo.domain.review.repository.RecommendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecommendService {

    private final RecommendRepository recommendRepository;


}
