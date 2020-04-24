package net.profeinformatica.eprofe.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import static net.profeinformatica.eprofe.modelo.MarcasSincronizado.ADD_SERVER;

public class EncabezadoAsistencia {
    @SerializedName("id")
    @Expose
    private Integer id=0;
    @SerializedName("seccion_id")
    @Expose
    private Integer seccionId;
    @SerializedName("asignatura_id")
    @Expose
    private Integer asignaturaId;
    @SerializedName("fecha")
    @Expose
    private String fecha;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("seccion")
    @Expose
    private Seccion seccion;
    @SerializedName("asignatura")
    @Expose
    private Asignatura asignatura;

    @SerializedName("detalles_asistencia")
    @Expose
    private List<DetalleAsistencia> detallesAsistencia = new ArrayList<DetalleAsistencia>();

    private Integer sicronizadoServidor=ADD_SERVER;

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

    public Integer getSeccionId() {
        return seccionId;
    }

    public void setSeccionId(Integer seccionId) {
        this.seccionId = seccionId;
    }

    public Integer getAsignaturaId() {
        return asignaturaId;
    }

    public void setAsignaturaId(Integer asignaturaId) {
        this.asignaturaId = asignaturaId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
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

    public Seccion getSeccion() {
        return seccion;
    }

    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
        this.setSeccionId(seccion.getId());
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {

        this.asignatura = asignatura;
        this.setAsignaturaId(asignatura.getId());
    }

    public List<DetalleAsistencia> getDetallesAsistencia() {
        return detallesAsistencia;
    }

    public void setDetallesAsistencia(List<DetalleAsistencia> detallesAsistencia) {
        this.detallesAsistencia = detallesAsistencia;
    }

    @Override
    public String toString() {
        return "EncabezadoAsistencia{" +
                "\'id\':" + id +
                ",\'seccion_id\':" + seccionId +
                ",\'asignatura_id\':" + asignaturaId +
                ",\'fecha\':'" + fecha + '\'' +
                ",\'createdAt\':'" + createdAt + '\'' +
                ",\'updatedAt\':'" + updatedAt + '\'' +
             //   "," + asignatura +
             //   "," + seccion +
                ",\'detalles_asistencia\':"+ detallesAsistencia +
                '}';
    }

    public Integer getSicronizadoServidor() {
        return sicronizadoServidor;
    }

    public void setSicronizadoServidor(@MarcasSincronizado Integer s) {
        sicronizadoServidor = s;
    }
}
