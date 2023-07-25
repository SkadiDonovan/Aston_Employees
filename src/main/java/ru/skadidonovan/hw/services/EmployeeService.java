package ru.skadidonovan.hw.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skadidonovan.hw.dao.EmployeeDAO;
import ru.skadidonovan.hw.models.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeDAO employeeDAO;
    private final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

    @Autowired
    public EmployeeService(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public String getAll() {
        List<Employee> list = employeeDAO.getAll();
        String json;
        try {
            json = mapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    public String getOneByName(String firstName, String lastName) {
        String json;
        try {
            json = mapper.writeValueAsString(employeeDAO.getByName(firstName, lastName));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    @Transactional
    public void save(Employee employee) {
        employeeDAO.save(employee);
    }

    @Transactional
    public boolean update(long id, Employee employee) {
        employeeDAO.update(id, employee);
        return true;
    }
    @Transactional
    public boolean delete(long id){
        employeeDAO.delete(id);
        return true;
    }

    public String getProjectByEmployee(long id){
        String json;
        try {
            json = mapper.writeValueAsString(employeeDAO.getProjectByEmployee(id));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }
}
