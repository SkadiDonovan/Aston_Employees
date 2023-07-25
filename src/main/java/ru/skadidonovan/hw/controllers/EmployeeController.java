package ru.skadidonovan.hw.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.skadidonovan.hw.dao.EmployeeDAO;
import ru.skadidonovan.hw.models.Employee;
import ru.skadidonovan.hw.services.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping()
    public String getAll() {
        return employeeService.getAll();
    }

    @GetMapping("/employee")
    public String getOneByName(@RequestParam("first_name") String firstName,
                               @RequestParam("last_name") String lastName) {
        return employeeService.getOneByName(firstName, lastName);
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid Employee employee,
                                             BindingResult bindingResult) {
        if(bindingResult.hasErrors()){

        }
        System.out.println(employee);
        employeeService.save(employee);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/employee/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid Employee employee,
                                             @PathVariable long id) {
        employeeService.update(id, employee);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable long id) {
        employeeService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @GetMapping("/employee/{id}/projects")
    public String getEmployeeProject(@PathVariable long id){
        return employeeService.getProjectByEmployee(id);
    }
}
