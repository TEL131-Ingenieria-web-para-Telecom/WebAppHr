package com.example.webapphr.Daos;


import com.example.webapphr.Beans.Job;

import java.sql.*;
import java.util.ArrayList;

public class JobDao {

    public ArrayList<Job> obtenerListaTrabajos() {
        ArrayList<Job> listaTrabajos = new ArrayList<>();

        try {
            String user = "root";
            String pass = "root";
            String url = "jdbc:mysql://127.0.0.1:3306/hr";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, pass);
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM jobs");

            while (rs.next()) {
                Job job = new Job();
                job.setJobId(rs.getString(1));
                job.setJobTitle(rs.getString(2));
                job.setMinSalary(rs.getInt(3));
                job.setMaxSalary(rs.getInt(4));

                listaTrabajos.add(job);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return listaTrabajos;

    }

    public void crearTrabajo(String jobId, String jobTitle, int minSalary, int maxSalary) {
        try {
            String user = "root";
            String pass = "root";
            String url = "jdbc:mysql://127.0.0.1:3306/hr";

            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(url, user, pass)) {
                String sql = "INSERT INTO jobs (job_id,job_title,min_salary,max_salary) VALUES (?,?,?,?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, jobId);
                    pstmt.setString(2, jobTitle);
                    pstmt.setInt(3, minSalary);
                    pstmt.setInt(4, maxSalary);
                    pstmt.executeUpdate();
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Job obtenerTrabajo(String jobId) {

        Job job = null;
        try {
            String user = "root";
            String pass = "root";
            String url = "jdbc:mysql://127.0.0.1:3306/hr";

            Class.forName("com.mysql.cj.jdbc.Driver");
            String sql = "SELECT * FROM jobs WHERE job_id = ?";
            try (Connection conn = DriverManager.getConnection(url, user, pass);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, jobId);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        job = new Job();
                        job.setJobId(rs.getString(1));
                        job.setJobTitle(rs.getString(2));
                        job.setMinSalary(rs.getInt(3));
                        job.setMaxSalary(rs.getInt(4));
                    }
                }

            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return job;
    }

    public void actualizarTrabajo(String jobId, String jobTitle, int minSalary, int maxSalary) {
        try {
            String user = "root";
            String pass = "root";
            String url = "jdbc:mysql://127.0.0.1:3306/hr";

            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(url, user, pass)) {
                String sql = "UPDATE jobs SET job_title = ?, min_salary = ?, max_salary = ? "
                        + "WHERE job_id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, jobTitle);
                    pstmt.setInt(2, minSalary);
                    pstmt.setInt(3, maxSalary);
                    pstmt.setString(4, jobId);
                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void borrarTrabajo(String jobId) {
        try {
            String user = "root";
            String pass = "root";
            String url = "jdbc:mysql://127.0.0.1:3306/hr";

            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(url, user, pass)) {
                String sql = "DELETE FROM jobs WHERE job_id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, jobId);
                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
