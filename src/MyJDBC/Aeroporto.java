package MyJDBC;

public class Aeroporto {
    public String sigla;
    public String siglaEstado;
    public String estado;
    public String municipio;
    public double latitude;
    public double longitude;

    public Aeroporto(String sigla, String siglaEstado, String estado, String municipio, double latitude, double longitude) {
        this.sigla = sigla;
        this.siglaEstado = siglaEstado;
        this.estado = estado;
        this.municipio = municipio;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return estado + "-" + municipio + " | " + sigla;
    }
}
