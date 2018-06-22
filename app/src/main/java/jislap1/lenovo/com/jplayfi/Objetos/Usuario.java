package jislap1.lenovo.com.jplayfi.Objetos;

public class Usuario {
    String nombres;
    String email;
    String contraseña;

    public Usuario(){
    }

    public Usuario(String nombres, String email, String contraseña) {
        this.nombres = nombres;
        this.email = email;
        this.contraseña = contraseña;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
