import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CampeonatoGolsProcessador extends CampeonatoProcessador {

    public static void addToCsv(Path path, List<CampeonatoGols> campeonato) {
        boolean arquivoExiste = Files.exists(path);
        try(BufferedWriter writer =
                Files.newBufferedWriter(path,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND)) {
            if(!arquivoExiste) {
                writer.write("partida_id, rodata, clube, atleta, minuto, tipo_de_gol");
                writer.newLine();
            } else {
                BufferedReader arquivo = Files.newBufferedReader(path);
                String primeiraLinha = arquivo.readLine();
                if(!primeiraLinha.toLowerCase().contains("partida_id")) {
                    writer.write("partida_id, rodata, clube, atleta, minuto, tipo_de_gol");
                    writer.newLine();
                }
            }
            for(CampeonatoGols c : campeonato) {
                writer.write(toCsvLine(c));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<CampeonatoGols> readFromCsv(Path path) {
        if(!Files.exists(path)) {
            return new ArrayList<>();
        }
        try(Stream<String> lines = Files.lines(path)) {
            return lines.skip(1)
                    .map(String::trim)
                    .map(CampeonatoGolsProcessador::parseCsvLine)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Optional<CampeonatoGols> parseCsvLine(String line) {
        try {
            String[] parts = line.split(",", -1);
            if(parts.length < 6) {
                System.err.println("Linha ignorada por formato inválido: " + line);
                return Optional.empty();
            }
            Function<String, Integer> avaliarExpressao = s -> {
                ScriptEngineManager factory = new ScriptEngineManager();
                ScriptEngine engine = factory.getEngineByName("JavaScript");
                try {
                    Object resultado = engine.eval(s);
                    return ((Double) resultado).intValue();
                } catch (ScriptException e) {
                    e.printStackTrace();
                    return 0;
                }
            };

            int partida_id = Integer.parseInt(unquote(parts[0].trim()));
            int rodata = Integer.parseInt(unquote(parts[1].trim()));
            String clube = unquote(parts[2].trim());
            String atleta = unquote(parts[3].trim());
            int minuto = 0;
            try  {
                minuto = Integer.parseInt(unquote(parts[4].trim()));
            } catch (NumberFormatException e) {
                try {
                    String minutoStr = unquote(parts[4].trim());
                    String[] partes = minutoStr.split("\\+");
                    for (String parte : partes) {
                        try {
                            minuto += Integer.parseInt(parte.trim());
                        } catch (NumberFormatException e1) { }
                    }
                }catch (NumberFormatException e1) { }
            }
            String tipo_de_gol = unquote(parts[5].trim());
            return Optional.of(new CampeonatoGols(partida_id, rodata, clube, atleta, minuto, tipo_de_gol));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private static String toCsvLine(CampeonatoGols campeonato) {
        return String.join(",",
                escapeCsv(String.valueOf(campeonato.getPartida_id())),
                escapeCsv(String.valueOf(campeonato.getRodata())),
                escapeCsv(campeonato.getClube()),
                escapeCsv(campeonato.getAtleta()),
                escapeCsv(String.valueOf(campeonato.getMinuto())),
                escapeCsv(campeonato.getTipo_de_gol()));
    }
}

