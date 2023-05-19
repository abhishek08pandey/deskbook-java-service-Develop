package com.onerivet.deskbook.models.response;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NotEmpty
public class GenericResponse<T> {
	
	private T data;
	private String error;
}
