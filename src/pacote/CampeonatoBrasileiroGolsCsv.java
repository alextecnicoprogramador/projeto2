package pacote;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CampeonatoBrasileiroGolsCsv {
    private String partidaId;
    private int rodata;
    private String clube;
    private String atleta;
    private int minuto;
    private String tipoDeGol;

    public CampeonatoBrasileiroGolsCsv(String partidaId, int rodata, String clube,
                                       String atleta, int minuto, String tipoDeGol) {
        this.partidaId = partidaId;
        this.rodata = rodata;
        this.clube = clube;
        this.atleta = atleta;
        this.minuto = minuto;
        this.tipoDeGol = tipoDeGol;
    }

    public static List<String> readFromCsv(Path path) throws IOException {
        if(!Files.exists(path) ) return new ArrayList<>();
        try(Stream<String> lines = Files.lines(path)) {
            return (List<String>) lines
                    .map(String::trim)
                    .filter(l -> l.toLowerCase().startsWith("partida_id"))
                    .collect(Collectors.toSet());
        }
    }

    public String getPartidaId() {
        return partidaId;
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

    public String getTipoDeGol() {
        return tipoDeGol;
    }

    @Override
    public String toString() {
        return "CampeonatoBrasileiroGolsCsv{" +
                "partidaId='" + partidaId + '\'' +
                ", rodata=" + rodata +
                ", clube='" + clube + '\'' +
                ", atleta='" + atleta + '\'' +
                ", minuto=" + minuto +
                ", tipoDeGol='" + tipoDeGol + '\'' +
                '}';
    }
}
