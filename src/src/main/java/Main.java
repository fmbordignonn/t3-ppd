package src.main.java;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.SocketException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, SocketException {

        int linhaDoArquivoIssoTemQueSerPorLinhaDeComando = 1;

        System.out.println("Iniciando app");

        System.out.println("controlar aqui o inicio de todos processos");

        List<String> lines = read("C:\\Users\\Felipe\\Desktop\\t3\\t3-ppd\\src\\src\\main\\java\\config.txt");

        String[] thisProcessConfig = lines.get(linhaDoArquivoIssoTemQueSerPorLinhaDeComando).split(" ");

        String[] otherHosts = new String[lines.size()];
        int[] otherPorts = new int[lines.size()];

        String host;
        int port;

        for (int i = 0; i < lines.size(); i++) {
            String[] split = lines.get(i).split(" ");

            host = split[0];
            port = Integer.parseInt(split[1]);

            otherHosts[i] = host;
            otherPorts[i] = port;

        }

        new Processo(
                Integer.parseInt(thisProcessConfig[0]),
                thisProcessConfig[1],
                Integer.parseInt(thisProcessConfig[2]),
                Double.parseDouble(thisProcessConfig[3]),
                Integer.parseInt(thisProcessConfig[4]),
                Integer.parseInt(thisProcessConfig[5]),
                Integer.parseInt(thisProcessConfig[6]),
                otherHosts,
                otherPorts)
                .start();
    }

    private static List<String> read(String filePath) throws FileNotFoundException {
        Path path = Paths.get(filePath);

        FileReader fr = new FileReader(path.toFile());

        BufferedReader reader = new BufferedReader(fr);

        return reader.lines().collect(Collectors.toList());
    }
}
