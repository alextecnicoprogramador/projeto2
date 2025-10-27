public class CampeonatoGols {

    private int partida_id;
    private int rodata;
    private String clube;
    private String atleta;
    private int minuto;
    private String tipo_de_gol;

    public CampeonatoGols(int partida_id, int rodata, String clube, String atleta, int minuto, String tipo_de_gol) {
        this.partida_id = partida_id;
        this.rodata = rodata;
        this.clube = clube;
        this.atleta = atleta;
        this.minuto = minuto;
        this.tipo_de_gol = tipo_de_gol;
    }

    @Override
    public String toString() {
        return "Campeonato{" +
                "partida_id='" + partida_id + '\'' +
                ", rodata=" + rodata +
                ", clube='" + clube + '\'' +
                ", atleta='" + atleta + '\'' +
                ", minuto=" + minuto +
                ", tipo_de_gol='" + tipo_de_gol + '\'' +
                '}';
    }

    public int getPartida_id() {
        return partida_id;
    }

    public int getRodata() {
        return rodata;
    }

    public String getClube() {
        return clube;
    }

    public String getAtleta() {
        return atleta;
    }

    public int getMinuto() {
        return minuto;
    }

    public String getTipo_de_gol() {
        return tipo_de_gol;
    }
}
