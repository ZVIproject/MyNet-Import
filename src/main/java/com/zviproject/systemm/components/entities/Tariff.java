package com.zviproject.systemm.components.entities;

import java.util.Optional;

/**
 * Entity for import tariff
 * 
 * @author zviproject
 *
 */
public class Tariff {

	private Integer id;

	private Double cost;

	private String name;

	private String condition;

	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = Optional.ofNullable(description).toString();
	}

}
