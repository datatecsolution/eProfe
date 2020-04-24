package net.profeinformatica.eprofe.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static net.profeinformatica.eprofe.modelo.MarcasSincronizado.ADD_SERVER;

public class NotaAcumulativo {

    @SerializedName("id")
    @Expose
    private Integer id=0;

    @SerializedName("alumno_id")
    @Expose
    private Integer alumnoId;

    @SerializedName("acumulativo_id")
    @Expose
    private Integer acumulativoId=0;

    @SerializedName("nota")
    @Expose
    private double nota;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("alumno")
    @Expose
    private Alumno alumno;

    @SerializedName("acumulativo")
    @Expose
    private Acumulativo acumulativo;

    private Integer sicronizadoServidor=ADD_SERVER;

    private Integer acumulativoMovilId=0;

    @SerializedName("movil_id")
    @Expose
    private Integer movilId;

    public Integer getMovilId() {
        return movilId;
    }

    public void setMovilId(Integer movilId) {
        this.movilId = movilId;
    }

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

    public Integer getAcumulativoId() {
        return acumulativoId;
    }

    public void setAcumulativoId(Integer acumulativoId) {
        this.acumulativoId = acumulativoId;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
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

    public Acumulativo getAcumulativo() {
        return acumulativo;
    }

    public void setAcumulativo(Acumulativo acumulativo) {
        this.acumulativo = acumulativo;
    }

    @Override
    public String toString() {
        return "{" +
                "\'id\':" + id +
                ",\'alumno_id\':" + alumnoId +
                ",\'acumulativo_id\':" + acumulativoId +
                ",\'nota\':" + nota +
                ",\'createdAt\':'" + createdAt + '\'' +
                ",\'updatedAt\':'" + updatedAt + '\'' +
                "," + alumno +
          //      "," + acumulativo +
                '}';
    }

    public Integer getSicronizadoServidor() {
        return sicronizadoServidor;
    }

    public void setSicronizadoServidor(@MarcasSincronizado Integer s) {
        this.sicronizadoServidor = s;
    }

    public Integer getAcumulativoMovilId() {
        return acumulativoMovilId;
    }

    public void setAcumulativoMovilId(Integer acumulativoMovilId) {
        this.acumulativoMovilId = acumulativoMovilId;
    }
}
