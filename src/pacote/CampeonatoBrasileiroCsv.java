package pacote;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CampeonatoBrasileiroCsv {
    public static List<CampeonatoBrasileiroGolsCsv> readFromCsv(Path path) {
        if(!Files.exists(path)) {
            return new ArrayList<>();
        }
        try(Stream<String> lines = Files.lines(path)) {
            return lines.map(String::trim)
                    .filter(l -> !l.toLowerCase().startsWith("partida_id"))
                    .map(CampeonatoBrasileiroCsv::parseCsvLine)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static Optional<CampeonatoBrasileiroGolsCsv> parseCsvLine(String line) {
        try {
            String [] parts = line.split(",", -1);
            if (parts.length < 6) {
                return Optional.empty();
            }
            String partidaId = (parts[0].trim());
            int rodada = Integer.parseInt((parts[1].trim()));
            String clube = (parts[2].trim());
            String atleta = (parts[3].trim());
            int minuto = Integer.parseInt((parts[4].trim()));
            String tipoDeGol = (parts[5].trim());
            return Optional.of(new CampeonatoBrasileiroGolsCsv(partidaId,rodada,clube,atleta,minuto,tipoDeGol));
        } catch (Exception e) {
            System.err.println("Erro na linha "+line+"->"+e.getMessage());
            return Optional.empty();
        }

    }

}
