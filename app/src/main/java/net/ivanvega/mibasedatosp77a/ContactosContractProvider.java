package net.ivanvega.mibasedatosp77a;

public class ContactosContractProvider {


    public static final String CONTENT_URI_CONTACTOS =
            "content://net.ivanvega.mibasedatosp77a.provider/contactos";


    public static final  String [] PROJECTION_CONTACTOS
            = {
            "_id", "usuario", "email", "tel", "fecNacimiento"
    };

    public static final  String FIELD_ID = "_id";
    public static final  String FIELD_USUARIO = "usuario";
    public static final  String FIELD_EMAIL = "email";

}
