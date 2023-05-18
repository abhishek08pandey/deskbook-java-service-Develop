package com.onerivet.deskbook.services.impl;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.onerivet.deskbook.models.entity.Designation;
import com.onerivet.deskbook.models.payload.DesignationDto;
import com.onerivet.deskbook.repository.DesignationRepo;

@ExtendWith(MockitoExtension.class)
class DesignationServiceImplTest {

	@Mock private DesignationRepo designationRepo;
	
	@InjectMocks private DesignationServiceImpl designationServiceImpl;
	
	@Mock private Designation designation1;
	
	@Mock private DesignationDto designationDto;
	
	@Mock private ModelMapper modelMapper;
	
	@BeforeEach
	void setUp() {
		this.designationServiceImpl= new DesignationServiceImpl(this.designationRepo, modelMapper);
	}
	
	@Test
	void test() {
		
		Mockito.mock(Designation.class);
		Mockito.mock(DesignationDto.class);
		Mockito.mock(DesignationServiceImpl.class);
		
		
		designation1 = Designation.builder()
				.id(1)
				.designationName("BA")
				.build();
		
		designation1 = Designation.builder()
				.id(1)
				.designationName("BA")
				.build();
		
		when(this.designationRepo.findAll()).thenReturn(designation);
		
		when(modelMapper.map(designation, DesignationDto.class)).thenReturn(designationDto);
		
		   List<DesignationDto> designationDto = this.designationServiceImpl.getAllDesignations();
	
		   assertThat(designationDto.get(0)).isEqualTo(designation);
	}

}
