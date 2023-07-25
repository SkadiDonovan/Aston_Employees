package ru.skadidonovan.hw.dao;

import org.springframework.stereotype.Component;
import ru.skadidonovan.hw.models.Employee;

import java.sql.*;
import java.sql.Date;
import java.util.*;

@Component
public class EmployeeDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/hw1";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }


    public List<Employee> getAll() {
        List<Employee> employees = new ArrayList<>();
        String SQL = "SELECT empl.id, empl.first_name, empl.last_name, " +
                "empl.date_of_birth, pos.position " +
                "FROM employee AS empl LEFT JOIN position pos on pos.id = empl.position_id ";
        Statement statement = getStatement();
        try {
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                Employee employee = createEmployee(resultSet);
                employees.add(employee);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
        }
        return employees;
    }

    public Employee getByName(String firstName, String lastName) {
        Employee employee;
        String SQL = "SELECT empl.id, empl.first_name, empl.last_name, " +
                "empl.date_of_birth, pos.position " +
                "FROM employee AS empl LEFT JOIN position pos on pos.id = empl.position_id " +
                "WHERE first_name=? AND last_name=?";
        PreparedStatement ps = getPrepareStatement(SQL);
        try {
            ps.setString(1, firstName);
            ps.setString(2, lastName);

            ResultSet resultSet = ps.executeQuery();

            resultSet.next();

            employee = createEmployee(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closePrepareStatement(ps);
        }
        return employee;
    }

    public boolean save(Employee employee) {
        String SQL = "INSERT INTO Employee(first_name, last_name, date_of_birth) " +
                "VALUES(?, ?, ?)";

        PreparedStatement ps = getPrepareStatement(SQL);
        try {

            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setDate(3, Date.valueOf(employee.getDateOfBirth()));

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closePrepareStatement(ps);

        }
        return true;
    }

    public boolean update(long id, Employee updatedEmployee) {
        String SQL = "UPDATE Employee SET first_name=?, last_name=?, date_of_birth=? WHERE id=?";
        PreparedStatement ps = getPrepareStatement(SQL);
        try {
            ps.setString(1, updatedEmployee.getFirstName());
            ps.setString(2, updatedEmployee.getLastName());
            ps.setDate(3, Date.valueOf(updatedEmployee.getDateOfBirth()));
            ps.setLong(4, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closePrepareStatement(ps);
        }
        return true;
    }


    public boolean delete(long id) {
        String SQL = "DELETE FROM Employee WHERE id=?";
        PreparedStatement ps = getPrepareStatement(SQL);
        try {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closePrepareStatement(ps);
        }
        return true;
    }

    public List<String> getProjectByEmployee(long id) {
        List<String> projects = new ArrayList<>();
        String SQL = "SELECT p.project_name AS project FROM employee empl " +
                "LEFT JOIN employee_project ep on empl.id = ep.employee_id " +
                "LEFT JOIN project p on p.id = ep.project_id " +
                "WHERE empl.id=?";
        PreparedStatement ps = getPrepareStatement(SQL);
        try {
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()){
                projects.add(resultSet.getString("project"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closePrepareStatement(ps);
        }
        return projects;
    }

    //Заполнение полей сущности Employee
    public Employee createEmployee(ResultSet resultSet) throws SQLException {
        Employee employee = new Employee();

        employee.setId(resultSet.getLong("id"));
        employee.setFirstName(resultSet.getString("first_name"));
        employee.setLastName(resultSet.getString("last_name"));
        employee.setDateOfBirth(resultSet.getDate("date_of_birth").toLocalDate());
        employee.setPosition(resultSet.getString("position"));

        return employee;
    }

    // Получение экземпляра PrepareStatement
    private PreparedStatement getPrepareStatement(String sql) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }

    // Закрытие PrepareStatement
    private void closePrepareStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Получение экземпляра Statement
    private Statement getStatement() {
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    // Закрытие Statement
    private void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
