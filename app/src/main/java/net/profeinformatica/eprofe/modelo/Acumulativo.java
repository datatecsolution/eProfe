package net.profeinformatica.eprofe.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import net.profeinformatica.eprofe.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

import static net.profeinformatica.eprofe.modelo.MarcasSincronizado.ADD_SERVER;

public class Acumulativo {
    @SerializedName("id")
    @Expose
    private Integer id=0;
    @SerializedName("seccion_id")
    @Expose
    private Integer seccionId;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("tipo_acumulativo_id")
    @Expose
    private Integer tipoAcumulativoId;
    @SerializedName("fecha")
    @Expose
    private String fecha;
    @SerializedName("parcial")
    @Expose
    private String parcial;
    @SerializedName("valor")
    @Expose
    private double valor;
    @SerializedName("asignatura_id")
    @Expose
    private Integer asignaturaId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("tipo_acumulativo")
    @Expose
    private TipoAcumulativo tipoAcumulativo;
    @SerializedName("asignatura")
    @Expose
    private Asignatura asignatura;
    @SerializedName("seccion")
    @Expose
    private Seccion seccion;
    @SerializedName("notas_acumulativos")
    @Expose
    private List<NotaAcumulativo> notasAcumulativos = new ArrayList<NotaAcumulativo>();


    @SerializedName("movil_id")
    @Expose
    private Integer movilId;

    public Integer getMovilId() {
        return movilId;
    }
    /**
     * Este codigo esta reservado para la base de datos del movil
     * @author jdmayorga
     * @param movilId  debe enviar una Seccion
     *
     */
    public void setMovilId(Integer movilId) {
        this.movilId = movilId;
    }

    private Integer sicronizadoServidor=ADD_SERVER;

    public Integer getId() {
        return id;
    }

    /**
     * Este codigo esta reservado para la base de datos del servidor
     * @author jdmayorga
     * @param id  el codigo de el servidor
     *
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSeccionId() {
        return seccionId;
    }

    public void setSeccionId(Integer seccionId) {
        this.seccionId = seccionId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getTipoAcumulativoId() {
        return tipoAcumulativoId;
    }

    public void setTipoAcumulativoId(Integer tipoAcumulativoId) {
        this.tipoAcumulativoId = tipoAcumulativoId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getParcial() {
        return parcial;
    }

    public void setParcial(String parcial) {
        this.parcial = parcial;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Integer getAsignaturaId() {
        return asignaturaId;
    }

    public void setAsignaturaId(Integer asignaturaId) {
        this.asignaturaId = asignaturaId;
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

    public TipoAcumulativo getTipoAcumulativo() {
        return tipoAcumulativo;
    }

    public void setTipoAcumulativo(TipoAcumulativo tipoAcumulativo) {
        this.tipoAcumulativo = tipoAcumulativo;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    public Seccion getSeccion() {
        return seccion;
    }

    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
    }

    public List<NotaAcumulativo> getNotasAcumulativos() {
        return notasAcumulativos;
    }

    public void setNotasAcumulativos(List<NotaAcumulativo> n) {
        this.notasAcumulativos = n;
    }

    @Override
    public String toString() {
        return "Acumulativo{" +
                "\'id\':" + id +
                ",\'seccion_id\':" + seccionId +
                ", \'descripcion\':'" + descripcion + '\'' +
                ",\'tipo_acumulativo_id\':" + tipoAcumulativoId +
                ",\'fecha\':'" + fecha + '\'' +
                ",\'parcial\':'" + parcial + '\'' +
                ",\'valor\':" + valor +
                ",\'asignatura_id\':" + asignaturaId +
                ",\'createdAt\':'" + createdAt + '\'' +
                ",\'updatedAt\':'" + updatedAt + '\'' +
                "," + tipoAcumulativo +
                "," + asignatura +
                "," + seccion +
                ",\'notas_acumulativos\':" + notasAcumulativos +
                '}';
    }

    public Integer getSicronizadoServidor() {
        return sicronizadoServidor;
    }

    public void setSicronizadoServidor(@MarcasSincronizado @NonNull Integer sicronizadoServidor) {
        this.sicronizadoServidor = sicronizadoServidor;
    }


}
