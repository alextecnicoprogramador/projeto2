package pacote;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class CampeonatoMain {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("campeonato-brasileiro-gols.csv");
        List<String> resultado = CampeonatoBrasileiroGolsCsv.readFromCsv(path);
        resultado.stream()
                .forEach(System.out::println);
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
}
