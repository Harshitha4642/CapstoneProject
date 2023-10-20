package com.capstone.cdr.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserForm {
	private String name;
    private String password;
    private String passwordRepeat;

}