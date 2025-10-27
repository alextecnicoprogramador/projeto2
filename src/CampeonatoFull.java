import java.time.LocalDate;
import java.time.LocalTime;

public class CampeonatoFull {

    private int ID;
    private int rodata;
    private LocalDate data;
    private LocalTime hora;
    private String mandante;
    private String visitante;
    private String formacao_mandante;
    private String formacao_visitante;
    private String tecnico_mandante;
    private String tecnico_visitante;
    private String vencedor;
    private String arena;
    private int mandante_Placar;
    private int visitante_Placar;
    private String mandante_Estado;
    private String visitante_Estado;

    public CampeonatoFull(int ID, int rodata, LocalDate data, LocalTime hora, String mandante, String visitante, String formacao_mandante, String formacao_visitante, String tecnico_mandante, String tecnico_visitante, String vencedor, String arena, int mandante_Placar, int visitante_Placar, String mandante_Estado, String visitante_Estado) {
        this.ID = ID;
        this.rodata = rodata;
        this.data = data;
        this.hora = hora;
        this.mandante = mandante;
        this.visitante = visitante;
        this.formacao_mandante = formacao_mandante;
        this.formacao_visitante = formacao_visitante;
        this.tecnico_mandante = tecnico_mandante;
        this.tecnico_visitante = tecnico_visitante;
        this.vencedor = vencedor;
        this.arena = arena;
        this.mandante_Placar = mandante_Placar;
        this.visitante_Placar = visitante_Placar;
        this.mandante_Estado = mandante_Estado;
        this.visitante_Estado = visitante_Estado;
    }

    @Override
    public String toString() {
        return "CampeonatoFull{" +
                "ID=" + ID +
                ", rodata=" + rodata +
                ", data=" + data +
                ", hora=" + hora +
                ", mandante='" + mandante + '\'' +
                ", visitante='" + visitante + '\'' +
                ", formacao_mandante='" + formacao_mandante + '\'' +
                ", formacao_visitante='" + formacao_visitante + '\'' +
                ", tecnico_mandante='" + tecnico_mandante + '\'' +
                ", tecnico_visitante='" + tecnico_visitante + '\'' +
                ", vencedor='" + vencedor + '\'' +
                ", arena='" + arena + '\'' +
                ", mandante_Placar=" + mandante_Placar +
                ", visitante_Placar=" + visitante_Placar +
                ", mandante_Estado='" + mandante_Estado + '\'' +
                ", visitante_Estado='" + visitante_Estado + '\'' +
                '}';
    }

    public int getID() {
        return ID;
    }

    public int getRodata() {
        return rodata;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public String getMandante() {
        return mandante;
    }

    public String getVisitante() {
        return visitante;
    }

    public String getFormacao_mandante() {
        return formacao_mandante;
    }

    public String getFormacao_visitante() {
        return formacao_visitante;
    }

    public String getTecnico_mandante() {
        return tecnico_mandante;
    }

    public String getTecnico_visitante() {
        return tecnico_visitante;
    }

    public String getVencedor() {
        return vencedor;
    }

    public String getArena() {
        return arena;
    }

    public int getMandante_Placar() {
        return mandante_Placar;
    }

    public int getVisitante_Placar() {
        return visitante_Placar;
    }

    public String getMandante_Estado() {
        return mandante_Estado;
    }

    public String getVisitante_Estado() {
        return visitante_Estado;
    }
}
