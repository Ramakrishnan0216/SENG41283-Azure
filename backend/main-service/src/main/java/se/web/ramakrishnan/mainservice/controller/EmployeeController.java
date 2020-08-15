package se.web.ramakrishnan.mainservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.web.ramakrishnan.mainservice.exception.ResourceNotFoundException;
import se.web.ramakrishnan.mainservice.model.Employee;
import se.web.ramakrishnan.mainservice.model.ResponseDelete;
import se.web.ramakrishnan.mainservice.model.ResponseEmployees;
import se.web.ramakrishnan.mainservice.repository.EmployeeRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = {"*"})
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("/employees")
    public ResponseEmployees getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return new ResponseEmployees(employees);
    }

    @GetMapping("/employees/{id}")
    public Employee getEmployeeById(@PathVariable(value = "id") Long employeeId)  {
       return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee","id",employeeId));
    }

    @PostMapping("/employees")
    public Employee createEmployee(@Valid @RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
                                                   @Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee","id",employeeId));

        employee.setEmailId(employeeDetails.getEmailId());
        employee.setLastName(employeeDetails.getLastName());
        employee.setFirstName(employeeDetails.getFirstName());
        final Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseDelete deleteEmployee(@PathVariable(value = "id") Long employeeId) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee","id",employeeId));

        employeeRepository.delete(employee);

        return new ResponseDelete(true);
    }
}
