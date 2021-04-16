package services;

import com.google.gson.Gson;
import java.sql.SQLException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import models.Doctor;
import repositories.RepositoryDoctores;

@Path("/doctores")
public class ServiceDoctores {

    RepositoryDoctores repo;

    public ServiceDoctores() {
        this.repo = new RepositoryDoctores();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getDoctores() throws SQLException {
        List<Doctor> doctores = this.repo.getDoctores();
        Gson converter = new Gson();
        String json = converter.toJson(doctores);
        return json;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public String findDoctor(@PathParam("id") String id) throws SQLException {
        int iddoctor = Integer.parseInt(id);
        Doctor doc = this.repo.findDoctor(iddoctor);
        Gson converter = new Gson();
        String json = converter.toJson(doc);
        return json;
    }

    @POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertDoctor(String doctorjson) throws SQLException {
        Gson converter = new Gson();
        Doctor doctor
                = converter.fromJson(doctorjson, Doctor.class);
        this.repo.insertDoctor(doctor.getIdDoctor(),
                doctor.getApellido(), doctor.getEspecialidad(),
                doctor.getSalario(), doctor.getIdHospital());
        return Response.status(200).build();
    }

    @PUT
    @Path("/put")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDoctor(String doctorjson) throws SQLException {
        Gson converter = new Gson();
        Doctor doctor
                = converter.fromJson(doctorjson, Doctor.class);
        this.repo.updateDoctor(doctor.getIdDoctor(),
                doctor.getApellido(), doctor.getEspecialidad(),
                doctor.getSalario(), doctor.getIdHospital());
        return Response.status(200).build();
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteDoctor(@PathParam("id") String id) throws SQLException {
        int iddoctor = Integer.parseInt(id);
        this.repo.deleteDoctor(iddoctor);
        return Response.status(200).build();
    }
}
