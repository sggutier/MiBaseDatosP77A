package net.ivanvega.mibasedatosp77a;

public class ContactosContractProvider {


    public static final String CONTENT_URI_CONTACTOS =
            "content://net.ivanvega.mibasedatosp77a.provider/contactos";


    public static final  String [] PROJECTION_CONTACTOS
            = MiDB.COLUMNS_NAME_CONTACTO;

    public static final  String FIELD_ID = MiDB.COLUMNS_NAME_CONTACTO[0];
    public static final  String FIELD_USUARIO = MiDB.COLUMNS_NAME_CONTACTO[1];
    public static final  String FIELD_EMAIL = MiDB.COLUMNS_NAME_CONTACTO[2];

}
