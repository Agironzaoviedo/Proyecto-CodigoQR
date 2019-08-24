package ing.superdevs.geo_parking;

public class Usuario {

    private String EtNombreComp,EtCedula,EtTelefono,EtNroPosPArqueadero;

    public Usuario(String etNombreComp, String etCedula, String etTelefono, String etNroPosPArqueadero) {
        EtNombreComp = etNombreComp;
        EtCedula = etCedula;
        EtTelefono = etTelefono;
        EtNroPosPArqueadero = etNroPosPArqueadero;
    }

    public String getEtNombreComp() {
        return EtNombreComp;
    }

    public void setEtNombreComp(String etNombreComp) {
        EtNombreComp = etNombreComp;
    }

    public String getEtCedula() {
        return EtCedula;
    }

    public void setEtCedula(String etCedula) {
        EtCedula = etCedula;
    }

    public String getEtTelefono() {
        return EtTelefono;
    }

    public void setEtTelefono(String etTelefono) {
        EtTelefono = etTelefono;
    }

    public String getEtNroPosPArqueadero() {
        return EtNroPosPArqueadero;
    }

    public void setEtNroPosPArqueadero(String etNroPosPArqueadero) {
        EtNroPosPArqueadero = etNroPosPArqueadero;
    }


    @Override
    public String toString() {
        return "Usuario{" +
                "Nombre Completo=*" + EtNombreComp +"*"+
                ", Cedula=*" + EtCedula +"*"+
                ", Telefono=*" + EtTelefono +"*"+
                ", Nro Pos. Parqueadero=*" + EtNroPosPArqueadero +"*"+
                '}';
    }
}
