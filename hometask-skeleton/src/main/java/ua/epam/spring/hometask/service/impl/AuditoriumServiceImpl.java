package ua.epam.spring.hometask.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import ua.epam.spring.hometask.dao.jdbctemplate.AuditoriumJDBCTemplate;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.service.AuditoriumService;

public class AuditoriumServiceImpl implements AuditoriumService {

	@Autowired
	AuditoriumJDBCTemplate auditoriumJDBCTemplate;

	Set<Auditorium> auditoriums;

	@Autowired
	public AuditoriumServiceImpl(Set<Auditorium> auditoriums) {
		this.auditoriums = auditoriums;
	}

	@Override
	public Set<Auditorium> getAll() {
		return auditoriumJDBCTemplate.getAll();
	}

	@Override
	public Auditorium getByName(String name) {
		return auditoriumJDBCTemplate.getByName(name);
	}

}
