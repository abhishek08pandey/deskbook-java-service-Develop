package com.onerivet.deskbook.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.onerivet.deskbook.models.entity.Employee;
import com.onerivet.deskbook.models.entity.ModeOfWork;
import com.onerivet.deskbook.models.payload.EmployeeDto;
import com.onerivet.deskbook.models.payload.ModeOfWorkDto;
import com.onerivet.deskbook.repository.EmployeeRepo;
import com.onerivet.deskbook.repository.SeatConfigurationRepo;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

	@Mock
	private EmployeeRepo employeeRepo;
	
	@InjectMocks
	private EmployeeServiceImpl employeeServiceImpl;
	
	private EmployeeDto employeeDto;
	
	
	private Employee employee;
	
	@Mock
	private ModelMapper modelMapper;
	
	@Mock
	private ModeOfWork modeOfWork;
	
	@Mock
	private ModeOfWorkDto modeOfWorkDto;

	private SeatConfigurationRepo seatConfigurationRepo;

	
//	@BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//    }
	
	@Test
	void testGetAllEmployees() {
		
		 employeeServiceImpl = new EmployeeServiceImpl(employeeRepo, seatConfigurationRepo, null, null);
		
		Mockito.mock(Employee.class);
		Mockito.mock(ModeOfWork.class);
		
		
		
		Employee employee1 = Employee.builder()
			.id("ABHI123")
			.emailId("abhi@gmail.com")
			.firstName("abhishek")
			.lastName("pandey")
			.phoneNumber("9876543215")
			.build();
		
		Employee employee2 = Employee.builder()
				.id("Shek123")
				.emailId("shek@gmail.com")
				.firstName("shek")
				.lastName("pandey")
				.phoneNumber("9976543215")
				.build();
		
// Here we set value inside list of Employee		
		List<Employee> employees = Arrays.asList(employee1, employee2);
		
		when(employeeRepo.findAll()).thenReturn(employees);
		
		
		Mockito.mock(Employee.class);
		Mockito.mock(ModeOfWorkDto.class);
		
		EmployeeDto employeeDto1 = EmployeeDto.builder()
				.id("ABHI123")
				.emailId("abhi@gmail.com")
				.firstName("abhishek")
				.lastName("pandey")
				.phoneNumber("9876543215")
				.build();
		
		EmployeeDto employeeDto2 = EmployeeDto.builder()
				.id("Shek123")
				.emailId("shek@gmail.com")
				.firstName("shek")
				.lastName("pandey")
				.phoneNumber("9976543215")
				.build();
		
		
		 
		when(modelMapper.map(employee1, EmployeeDto.class)).thenReturn(employeeDto1);
		when(modelMapper.map(employee2, EmployeeDto.class)).thenReturn(employeeDto2);
		
// Here we set value inside list of EmployeeDto		
		
		List<EmployeeDto> result = employeeServiceImpl.getAllEmployees();

// Check modelMapper		
		List<EmployeeDto> expected = employees.stream().map(dto->modelMapper.map(dto, EmployeeDto.class)).collect(Collectors.toList());
		  
		System.out.println(expected);
		//assertThat(result).containsExactly(employeeDto1, employeeDto2);
		assertEquals(expected, result);
		
		fail("Not yet implemented");
	}

	@Test
	void testGetEmployeeById() {
		 fail("Not yet implemented");
	}

	@Test
	void testUpdateEmpById() {
		fail("Not yet implemented");
	}

}
