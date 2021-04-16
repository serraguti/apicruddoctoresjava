package repositories;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.Doctor;

public class RepositoryDoctores {

    private Connection getConnection() throws SQLException {
        DriverManager.registerDriver(new SQLServerDriver());
        String cadena
                = "jdbc:sqlserver://sqlserverjavapgs.database.windows.net:1433;databaseName=SQLAZURE";
        Connection cn
                = DriverManager.getConnection(cadena, "adminsql", "Admin123");
        return cn;
    }

    public List<Doctor> getDoctores() throws SQLException {
        Connection cn = this.getConnection();
        String sql = "select * from doctor";
        Statement st = cn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        ArrayList<Doctor> doctores = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("DOCTOR_NO");
            String apellido = rs.getString("APELLIDO");
            String especialidad = rs.getString("ESPECIALIDAD");
            int salario = rs.getInt("SALARIO");
            int idhospital = rs.getInt("HOSPITAL_COD");
            Doctor doc = new Doctor(id, apellido, especialidad, salario, idhospital);
            doctores.add(doc);
        }
        rs.close();
        cn.close();
        return doctores;
    }

    public Doctor findDoctor(int iddoctor) throws SQLException {
        Connection cn = this.getConnection();
        String sql = "select * from doctor where doctor_no=?";
        PreparedStatement pst = cn.prepareStatement(sql);
        pst.setInt(1, iddoctor);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            int id = rs.getInt("DOCTOR_NO");
            String apellido = rs.getString("APELLIDO");
            String espe = rs.getString("ESPECIALIDAD");
            int sal = rs.getInt("SALARIO");
            int idhosp = rs.getInt("HOSPITAL_COD");
            Doctor doc = new Doctor(id, apellido, espe, sal, idhosp);
            rs.close();
            cn.close();
            return doc;
        } else {
            rs.close();
            cn.close();
            return null;
        }
    }

    public void insertDoctor(int id, String apellido, String espe, int salario, int idhospital) throws SQLException {
        Connection cn = this.getConnection();
        String sql = "insert into doctor values (?,?,?,?,?)";
        PreparedStatement pst = cn.prepareStatement(sql);
        pst.setInt(1, idhospital);
        pst.setInt(2, id);
        pst.setString(3, apellido);
        pst.setString(4, espe);
        pst.setInt(5, salario);
        pst.executeUpdate();
        cn.close();
    }

    public void updateDoctor(int iddoctor, String apellido, String espe,
            int salario, int idhospital) throws SQLException {
        Connection cn = this.getConnection();
        String sql = "update doctor set apellido=?, especialidad=?, "
                + "salario=?, hospital_cod=? "
                + " where doctor_no=?";
        PreparedStatement pst = cn.prepareStatement(sql);
        pst.setString(1, apellido);
        pst.setString(2, espe);
        pst.setInt(3, salario);
        pst.setInt(4, idhospital);
        pst.setInt(5, iddoctor);
        pst.executeUpdate();
        cn.close();
    }

    public void deleteDoctor(int iddoctor) throws SQLException {
        Connection cn = this.getConnection();
        String sql = "delete from doctor where doctor_no=?";
        PreparedStatement pst = cn.prepareStatement(sql);
        pst.setInt(1, iddoctor);
        pst.executeUpdate();
        cn.close();
    }
}
