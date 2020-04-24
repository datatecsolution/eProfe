package net.profeinformatica.eprofe.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Centro {



    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("codigo_sace")
    @Expose
    private String codigoSace;

    @SerializedName("direccion")
    @Expose
    private String direccion;

    @SerializedName("telefono")
    @Expose
    private String telefono;

    @SerializedName("remember_token")
    @Expose
    private String rememberToken;

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getCodigoSace() {
        return codigoSace;
    }

    public void setCodigoSace(String codigoSace) {
        this.codigoSace = codigoSace;
    }



    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }



    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }



    public String getRememberToken() {
        return rememberToken;
    }

    public void setRememberToken(String rememberToken) {
        this.rememberToken = rememberToken;
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



}
