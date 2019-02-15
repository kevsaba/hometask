package ua.epam.spring.application;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.epam.spring.hometask.dao.UserDAO;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

@Aspect
@Component
public class AppAspect {
	
	@Autowired
	private UserDAO userDao;
	
	private Map<String,Integer> counter = new HashMap<>();
	
	private Map<User,Map<Integer,Integer>> userDiscountMapToMapCounter = new HashMap<>();
	
	@After("execution(* ua.epam.spring.hometask.domain.Event.getName())")
	public void countEventNameMethodCalled(JoinPoint jp) {
		updateCounter(jp.getSignature().getName());
	}
	
	@After("execution(* ua.epam.spring.hometask.domain.Event.getBasePrice())")
	public void countEventBasePriceMethodCalled(JoinPoint jp) {
		updateCounter(jp.getSignature().getName());
	}
	
	@After("execution(* ua.epam.spring.hometask.service.impl.BookingServiceImpl.bookTickets(..))")
	public void countBookTicketsMethodCalled(JoinPoint jp) {
		updateCounter(jp.getSignature().getName());
	}
	
	@Around("execution(* ua.epam.spring.hometask.service.impl.DiscountServiceImpl.getDiscount(..))")
	public Object countBookTicketsLuckyMethodCalled(ProceedingJoinPoint  jp) throws Throwable {
		boolean lucky = Math.random() >= 0.8;
		Object retValue = jp.proceed();
		if(lucky) {
			retValue = 100;
		}
		return retValue;
	}

	@AfterReturning(pointcut="execution(* ua.epam.spring.hometask.service.impl.DiscountServiceImpl.getDiscount(..))", returning="result2")
	public void countDiscount4UserMethodCalled(JoinPoint jp, Object result2) {
		byte result = (byte) result2;
		for(Object arg:jp.getArgs()) {
			if(arg instanceof User) {
				User user = (User)arg;
				Map<Integer,Integer> discuountCounter = updateDiscountMapCounter(result, user);
				discuountCounter.put(Integer.valueOf(result),discuountCounter.get(Integer.valueOf(result))+1);
				userDiscountMapToMapCounter.put(user,discuountCounter);
				break;
			}
		}
	}

	private Map<Integer, Integer> updateDiscountMapCounter(byte result, User user) {
		Map<Integer,Integer> discuountCounter = new HashMap<>();
		if(!userDiscountMapToMapCounter.containsKey(user)) {
			discuountCounter.put(Integer.valueOf(result), 0);
			userDiscountMapToMapCounter.put(user,discuountCounter);
		}
		discuountCounter=userDiscountMapToMapCounter.get(user);
		if(!discuountCounter.containsKey(Integer.valueOf(result))) {
			discuountCounter.put(Integer.valueOf(result), 0);
		}
		return discuountCounter;
	}
	

	private void updateCounter(String methodAccessByName) {
		if(!counter.containsKey(methodAccessByName)) {
			counter.put(methodAccessByName, 0);
		}
		counter.put(methodAccessByName, counter.get(methodAccessByName)+1);
	}
	
}
