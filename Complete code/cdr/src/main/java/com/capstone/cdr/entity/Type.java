package com.capstone.cdr.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
public class Type {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String type;
	private int rate;
}
