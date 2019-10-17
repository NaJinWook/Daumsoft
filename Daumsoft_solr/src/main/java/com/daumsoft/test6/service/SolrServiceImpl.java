package com.daumsoft.test6.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.daumsoft.test6.model.dao.SolrDAO;

@Service
public class SolrServiceImpl implements SolrService {
	@Inject
	SolrDAO solrDao;
}
