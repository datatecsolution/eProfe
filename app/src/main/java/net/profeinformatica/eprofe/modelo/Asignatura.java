package net.profeinformatica.eprofe.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Asignatura implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id=0;

    @SerializedName("alias")
    @Expose
    private String alias="";

    @SerializedName("nombre")
    @Expose
    private String nombre="";

    @SerializedName("tipo")
    @Expose
    private Integer tipo=0;

    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("seccions")
    @Expose
    private List<Seccion> seccions=new ArrayList<Seccion>();

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Seccion> getSeccions() {
        return seccions;
    }

    public void setSeccions(List<Seccion> seccions) {
        this.seccions = seccions;
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

    public Asignatura(){

    }

    @Override
    public String toString() {
        return "\'Asignatura\':{" +
                "\'id\':" + id +
                ",\'alias\':'" + alias + '\'' +
                ",\'nombre\':'" + nombre + '\'' +
                ",\'tipo\':" + tipo +
                ",\'createdAt\':'" + createdAt + '\'' +
                ",\'updatedAt\':' " + updatedAt + '\'' +
                ",\'secciones\':"+seccions+
                '}';
    }

    protected Asignatura(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        alias = in.readString();
        nombre = in.readString();
        tipo = in.readByte() == 0x00 ? null : in.readInt();
        createdAt = in.readString();
        updatedAt = in.readString();
        if (in.readByte() == 0x01) {
            seccions = new ArrayList<Seccion>();
            in.readList(seccions, Seccion.class.getClassLoader());
        } else {
            seccions = null;
        }
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
        dest.writeString(alias);
        dest.writeString(nombre);
        if (tipo == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(tipo);
        }
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        if (seccions == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(seccions);
        }
    }

    @SuppressWarnings("unused")
    public static final Creator<Asignatura> CREATOR = new Creator<Asignatura>() {
        @Override
        public Asignatura createFromParcel(Parcel in) {
            return new Asignatura(in);
        }

        @Override
        public Asignatura[] newArray(int size) {
            return new Asignatura[size];
        }
    };
}