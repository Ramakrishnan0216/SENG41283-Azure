package se.web.ramakrishnan.mainservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.web.ramakrishnan.mainservice.model.Employee;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
