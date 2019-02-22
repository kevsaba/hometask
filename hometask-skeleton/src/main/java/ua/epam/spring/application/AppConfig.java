package ua.epam.spring.application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import ua.epam.spring.hometask.dao.jdbctemplate.AirDateJDBCTemplate;
import ua.epam.spring.hometask.dao.jdbctemplate.AuditoriumJDBCTemplate;
import ua.epam.spring.hometask.dao.jdbctemplate.EventJDBCTemplate;
import ua.epam.spring.hometask.dao.jdbctemplate.TicketJDBCTemplate;
import ua.epam.spring.hometask.dao.jdbctemplate.UserJDBCTemplate;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.manager.BirthdayDiscountStrategy;
import ua.epam.spring.hometask.manager.TenTicketDiscountStrategy;
import ua.epam.spring.hometask.service.AuditoriumService;
import ua.epam.spring.hometask.service.DiscountService;
import ua.epam.spring.hometask.service.DiscountStrategy;
import ua.epam.spring.hometask.service.impl.AuditoriumServiceImpl;
import ua.epam.spring.hometask.service.impl.DiscountServiceImpl;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan({ "ua.epam.spring", "org.baeldung.jdbc" })
@PropertySource("classpath:auditorium.properties")
public class AppConfig {

	@Autowired
	private Environment env;

	@Bean
	public Application getApplication() {
		return new Application();
	}

	@Bean
	public DiscountStrategy getBdayDiscountStrategy() {
		return new BirthdayDiscountStrategy();
	}

	@Bean
	public DiscountStrategy getTenthDiscountStrategy() {
		return new TenTicketDiscountStrategy();
	}

	@Bean
	public List<DiscountStrategy> getDiscountStrategies() {
		return new ArrayList<DiscountStrategy>(List.of(getBdayDiscountStrategy(), getTenthDiscountStrategy()));
	}

	@Bean
	public DiscountService getDiscountService() {
		return new DiscountServiceImpl(getDiscountStrategies());
	}

	@Bean
	public Auditorium getAuditorium1() {
		return createAuditorium("1");
	}

	@Bean
	public Auditorium getAuditorium2() {
		return createAuditorium("2");
	}

	@Bean
	public Auditorium getAuditorium3() {
		return createAuditorium("3");
	}

	@Bean
	public Auditorium getAuditorium4() {
		return createAuditorium("4");
	}

	@Bean
	public AuditoriumService getAuditoriumService() {
		Set<Auditorium> auditoriums = new HashSet<>(Arrays.asList(getAuditorium1(), getAuditorium2(), getAuditorium3(), getAuditorium4()));
		return new AuditoriumServiceImpl(auditoriums);
	}

	private Auditorium createAuditorium(String numb) {
		Auditorium auditorium = new Auditorium();
		auditorium.setName(env.getProperty("auditorium." + numb + ".name"));
		auditorium.setNumberOfSeats(Long.valueOf(env.getProperty("auditorium." + numb + ".numberOfSeats")));
		List<String> vips = Arrays.asList(env.getProperty("auditorium." + numb + ".vipSeats").split(","));
		Set<Long> vipsLong = new HashSet<>();
		vips.stream().forEach(s -> vipsLong.add(Long.valueOf(s)));
		auditorium.setVipSeats(vipsLong);
		return auditorium;
	}

	@Bean
	public DataSource mysqlDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/kevin?autoReconnect=true&useSSL=false");
		dataSource.setUsername("root");
		dataSource.setPassword("root");

		return dataSource;
	}

	@Bean
	public UserJDBCTemplate getUserJDBCTemplate() {
		return new UserJDBCTemplate(mysqlDataSource());
	}

	@Bean
	public TicketJDBCTemplate getTicketJDBCTemplate() {
		return new TicketJDBCTemplate(mysqlDataSource());
	}

	@Bean
	public EventJDBCTemplate getEventJDBCTemplate() {
		return new EventJDBCTemplate(mysqlDataSource());
	}

	@Bean
	public AuditoriumJDBCTemplate getAuditoriumJDBCTemplate() {
		return new AuditoriumJDBCTemplate(mysqlDataSource());
	}

	@Bean
	public AirDateJDBCTemplate getAirDateJDBCTemplate() {
		return new AirDateJDBCTemplate(mysqlDataSource());
	}

}
