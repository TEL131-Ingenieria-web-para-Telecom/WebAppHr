package com.example.webapphr.Controllers;

import com.example.webapphr.Beans.Employee;
import com.example.webapphr.Daos.EmployeeDao;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(name = "EmployeeServlet", urlPatterns = {"/EmployeeServlet"})
public class EmployeeServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");

        RequestDispatcher view;
        EmployeeDao employeeDao = new EmployeeDao();

        switch (action) {
            case "lista":
                request.setAttribute("listaEmpleados", employeeDao.listarEmpleados());
                view = request.getRequestDispatcher("employees/lista.jsp");
                view.forward(request, response);
                break;
            case "agregar":
                view = request.getRequestDispatcher("employees/formularioNuevo.jsp");
                view.forward(request, response);
                break;
            case "editar":
                if (request.getParameter("id") != null) {
                    String employeeIdString = request.getParameter("id");
                    int employeeId = 0;
                    try {
                        employeeId = Integer.parseInt(employeeIdString);
                    } catch (NumberFormatException ex) {
                        response.sendRedirect("EmployeeServlet");
                    }

                    Employee emp = employeeDao.obtenerEmpleado(employeeId);

                    if (emp != null) {
                        request.setAttribute("empleado", emp);
                        view = request.getRequestDispatcher("employees/formularioEditar.jsp");
                        view.forward(request, response);
                    } else {
                        response.sendRedirect("EmployeeServlet");
                    }

                } else {
                    response.sendRedirect("EmployeeServlet");
                }

                break;
            case "borrar":
                if (request.getParameter("id") != null) {
                    String employeeIdString = request.getParameter("id");
                    int employeeId = 0;
                    try {
                        employeeId = Integer.parseInt(employeeIdString);
                    } catch (NumberFormatException ex) {
                        response.sendRedirect("EmployeeServlet");
                    }

                    Employee emp = employeeDao.obtenerEmpleado(employeeId);

                    if (emp != null) {
                        employeeDao.borrarEmpleado(employeeId);
                    }
                }

                response.sendRedirect("EmployeeServlet");
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");

        Employee employee;
        EmployeeDao employeeDao = new EmployeeDao();

        switch (action) {
            case "guardar":
                employee = new Employee();
                employee.setFirstName(request.getParameter("first_name"));
                employee.setLastName(request.getParameter("last_name"));
                employee.setEmail(request.getParameter("email"));
                employee.setPhoneNumber(request.getParameter("phone"));
                employee.setHireDate(request.getParameter("hire_date"));
                employee.setJobId(request.getParameter("job_id"));
                employee.setSalary(new BigDecimal(request.getParameter("salary")));
                employee.setCommissionPct(request.getParameter("commission").equals("") ? null : new BigDecimal(request.getParameter("commission")));
                employee.setManagerId(Integer.parseInt(request.getParameter("manager_id")));
                employee.setDepartmentId(Integer.parseInt(request.getParameter("department_id")));
                employeeDao.guardarEmpleado(employee);

                response.sendRedirect("EmployeeServlet");
                break;
            case "actualizar":
                employee = new Employee();
                employee.setEmployeeId(Integer.parseInt(request.getParameter("employee_id"))); //no olvidar que para actualizar se debe enviar el ID
                employee.setFirstName(request.getParameter("first_name"));
                employee.setLastName(request.getParameter("last_name"));
                employee.setEmail(request.getParameter("email"));
                employee.setPhoneNumber(request.getParameter("phone"));
                employee.setHireDate(request.getParameter("hire_date"));
                employee.setJobId(request.getParameter("job_id"));
                employee.setSalary(new BigDecimal(request.getParameter("salary")));
                employee.setCommissionPct(request.getParameter("commission").equals("") ? null : new BigDecimal(request.getParameter("commission")));
                employee.setManagerId(Integer.parseInt(request.getParameter("manager_id")));
                employee.setDepartmentId(Integer.parseInt(request.getParameter("department_id")));
                employeeDao.actualizarEmpleado(employee);

                response.sendRedirect("EmployeeServlet?action=lista");

                break;
        }
    }

}
