package com.onerivet.deskbook.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.onerivet.deskbook.exception.ResourceNotFoundException;
import com.onerivet.deskbook.models.entity.Employee;
import com.onerivet.deskbook.models.entity.EmployeeWorkingDays;
import com.onerivet.deskbook.models.entity.SeatConfiguration;
import com.onerivet.deskbook.models.payload.EmployeeDto;
import com.onerivet.deskbook.models.payload.ProfileViewDto;
import com.onerivet.deskbook.models.payload.UpdateProfileDto;
import com.onerivet.deskbook.repository.EmployeeRepo;
import com.onerivet.deskbook.repository.EmployeeWorkingDaysRepo;
import com.onerivet.deskbook.repository.SeatConfigurationRepo;
import com.onerivet.deskbook.repository.WorkingDaysRepo;
import com.onerivet.deskbook.services.EmployeeService;
import com.onerivet.deskbook.util.ProfileMapper;
import com.onerivet.deskbook.util.UpdateSeatUtils;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Transactional
@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

	public EmployeeServiceImpl(EmployeeRepo employeeRepo) {
		
	}
	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private SeatConfigurationRepo seatConfigurationRepo;

	@Autowired
	private EmployeeWorkingDaysRepo employeeWorkingDaysRepo;

	@Autowired
	private WorkingDaysRepo workingDaysRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ProfileMapper profileMapper;

	@Autowired
	private UpdateSeatUtils updateSeatUtils;

	@Value("${image.upload.path}")
	String path;
	
	@Override 
	public List<EmployeeDto> getAllEmployees() {
		return this.employeeRepo.findAll().stream().map((employee) -> this.modelMapper.map(employee, EmployeeDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public ProfileViewDto getEmployeeById(String id) throws Exception {
		Employee employee = this.employeeRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " not found."));

		SeatConfiguration seatConfiguration = seatConfigurationRepo.findByEmployee(employee);

		return profileMapper.getProfile(employee, seatConfiguration);
	}

	@Override
	public ProfileViewDto updateEmpById(String id, UpdateProfileDto newEmployeeDto) throws Exception {

		Employee employee = this.employeeRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee With id " + id + " not found."));
		SeatConfiguration savedSeatConfiguration = null;

		updateSeatUtils.checkExceptions(employee, newEmployeeDto);

		employee = updateSeatUtils.getUpdatedEmployee(employee, newEmployeeDto);

		SeatConfiguration employeeSeat = updateSeatUtils.saveSeat(employee, newEmployeeDto);
		savedSeatConfiguration = seatConfigurationRepo.save(employeeSeat);

		List<EmployeeWorkingDays> employeeWorkingDays = employeeWorkingDaysRepo.findByEmployee(employee);

		for (EmployeeWorkingDays day : employeeWorkingDays) {
			day.setDeletedBy(employee);
			day.setDeletedDate(LocalDateTime.now());
			employeeWorkingDaysRepo.save(day);
		}

		if (employee.getModeOfWork().getId() == 1) {

			for (int i : newEmployeeDto.getWorkingDays()) {
				employeeWorkingDaysRepo.save(new EmployeeWorkingDays(employee, this.workingDaysRepo.findById(i).get(),
						employee, employee, LocalDateTime.now()));
			}
		}

		Employee savedEmployee = this.employeeRepo.save(employee);

		return profileMapper.getProfile(savedEmployee, savedSeatConfiguration);

	}
}
