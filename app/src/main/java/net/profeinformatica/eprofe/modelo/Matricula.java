package net.profeinformatica.eprofe.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Matricula {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("alumno_id")
    @Expose
    private Integer alumnoId;
    @SerializedName("seccion_id")
    @Expose
    private Integer seccionId;
    @SerializedName("year")
    @Expose
    private Integer year;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("alumno")
    @Expose
    private Alumno alumno;
    @SerializedName("seccion")
    @Expose
    private Seccion seccion;



    private boolean asistencia=false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(Integer alumnoId) {
        this.alumnoId = alumnoId;
    }

    public Integer getSeccionId() {
        return seccionId;
    }

    public void setSeccionId(Integer seccionId) {
        this.seccionId = seccionId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Seccion getSeccion() {
        return seccion;
    }

    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
    }

    public boolean isAsistencia() {
        return asistencia;
    }

    public void setAsistencia(boolean asistencia) {
        this.asistencia = asistencia;
    }

    @Override
    public String toString() {
        return "Matricula{" +
                "id=" + id +
                ", alumnoId=" + alumnoId +
                ", seccionId=" + seccionId +
                ", year=" + year +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", alumno=" + alumno +
                ", seccion=" + seccion +
                '}';
    }
}