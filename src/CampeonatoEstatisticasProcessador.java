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

public class CampeonatoEstatisticasProcessador extends CampeonatoProcessador {

    public static void addToCsv(Path path, List<CampeonatoEstatisticas> campeonato) {
        boolean arquivoExiste = Files.exists(path);
        try(BufferedWriter writer =
                    Files.newBufferedWriter(path,
                            StandardOpenOption.CREATE,
                            StandardOpenOption.APPEND)) {
            if(!arquivoExiste) {
                writer.write("partida_id, rodata, clube, chutes, chutes_no_alvo, posse_de_bola, passes, precisao_passes, faltas, cartao_amarelo, cartao_vermelho, impedimentos,escanteios");
                writer.newLine();
            } else {
                BufferedReader arquivo = Files.newBufferedReader(path);
                String primeiraLinha = arquivo.readLine();
                if(!primeiraLinha.toLowerCase().contains("partida_id")) {
                    writer.write("partida_id, rodata, clube, chutes, chutes_no_alvo, posse_de_bola, passes, precisao_passes, faltas, cartao_amarelo, cartao_vermelho, impedimentos,escanteios");
                    writer.newLine();
                }
            }
            for(CampeonatoEstatisticas c : campeonato) {
                writer.write(toCsvLine(c));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<CampeonatoEstatisticas> readFromCsv(Path path) {
        if(!Files.exists(path)) {
            return new ArrayList<>();
        }
        try(Stream<String> lines = Files.lines(path)) {
            return lines.skip(1)
                    .map(String::trim)
                    .map(CampeonatoEstatisticasProcessador::parseCsvLine)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Optional<CampeonatoEstatisticas> parseCsvLine(String line) {
        try {
            String[] parts = line.split(",", -1);
            if(parts.length < 13) {
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
            int chutes = Integer.parseInt(unquote(parts[3].trim()));
            int chutes_no_alvo = Integer.parseInt(unquote(parts[4].trim()));

            int posse_de_bola = 0;
            try  {
                posse_de_bola = Integer.parseInt(unquote(parts[5].trim()));
            } catch (NumberFormatException e) {
                try {
                    String minutoStr = unquote(parts[5].trim());
                    String[] partes = minutoStr.split("\\%");
                    for (String parte : partes) {
                        try {
                            posse_de_bola += Integer.parseInt(parte.trim());
                        } catch (NumberFormatException e1) { }
                    }
                }catch (NumberFormatException e1) { }
            }

            int passes = 0;
            try  {
                passes = Integer.parseInt(unquote(parts[6].trim()));
            } catch (NumberFormatException e) {
                try {
                    String minutoStr = unquote(parts[6].trim());
                    String[] partes = minutoStr.split("\\%");
                    for (String parte : partes) {
                        try {
                            passes += Integer.parseInt(parte.trim());
                        } catch (NumberFormatException e1) { }
                    }
                }catch (NumberFormatException e1) { }
            }

            int precisao_passes = 0;
            try  {
                precisao_passes = Integer.parseInt(unquote(parts[7].trim()));
            } catch (NumberFormatException e) {
                try {
                    String minutoStr = unquote(parts[7].trim());
                    String[] partes = minutoStr.split("\\%");
                    for (String parte : partes) {
                        try {
                            precisao_passes += Integer.parseInt(parte.trim());
                        } catch (NumberFormatException e1) { }
                    }
                }catch (NumberFormatException e1) { }
            }

            int faltas = 0;
            try  {
                faltas = Integer.parseInt(unquote(parts[8].trim()));
            } catch (NumberFormatException e) {
                try {
                    String minutoStr = unquote(parts[8].trim());
                    String[] partes = minutoStr.split("\\%");
                    for (String parte : partes) {
                        try {
                            faltas += Integer.parseInt(parte.trim());
                        } catch (NumberFormatException e1) { }
                    }
                }catch (NumberFormatException e1) { }
            }

            int cartao_amarelo = Integer.parseInt(unquote(parts[9].trim()));
            int cartao_vermelho = Integer.parseInt(unquote(parts[10].trim()));
            int impedimentos = Integer.parseInt(unquote(parts[11].trim()));
            int escanteios = Integer.parseInt(unquote(parts[12].trim()));

            return Optional.of(new CampeonatoEstatisticas(partida_id, rodata, clube, chutes, chutes_no_alvo, posse_de_bola, passes, precisao_passes, faltas, cartao_amarelo, cartao_vermelho, impedimentos,escanteios));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private static String toCsvLine(CampeonatoEstatisticas campeonato) {
        return String.join(",",
                escapeCsv(String.valueOf(campeonato.getPartida_id())),
                escapeCsv(String.valueOf(campeonato.getRodata())),
                escapeCsv(campeonato.getClube()),
                escapeCsv(String.valueOf(campeonato.getChutes())),
                escapeCsv(String.valueOf(campeonato.getChutes_no_alvo())),
                escapeCsv(String.valueOf(campeonato.getPosse_de_bola())),
                escapeCsv(String.valueOf(campeonato.getPasses())),
                escapeCsv(String.valueOf(campeonato.getPrecisao_passes())),
                escapeCsv(String.valueOf(campeonato.getFaltas())),
                escapeCsv(String.valueOf(campeonato.getCartao_amarelo())),
                escapeCsv(String.valueOf(campeonato.getCartao_vermelho())),
                escapeCsv(String.valueOf(campeonato.getImpedimentos())),
                escapeCsv(String.valueOf(campeonato.getEscanteios())));
    }
}


