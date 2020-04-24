package net.profeinformatica.eprofe.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Persona {
    @SerializedName("username")
    @Expose
    protected String username;
    @SerializedName("nombre")
    @Expose
    protected String nombre;
    @SerializedName("apellido")
    @Expose
    protected String apellido;
    @SerializedName("email")
    @Expose
    protected String email;
    @SerializedName("password")
    @Expose
    protected String password;


    public Persona(String n, String a) {
        this.nombre = n;
        this.apellido = a;
    }
    public Persona(){

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String n) {
        this.nombre = n;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String a) {
        this.apellido = a;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
