package net.profeinformatica.eprofe.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Modalidad implements Parcelable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("alias")
    @Expose
    private String alias;
    @SerializedName("nombre")
    @Expose
    private String nombre;
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

    public Modalidad(){

    }

    @Override
    public String toString() {
        return "\'Modalidad\':{" +
                "id:" + id +
                ", alias:'" + alias + '\'' +
                ", nombre:'" + nombre + '\'' +
                ", observaciones:'" + observaciones + '\'' +
                ", createdAt:'" + createdAt + '\'' +
                ", updatedAt:'" + updatedAt + '\'' +
                '}';
    }

    protected Modalidad(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        alias = in.readString();
        nombre = in.readString();
        observaciones = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
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
        dest.writeString(observaciones);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
    }

    @SuppressWarnings("unused")
    public static final Creator<Modalidad> CREATOR = new Creator<Modalidad>() {
        @Override
        public Modalidad createFromParcel(Parcel in) {
            return new Modalidad(in);
        }

        @Override
        public Modalidad[] newArray(int size) {
            return new Modalidad[size];
        }
    };
}
