package ua.epam.spring.hometask.domain;

import javax.persistence.Id;

/**
 * @author Yuriy_Tkach
 */
public class DomainObject {

	@Id
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
