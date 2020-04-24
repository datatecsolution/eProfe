package net.profeinformatica.eprofe.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TipoAcumulativo implements Serializable, Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public TipoAcumulativo(){

    }

    protected TipoAcumulativo(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        descripcion = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
    }

    public static final Creator<TipoAcumulativo> CREATOR = new Creator<TipoAcumulativo>() {
        @Override
        public TipoAcumulativo createFromParcel(Parcel in) {
            return new TipoAcumulativo(in);
        }

        @Override
        public TipoAcumulativo[] newArray(int size) {
            return new TipoAcumulativo[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        return "\'tipo_acumulativo\':{" +
                "\'id\':" + id +
                ",\'descripcion\':'" + descripcion + '\'' +
                ",\'createdAt\':'" + createdAt + '\'' +
                ",\'updatedAt\':'" + updatedAt + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(descripcion);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
    }
}