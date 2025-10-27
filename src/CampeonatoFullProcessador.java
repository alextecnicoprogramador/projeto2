import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CampeonatoFullProcessador extends CampeonatoProcessador {

    public static void addToCsv(Path path, List<CampeonatoFull> campeonato) {

        boolean arquivoExiste = Files.exists(path);
        try(BufferedWriter writer =
                Files.newBufferedWriter(path,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND)) {
            if(!arquivoExiste) {
                writer.write("ID, rodata, data, hora, mandante, visitante, formacao_mandante, formacao_visitante, tecnico_mandante, tecnico_visitante, vencedor, arena, mandante_Placar, visitante_Placar, mandante_Estado, visitante_Estado");
                writer.newLine();
            } else {
                BufferedReader arquivo = Files.newBufferedReader(path);
                String primeiraLinha = arquivo.readLine();
                if(!primeiraLinha.toLowerCase().contains("partida_id")) {
                    writer.write("ID, rodata, data, hora, mandante, visitante, formacao_mandante, formacao_visitante, tecnico_mandante, tecnico_visitante, vencedor, arena, mandante_Placar, visitante_Placar, mandante_Estado, visitante_Estado");
                    writer.newLine();
                }
            }
            for(CampeonatoFull c : campeonato) {
                writer.write(toCsvLine(c));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<CampeonatoFull> readFromCsv(Path path) {
        if(!Files.exists(path)) {
            return new ArrayList<>();
        }
        try(Stream<String> lines = Files.lines(path)) {
            return lines.skip(1)
                    .map(String::trim)
                    .map(CampeonatoFullProcessador::parseCsvLine)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Optional<CampeonatoFull> parseCsvLine(String line) {
        try {
            String[] parts = line.split(",", -1);
            if(parts.length < 16) {
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

            int ID = Integer.parseInt(unquote(parts[0].trim()));
            int rodata = Integer.parseInt(unquote(parts[1].trim()));
            DateTimeFormatter Brasil = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate data = LocalDate.parse(unquote(parts[2].trim()), Brasil);
            LocalTime hora = LocalTime.parse(unquote(parts[3].trim()));
            String mandante = unquote(parts[4].trim());
            String visitante = unquote(parts[5].trim());
            String formacao_mandante = unquote(parts[6].trim());
            String formacao_visitante = unquote(parts[7].trim());
            String tecnico_mandante = unquote(parts[8].trim());
            String tecnico_visitante = unquote(parts[9].trim());
            String vencedor = unquote(parts[10].trim());
            String arena = unquote(parts[11].trim());
            int mandante_Placar = Integer.parseInt(unquote(parts[12].trim()));
            int visitante_Placar = Integer.parseInt(unquote(parts[13].trim()));
            String mandante_Estado = unquote(parts[14].trim());
            String visitante_Estado = unquote(parts[15].trim());

            return Optional.of(new CampeonatoFull(ID, rodata, data, hora, mandante, visitante, formacao_mandante, formacao_visitante, tecnico_mandante, tecnico_visitante, vencedor, arena, mandante_Placar, visitante_Placar, mandante_Estado, visitante_Estado));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private static String toCsvLine(CampeonatoFull campeonato) {
        return String.join(",",
                escapeCsv(String.valueOf(campeonato.getID())),
                escapeCsv(String.valueOf(campeonato.getRodata())),
                escapeCsv(String.valueOf(campeonato.getData())),
                escapeCsv(String.valueOf(campeonato.getHora())),
                escapeCsv(campeonato.getMandante()),
                escapeCsv(campeonato.getVisitante()),
                escapeCsv(campeonato.getFormacao_mandante()),
                escapeCsv(campeonato.getFormacao_visitante()),
                escapeCsv(campeonato.getTecnico_mandante()),
                escapeCsv(campeonato.getTecnico_visitante()),
                escapeCsv(campeonato.getVencedor()),
                escapeCsv(campeonato.getArena()),
                escapeCsv(String.valueOf(campeonato.getMandante_Placar())),
                escapeCsv(String.valueOf(campeonato.getVisitante_Placar())),
                escapeCsv(campeonato.getMandante_Estado()),
                escapeCsv(campeonato.getVisitante_Estado()));
    }
}

