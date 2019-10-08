package com.daumsoft.test5.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.daumsoft.test5.model.dao.CaDAO;

@Service
public class CaServiceImpl implements CaService {
	@Inject
	CaDAO caDao;

	@Override
	public void getData() throws Exception {
		
	}
}
