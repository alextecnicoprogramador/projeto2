import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class CampeonatoMain {
    public static void main(String[] args) throws IOException {
        Path gols = Paths.get("campeonato-brasileiro-gols.csv");
        Path cartoes = Paths.get("campeonato-brasileiro-cartoes.csv");
        Path estatisticas = Paths.get("campeonato-brasileiro-estatisticas-full.csv");
        Path full = Paths.get("campeonato-brasileiro-full.csv");

        //System.out.println("Lendo arquivo de " + cartoes);
        List<CampeonatoCartoes> resultadoCartoes = CampeonatoCartoesProcessador.readFromCsv(cartoes);

        /// O jogador que mais recebeu cartões vermelhos
        System.out.println("==================================================================================");
        Map<String, Long> maisVermelhos = resultadoCartoes.stream()
                .filter(c -> "vermelho".equalsIgnoreCase(c.getCartao()))
                .collect(Collectors.groupingBy(CampeonatoCartoes::getAtleta, Collectors.counting()));
        long maxVermelhos = maisVermelhos.values().stream()
                .max(Long::compareTo)
                .orElse(0L);
        List<Map.Entry<String, Long>> vermelhos = maisVermelhos.entrySet().stream()
                .filter(entry -> entry.getValue() == maxVermelhos)
                .collect(Collectors.toList());
        System.out.println("O jogador que mais recebeu cartões vermelhos teve "+maxVermelhos+" cartões:");
        for(Map.Entry<String, Long> entry : vermelhos) {
            System.out.println("Jogador: " + entry.getKey());
        }

        /// O jogador que mais recebeu cartões amarelos
        System.out.println("==================================================================================");
        Map<String, Long> maisAmarelos = resultadoCartoes.stream()
                .filter(c -> "amarelo".equalsIgnoreCase(c.getCartao()))
                .collect(Collectors.groupingBy(CampeonatoCartoes::getAtleta, Collectors.counting()));
        long maxAmarelos = maisAmarelos.values().stream()
                .max(Long::compareTo)
                .orElse(0L);
        List<Map.Entry<String, Long>> amarelos = maisAmarelos.entrySet().stream()
                .filter(entry -> entry.getValue() == maxAmarelos)
                .collect(Collectors.toList());
        System.out.println("O jogador que mais recebeu cartões amarelos teve "+maxAmarelos+" cartões:");
        for(Map.Entry<String, Long> entry : amarelos) {
            System.out.println("Jogador: " + entry.getKey());
        }

        List<CampeonatoFull> resultadoFull = CampeonatoFullProcessador.readFromCsv(full);

        /// O time que mais venceu jogos no ano 2008
        System.out.println("==================================================================================");
        Map<String, Long> maisJogos = resultadoFull.stream()
                .filter(a ->a.getData().getYear() == 2008)
                .filter(a ->!a.getVencedor().isEmpty() && !a.getVencedor().contains("-"))
                .collect(Collectors.groupingBy(CampeonatoFull::getVencedor, Collectors.counting()));
        long maxJogos = maisJogos.values().stream()
                .max(Long::compareTo)
                .orElse(0L);
        List<Map.Entry<String, Long>> mais = maisJogos.entrySet().stream()
                .filter(entry -> entry.getValue() == maxJogos)
                .collect(Collectors.toList());
        System.out.println("O time com mais vitórias em 2008:");
        for(Map.Entry<String, Long> entry : mais) {
            System.out.println("Time: " + entry.getKey()+" com " + entry.getValue()+" vitórias");
        }

        /// O Estado que teve menos jogos dentro do período 2003 e 2022
        System.out.println("==================================================================================");
        Map<String, Long> menosJogos = resultadoFull.stream()
                .filter(a ->a.getData().getYear() > 2002 && a.getData().getYear() < 2023)
                .filter(a ->!a.getVencedor().isEmpty() && !a.getVencedor().contains("-"))
                .collect(Collectors.groupingBy(CampeonatoFull::getMandante_Estado, Collectors.counting()));
        long minJogos = menosJogos.values().stream()
                .min(Long::compareTo)
                .orElse(0L);
        List<Map.Entry<String, Long>> menos = menosJogos.entrySet().stream()
                .filter(entry -> entry.getValue() == minJogos)
                .collect(Collectors.toList());
        System.out.println("O Estado que teve menos jogos dentro do período 2003 e 2022 foi:");
        for(Map.Entry<String, Long> entry : menos) {
            System.out.println("Estado: "+entry.getKey()+ " que teve "+entry.getValue()+" jogos");
        }

        /// O placar da partida com mais gols.
        System.out.println("==================================================================================");
        Optional<CampeonatoFull> maiorPlacar = resultadoFull.stream()
                .max(Comparator.comparingInt(a -> a.getMandante_Placar()+a.getVisitante_Placar()));
        CampeonatoFull maxplacar = maiorPlacar.get();
        int valorMaximo = maxplacar.getMandante_Placar() + maxplacar.getVisitante_Placar();
        List<CampeonatoFull> placar = resultadoFull.stream()
                .filter(entry -> valorMaximo == (entry.getMandante_Placar() + entry.getVisitante_Placar()))
                .collect(Collectors.toList());
        System.out.println("O placar da partida com mais gols foi:");
        for(CampeonatoFull jogo : placar) {
            System.out.println(jogo.getMandante()
                    +" ("+ jogo.getMandante_Placar()+") X "+
                    jogo.getVisitante()+ " com ("+ jogo.getVisitante_Placar()+")");
        }

        List<CampeonatoGols> resultadoGols = CampeonatoGolsProcessador.readFromCsv(gols);
        /// O jogador que mais fez gols de pênaltis
        System.out.println("==================================================================================");
        Map<String, Long> golsPenalti = resultadoGols.stream()
                .filter(a ->"penalty".equalsIgnoreCase(a.getTipo_de_gol()))
                .collect(Collectors.groupingBy(CampeonatoGols::getAtleta, Collectors.counting()));
        long maxgolsPenalti = golsPenalti.values().stream()
                .max(Long::compareTo)
                .orElse(0L);
        List<Map.Entry<String, Long>> penalti = golsPenalti.entrySet().stream()
                .filter(entry -> entry.getValue() == maxgolsPenalti)
                .collect(Collectors.toList());
        System.out.println("O jogador que mais fez gols por pênalti fez "+maxgolsPenalti+" gols:");
        for(Map.Entry<String, Long> entry : penalti) {
            System.out.println("Jogador: "+entry.getKey());
        }

        /// O jogador que mais fez gols contras
        System.out.println("==================================================================================");
        Map<String, Long> golsContra = resultadoGols.stream()
                .filter(a ->"Gol Contra".equalsIgnoreCase(a.getTipo_de_gol()))
                .collect(Collectors.groupingBy(CampeonatoGols::getAtleta, Collectors.counting()));
        long maxContra = golsContra.values().stream()
                .max(Long::compareTo)
                .orElse(0L);
        List<Map.Entry<String, Long>> contra = golsContra.entrySet().stream()
                .filter(entry -> entry.getValue() == maxContra)
                .collect(Collectors.toList());
        System.out.println("O jogador que mais fez gols contras fez "+maxContra+" gols:");
        for(Map.Entry<String, Long> entry : contra) {
            System.out.println("Jogador: "+entry.getKey());
        }
        System.out.println("==================================================================================");

        /// O jogador que mais fez gols
        Map<String, Long> maisGols = resultadoGols.stream()
                .collect(Collectors.groupingBy(CampeonatoGols::getAtleta, Collectors.counting()));
        long maxGols = maisGols.values().stream()
                .max(Long::compareTo)
                .orElse(0L);
        List<Map.Entry<String, Long>> mgols = maisGols.entrySet().stream()
                .filter(entry -> entry.getValue() == maxGols)
                .collect(Collectors.toList());
        System.out.println("O jogador que mais fez gols com o total de "+maxGols+" gols:");
        for(Map.Entry<String, Long> entry : mgols) {
            System.out.println("Jogador: "+entry.getKey());
        }
        System.out.println("==================================================================================");

//        List<CampeonatoEstatisticas> resultadoEstatisticas = CampeonatoEstatisticasProcessador.readFromCsv(estatisticas);
//        resultadoEstatisticas.stream();

    }
}
