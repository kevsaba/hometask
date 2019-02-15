package ua.epam.spring.hometask.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.service.AuditoriumService;

public class AuditoriumServiceImpl implements AuditoriumService{

	Set<Auditorium> auditoriums ;

	@Autowired
	public AuditoriumServiceImpl(Set<Auditorium> auditoriums) {
		this.auditoriums = auditoriums;
	}
	
	@Override
	public Set<Auditorium> getAll() {
		return auditoriums;
	}

	@Override
	public Auditorium getByName(String name) {
		return auditoriums.stream().filter(a->name.equals(a.getName())).findFirst().get();
	}

}
