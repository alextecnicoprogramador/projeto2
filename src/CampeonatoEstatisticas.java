public class CampeonatoEstatisticas {

    private int partida_id;
    private int rodata;
    private String clube;
    private int chutes;
    private int chutes_no_alvo;
    private int posse_de_bola;
    private int passes;
    private int precisao_passes;
    private int faltas;
    private int cartao_amarelo;
    private int cartao_vermelho;
    private int impedimentos;

    public CampeonatoEstatisticas(int partida_id, int rodata, String clube, int chutes, int chutes_no_alvo, int posse_de_bola, int passes, int precisao_passes, int faltas, int cartao_amarelo, int cartao_vermelho, int impedimentos, int escanteios) {
        this.partida_id = partida_id;
        this.rodata = rodata;
        this.clube = clube;
        this.chutes = chutes;
        this.chutes_no_alvo = chutes_no_alvo;
        this.posse_de_bola = posse_de_bola;
        this.passes = passes;
        this.precisao_passes = precisao_passes;
        this.faltas = faltas;
        this.cartao_amarelo = cartao_amarelo;
        this.cartao_vermelho = cartao_vermelho;
        this.impedimentos = impedimentos;
        this.escanteios = escanteios;
    }

    @Override
    public String toString() {
        return "CampeonatoEstatisticas{" +
                "partida_id=" + partida_id +
                ", rodata=" + rodata +
                ", clube='" + clube + '\'' +
                ", chutes=" + chutes +
                ", chutes_no_alvo=" + chutes_no_alvo +
                ", posse_de_bola=" + posse_de_bola +
                ", passes=" + passes +
                ", precisao_passes=" + precisao_passes +
                ", faltas=" + faltas +
                ", cartao_amarelo=" + cartao_amarelo +
                ", cartao_vermelho=" + cartao_vermelho +
                ", impedimentos=" + impedimentos +
                ", escanteios=" + escanteios +
                '}';
    }

    private int escanteios;

    public int getPartida_id() {
        return partida_id;
    }

    public int getRodata() {
        return rodata;
    }

    public String getClube() {
        return clube;
    }

    public int getChutes() {
        return chutes;
    }

    public int getChutes_no_alvo() {
        return chutes_no_alvo;
    }

    public int getPosse_de_bola() {
        return posse_de_bola;
    }

    public int getPasses() {
        return passes;
    }

    public int getPrecisao_passes() {
        return precisao_passes;
    }

    public int getFaltas() {
        return faltas;
    }

    public int getCartao_amarelo() {
        return cartao_amarelo;
    }

    public int getCartao_vermelho() {
        return cartao_vermelho;
    }

    public int getImpedimentos() {
        return impedimentos;
    }

    public int getEscanteios() {
        return escanteios;
    }
}

