package se.web.ramakrishnan.employeeservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import se.web.ramakrishnan.employeeservice.model.Employee;
import se.web.ramakrishnan.employeeservice.model.ResponseEmployees;
import se.web.ramakrishnan.employeeservice.model.StorageResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api")
@CrossOrigin(origins = {"*"})
public class EmployeeController {

    @Autowired
    Environment environment;
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/employees")
    public ResponseEmployees getAllEmployees() {
        String url = environment.getProperty("service.main.endpoint");
        return restTemplate.getForObject(url, ResponseEmployees.class);
    }

    @GetMapping("/employees/{id}")
    public Employee getEmployeeById(@PathVariable(value = "id") Long employeeId) {
        String url = environment.getProperty("service.main.endpoint");
        return restTemplate.getForObject(url, Employee.class);
    }

    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        String url = environment.getProperty("service.main.endpoint");
        HttpEntity<Employee> request = new HttpEntity<>(employee);
        Employee response = restTemplate.postForObject(url, request, Employee.class);
        StorageResponse storageResponse = uploadToStorage(response);
        if (!storageResponse.isError())
            response.setImgUrl(storageResponse.getUri());
        return response;
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
                                                   @RequestBody Employee employeeDetails) {
        String endpoint = environment.getProperty("service.main.endpoint");
        String url = endpoint + "/" + employeeId;
        HttpEntity<Employee> request = new HttpEntity<>(employeeDetails);
        return restTemplate.exchange(url, HttpMethod.PUT, request, Employee.class);
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployee(@PathVariable(value = "id") Long employeeId) {
        String endpoint = environment.getProperty("service.main.endpoint");
        String url = endpoint + "/" + employeeId;
        restTemplate.delete(url);
    }

    public StorageResponse uploadToStorage(Employee employee) {
        String url = environment.getProperty("service.storage.endpoint");
        HttpEntity<Employee> request = new HttpEntity<>(employee);
        return restTemplate.postForObject(url, request, StorageResponse.class);
    }
}
