package com.androfast.server.appgpsubicacion.negocio;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Server on 24/05/2018.
 */

public class Usuario implements Parcelable {
    private String nombre;
    private String codigo;
    private String email;
    private String clave;
    private String telefono;
    private int status;
    private String foto;

    protected Usuario(Parcel in) {
        codigo = in.readString();
        email = in.readString();
        clave = in.readString();
        nombre = in.readString();
        telefono = in.readString();
        foto = in.readString();

    }

    public Usuario(String codigo, String email, String clave, String nombre, String telefono,int status) {
        this.codigo = codigo;
        this.email = email;
        this.nombre = nombre;
        this.clave = clave;
        this.telefono = telefono;
        this.status =status;
    }
    public Usuario(String nombre, String email, String telefono, String clave) {

        this.email = email;
        this.nombre = nombre;
        this.telefono = telefono;
        this.clave = clave;
    }
    public Usuario() {

    }

    public String getCodigo() {
        return codigo;
    }

    public  void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(codigo);
        dest.writeString(email);
        dest.writeString(clave);
        dest.writeString(nombre);
        dest.writeString(telefono);

    }
    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };


}