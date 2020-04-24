package net.profeinformatica.eprofe.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Seccion  implements Serializable, Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id=0;

    @SerializedName("modalidad_id")
    @Expose
    private Integer modalidadId;

    @SerializedName("curso")
    @Expose
    private String curso;

    @SerializedName("seccion")
    @Expose
    private String seccion;

    @SerializedName("jornada")
    @Expose
    private String jornada;

    @SerializedName("centro_id")
    @Expose
    private Integer centroId;

    @SerializedName("periodo_id")
    @Expose
    private Integer periodoId;

    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("modalidad")
    @Expose
    private Modalidad modalidad;

    @SerializedName("periodo")
    @Expose
    private Periodo periodo;

    @SerializedName("centro")
    @Expose
    private Centro centro;

    private boolean sincronizarServer=false;


    public Seccion(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getModalidadId() {
        return modalidadId;
    }

    public void setModalidadId(Integer modalidadId) {
        this.modalidadId = modalidadId;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
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

    public Integer getCentroId() {
        return centroId;
    }

    public void setCentroId(Integer centroId) {
        this.centroId = centroId;
    }
    public Integer getPeriodoId() {
        return periodoId;
    }

    public void setPeriodoId(Integer periodoId) {
        this.periodoId = periodoId;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    public String getSeccionSort(){
        return curso+" "+modalidad.getAlias()+" SECCION "+seccion;
    }

    public Centro getCentro() {
        return centro;
    }

    public void setCentro(Centro centro) {
        this.centro = centro;
    }
    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    @Override
    public String toString() {
        return "\'Seccion\':{" +
                "\'id\':" + id +
                ",\'modalidadId\':" + modalidadId +
                ",\'curso\':'" + curso + '\'' +
                ",\'seccio\':'" + seccion + '\'' +
                ",\'jornada\':'" + jornada + '\'' +
                "\'centroId\':" + centroId +
                "\'periodoId\':" + periodoId +
                ",\'createdAt\':'" + createdAt + '\'' +
                ",\'updatedAt\':'" + updatedAt + '\'' +
                "," + modalidad +
                '}';
    }

    protected Seccion(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        modalidadId = in.readByte() == 0x00 ? null : in.readInt();
        centroId = in.readByte() == 0x00 ? null : in.readInt();
        periodoId = in.readByte() == 0x00 ? null : in.readInt();
        curso = in.readString();
        seccion = in.readString();
        jornada = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        modalidad = (Modalidad) in.readValue(Modalidad.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
        if (modalidadId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(modalidadId);
        }
        if (centroId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(centroId);
        }
        if (periodoId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(periodoId);
        }

        dest.writeString(curso);
        dest.writeString(seccion);
        dest.writeString(jornada);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        dest.writeValue(modalidad);
    }

    @SuppressWarnings("unused")
    public static final Creator<Seccion> CREATOR = new Creator<Seccion>() {
        @Override
        public Seccion createFromParcel(Parcel in) {
            return new Seccion(in);
        }

        @Override
        public Seccion[] newArray(int size) {
            return new Seccion[size];
        }
    };

    public boolean isSincronizarServer() {
        return sincronizarServer;
    }

    public void setSincronizarServer(boolean sincronizarServer) {
        this.sincronizarServer = sincronizarServer;
    }
}
