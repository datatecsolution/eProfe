package net.profeinformatica.eprofe.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Docente {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("apellido")
    @Expose
    private String apellido;
    @SerializedName("genero")
    @Expose
    private Integer genero;

    @SerializedName("direccion")
    @Expose
    private String direccion;

    @SerializedName("telefono")
    @Expose
    private String telefono;

    @SerializedName("user_sace")
    @Expose
    private String userSace;
    @SerializedName("password_sace")
    @Expose
    private String passwordSace;
    @SerializedName("email")
    @Expose
    private String email;
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
    @SerializedName("asignaturas")
    @Expose
    private List<Asignatura> asignaturas = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getGenero() {
        return genero;
    }

    public void setGenero(Integer genero) {
        this.genero = genero;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUserSace() {
        return userSace;
    }

    public void setUserSace(String userSace) {
        this.userSace = userSace;
    }

    public String getPasswordSace() {
        return passwordSace;
    }

    public void setPasswordSace(String passwordSace) {
        this.passwordSace = passwordSace;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public List<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(List<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }


    @Override
    public String toString() {
        return "Docente{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", genero=" + genero +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", userSace='" + userSace + '\'' +
                ", passwordSace='" + passwordSace + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", rememberToken='" + rememberToken + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", asignaturas=" + asignaturas +
                '}';
    }
}