package net.profeinformatica.eprofe.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static net.profeinformatica.eprofe.modelo.MarcasSincronizado.ADD_SERVER;

public class DetalleAsistencia {

    @SerializedName("id")
    @Expose
    private Integer id=0;

    @SerializedName("alumno_id")
    @Expose
    private Integer alumnoId;

    @SerializedName("encabezadoasistencia_id")
    @Expose
    private Integer encabezadoasistenciaId=0;

    @SerializedName("estado")
    @Expose
    private Integer estado=1;

    @SerializedName("excusa")
    @Expose
    private Integer excusa=0;


    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("encabezado_asistencia")
    @Expose
    private EncabezadoAsistencia encabezadoAsistencia;
    @SerializedName("alumno")
    @Expose
    private Alumno alumno;

    private Integer sicronizadoServidor=ADD_SERVER;

    private Integer encabezadoasistenciaMovilId=0;

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

    public Integer getEncabezadoasistenciaId() {
        return encabezadoasistenciaId;
    }

    public void setEncabezadoasistenciaId(Integer encabezadoasistenciaId) {
        this.encabezadoasistenciaId = encabezadoasistenciaId;
    }

    public Integer isEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer isExcusa() {
        return excusa;
    }

    public void setExcusa(Integer excusa) {
        this.excusa = excusa;
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

    public EncabezadoAsistencia getEncabezadoAsistencia() {
        return encabezadoAsistencia;
    }

    public void setEncabezadoAsistencia(EncabezadoAsistencia encabezadoAsistencia) {
        this.encabezadoAsistencia = encabezadoAsistencia;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
        this.setAlumnoId(alumno.getId());
    }

    @Override
    public String toString() {
        return "{" +
                "\'id\':" + id +
                ",\'alumno_id\':" + alumnoId +
                ",\' encabezadoasistenciaId\':'" + encabezadoasistenciaId +
                ",\' estado\':'"  + estado +
                ",\'createdAt\':'" + createdAt + '\'' +
                ",\'updatedAt\':'" + updatedAt + '\'' +
               // ", encabezadoAsistencia=" + encabezadoAsistencia +
           //     "," + alumno +
                '}';
    }

    public Integer getSicronizadoServidor() {
        return sicronizadoServidor;
    }

    public void setSicronizadoServidor(@MarcasSincronizado Integer s) {
        this.sicronizadoServidor = s;
    }

    public Integer getEncabezadoasistenciaMovilId() {
        return encabezadoasistenciaMovilId;
    }

    public void setEncabezadoasistenciaMovilId(Integer encabezadoasistenciaMovilId) {
        this.encabezadoasistenciaMovilId = encabezadoasistenciaMovilId;
    }
}
