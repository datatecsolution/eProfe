package net.profeinformatica.eprofe.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static net.profeinformatica.eprofe.modelo.MarcasSincronizado.ADD_SERVER;


public class Alumno {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("rne")
    @Expose
    private String rne;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("apellido")
    @Expose
    private String apellido;

    @SerializedName("email")
    @Expose
    private String email;



    @SerializedName("telefono")
    @Expose
    private String telefono;



    @SerializedName("genero")
    @Expose
    private Integer genero;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("remember_token")
    @Expose
    private String rememberToken;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    private Integer sicronizadoServidor=ADD_SERVER;

    public Alumno(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRne() {
        return rne;
    }

    public void setRne(String rne) {
        this.rne = rne;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Integer getGenero() {
        return genero;
    }

    public void setGenero(Integer g) {
        this.genero = g;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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


    @Override
    public String toString() {
        return "\'Alumno\':{" +
                "id:" + id +
                ", rne:'" + rne + '\'' +
                ", username:'" + username + '\'' +
                ", nombre:'" + nombre + '\'' +
                ", apellido:'" + apellido + '\'' +
                ", genero:'" + genero + '\'' +
                ", email:'" + email + '\'' +
                ", password:'" + password + '\'' +
                ", rememberToken:'" + rememberToken + '\'' +
                ", createdAt:'" + createdAt + '\'' +
                ", updatedAt:'" + updatedAt + '\'' +
                '}';
    }

    public int getSicronizadoServidor() {
        return sicronizadoServidor;
    }

    public void setSicronizadoServidor(@MarcasSincronizado int s) {
        this.sicronizadoServidor = s;
    }
}