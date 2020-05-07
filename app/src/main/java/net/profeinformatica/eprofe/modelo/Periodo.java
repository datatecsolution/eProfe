package net.profeinformatica.eprofe.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Periodo {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("fecha_inicio")
    @Expose
    private String fechaInicio;

    @SerializedName("fecha_final")
    @Expose
    private String fechaFinal;

    @SerializedName("estado")
    @Expose
    private Integer estado;

    @SerializedName("observaciones")
    @Expose
    private String observaciones;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }



    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }


    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }



    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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



    @Override
    public String toString() {
        return "\'Perido\':{" +
                "id:" + id +
                ", fecha_inicio:'" + fechaInicio + '\'' +
                ", fecha_final:'" + fechaFinal + '\'' +
                ", estado:'" + estado + '\'' +
                ", observaciones:'" + observaciones + '\'' +
                ", createdAt:'" + createdAt + '\'' +
                ", updatedAt:'" + updatedAt + '\'' +
                '}';
    }

}