public class CampeonatoCartoes {

    private int partida_id;
    private int rodata;
    private String clube;
    private String cartao;   //
    private String atleta;
    private int num_camisa;  //
    private String posicao;
    private int minuto;

    public CampeonatoCartoes(int partida_id, int rodata, String clube, String cartao, String atleta, int num_camisa, String posicao, int minuto) {
        this.partida_id = partida_id;
        this.rodata = rodata;
        this.clube = clube;
        this.cartao = cartao;
        this.atleta = atleta;
        this.num_camisa = num_camisa;
        this.posicao = posicao;
        this.minuto = minuto;
    }

    @Override
    public String toString() {
        return "CampeonatoCartoes{" +
                "partida_id='" + partida_id + '\'' +
                ", rodata=" + rodata +
                ", clube='" + clube + '\'' +
                ", cartao='" + cartao + '\'' +
                ", atleta='" + atleta + '\'' +
                ", num_camisa=" + num_camisa +
                ", posicao='" + posicao + '\'' +
                ", minuto=" + minuto +
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

    public String getCartao() {
        return cartao;
    }

    public int getNum_camisa() {
        return num_camisa;
    }

    public String getPosicao() {
        return posicao;
    }
}

