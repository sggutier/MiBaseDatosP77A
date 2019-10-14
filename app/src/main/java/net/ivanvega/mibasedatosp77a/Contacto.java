package net.ivanvega.mibasedatosp77a;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Date;

class Contacto implements Parcelable {
    private int id;
    private String usuario;
    private String email;
    private String tel;
    private Date fecNac;

    public Contacto(int id, String usuario, String email, String tel) {
        this.id = id;
        this.usuario = usuario;
        this.email = email;
        this.tel = tel;
    }

    public Contacto(String usuario, String email, String tel) {
        this.id = 0;
        this.usuario = usuario;
        this.email = email;
        this.tel = tel;
    }

    public Contacto(String usuario, String email, String tel, Date fecNac) {
        this.id = 0;
        this.usuario = usuario;
        this.email = email;
        this.tel = tel;
        this.fecNac = fecNac;
    }

    protected Contacto(Parcel in) {
        id = in.readInt();
        usuario = in.readString();
        email = in.readString();
        tel = in.readString();
        fecNac = (java.sql.Date) in.readSerializable();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Date getFecNac() {
        return fecNac;
    }

    public void setFecNac(Date fecNac) {
        this.fecNac = fecNac;
    }

    public static final Creator<Contacto> CREATOR = new Creator<Contacto>() {
        @Override
        public Contacto createFromParcel(Parcel entr) {
            return new Contacto(entr);
        }

        @Override
        public Contacto[] newArray(int size) {
            return new Contacto[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel sal, int flags) {
        sal.writeInt(this.id);
        sal.writeString(usuario);
        sal.writeString(email);
        sal.writeString(tel);
        sal.writeSerializable(fecNac);
    }
}
