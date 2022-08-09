package com.example.webapphr.Daos;

import com.example.webapphr.Beans.Employee;

import java.sql.*;
import java.util.ArrayList;

public class EmployeeDao {

    public ArrayList<Employee> listarEmpleados() {

        ArrayList<Employee> listaEmpleados = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hr", "root", "root");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select * from employees")) {

            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getInt(1));
                employee.setFirstName(rs.getString(2));
                employee.setLastName(rs.getString(3));
                employee.setEmail(rs.getString(4));
                employee.setPhoneNumber(rs.getString(5));
                employee.setHireDate(rs.getString(6));
                employee.setJobId(rs.getString(7));
                employee.setSalary(rs.getBigDecimal(8));
                employee.setCommissionPct(rs.getBigDecimal(9));
                employee.setManagerId(rs.getInt(10));
                employee.setDepartmentId(rs.getInt(11));

                listaEmpleados.add(employee);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listaEmpleados;
    }

    public Employee obtenerEmpleado(int employeeId) {

        Employee employee = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        String sql = "SELECT * FROM employees WHERE employee_id = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hr", "root", "root");
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, employeeId);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    employee = new Employee();
                    employee.setEmployeeId(rs.getInt(1));
                    employee.setFirstName(rs.getString(2));
                    employee.setLastName(rs.getString(3));
                    employee.setEmail(rs.getString(4));
                    employee.setPhoneNumber(rs.getString(5));
                    employee.setHireDate(rs.getString(6));
                    employee.setJobId(rs.getString(7));
                    employee.setSalary(rs.getBigDecimal(8));
                    employee.setCommissionPct(rs.getBigDecimal(9));
                    employee.setManagerId(rs.getInt(10));
                    employee.setDepartmentId(rs.getInt(11));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return employee;
    }

    public void guardarEmpleado(Employee employee) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        String sql = "INSERT INTO employees (first_name, last_name, email, phone_number, hire_date, job_id, salary, commission_pct, manager_id, department_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hr", "root", "root");
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, employee.getFirstName());
            pstmt.setString(2, employee.getLastName());
            pstmt.setString(3, employee.getEmail());
            pstmt.setString(4, employee.getPhoneNumber());
            pstmt.setString(5, employee.getHireDate());
            pstmt.setString(6, employee.getJobId());
            pstmt.setBigDecimal(7, employee.getSalary());
            if (employee.getCommissionPct() == null) {
                pstmt.setNull(8, Types.DECIMAL);
            } else {
                pstmt.setBigDecimal(8, employee.getCommissionPct());
            }
            pstmt.setInt(9, employee.getManagerId());
            pstmt.setInt(10, employee.getDepartmentId());

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void actualizarEmpleado(Employee employee) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        String sql = "UPDATE employees "
                + "SET first_name = ?, "
                + "last_name = ?, "
                + "email = ?, "
                + "phone_number = ?, "
                + "hire_date = ?, "
                + "job_id = ?, "
                + "salary = ?, "
                + "commission_pct = ?, "
                + "manager_id = ?, "
                + "department_id = ? "
                + "WHERE employee_id = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hr", "root", "root");
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, employee.getFirstName());
            pstmt.setString(2, employee.getLastName());
            pstmt.setString(3, employee.getEmail());
            pstmt.setString(4, employee.getPhoneNumber());
            pstmt.setString(5, employee.getHireDate());
            pstmt.setString(6, employee.getJobId());
            pstmt.setBigDecimal(7, employee.getSalary());
            if (employee.getCommissionPct() == null) {
                pstmt.setNull(8, Types.DECIMAL);
            } else {
                pstmt.setBigDecimal(8, employee.getCommissionPct());
            }
            pstmt.setInt(9, employee.getManagerId());
            pstmt.setInt(10, employee.getDepartmentId());
            pstmt.setInt(11, employee.getEmployeeId());

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void borrarEmpleado(int employeeId) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hr", "root", "root");
                PreparedStatement pstmt = conn.prepareStatement("DELETE FROM employees WHERE employee_id = ?")) {

            pstmt.setInt(1, employeeId);
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
