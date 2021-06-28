package src.main.java;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        //args = new String[1];
        //args[0] = "0";
        //args[0] = "1";
        //args[0] = "2";
        //args[0] = "3";
        //args[0] = "4";
        //args[0] = "server";
        if (args.length < 1) {
            throw new RuntimeException("args invalido");
        }

        MulticastSocket multicastSocket = new MulticastSocket(5000);
        InetAddress grupo = InetAddress.getByName("230.0.0.1");
        multicastSocket.joinGroup(grupo);
        multicastSocket.setSoTimeout(100000);

        List<String> lines = read("C:\\Users\\Felipe\\Desktop\\t3\\t3-ppd\\src\\src\\main\\java\\config.txt");
        int processCount = lines.size();
        int processID = 0;
        Processo processo;

        try {
            //se der erro é server
            processID = Integer.parseInt(args[0]);

            System.out.println("Iniciando fluxo processo");

            String[] thisProcessConfig = lines.get(processID).split(" ");

            String[] otherHosts = new String[lines.size()];
            int[] otherPorts = new int[lines.size()];

            String host;
            int port;

            for (int i = 0; i < lines.size(); i++) {
                String[] split = lines.get(i).split(" ");

                host = split[1];
                port = Integer.parseInt(split[2]);

                otherHosts[i] = host;
                otherPorts[i] = port;
            }

            processo = new Processo(
                    Integer.parseInt(thisProcessConfig[0]),
                    thisProcessConfig[1],
                    Integer.parseInt(thisProcessConfig[2]),
                    Double.parseDouble(thisProcessConfig[3]),
                    Integer.parseInt(thisProcessConfig[4]),
                    Integer.parseInt(thisProcessConfig[5]),
                    Integer.parseInt(thisProcessConfig[6]),
                    otherHosts,
                    otherPorts);

            System.out.println("Processo criado com sucesso");

            System.out.println("Preparando mensagem pro multicast");

            String mensagem = "PROCESS READY";
            byte[] bytes = new byte[1024];
            bytes = mensagem.getBytes();
            DatagramPacket pacote = new DatagramPacket(bytes, bytes.length, grupo, 5000);
            multicastSocket.send(pacote);

            while (true) {
                try {
                    bytes = new byte[1024];
                    pacote = new DatagramPacket(bytes, bytes.length);
                    multicastSocket.receive(pacote);

                    mensagem = new String(pacote.getData(), 0, pacote.getLength());

                    if (mensagem.equals("START")) {
                        break;
                    } else {
                        System.out.println("chegou: " + mensagem);
                    }
                } catch (Exception ignored) {
                }
            }

            processo.start();

        } catch (NumberFormatException ex) {
            System.out.println("I'm a coordenator");
            waitProcessStart(processCount, multicastSocket, grupo);
        }
    }

    public static void waitProcessStart(int processCount, MulticastSocket multicastSocket, InetAddress grupo) throws IOException {

        DatagramPacket receivedPacket;
        byte[] entrada;
        String message;
        int joinedProcess = 0;

        System.out.println("Entrando no while pra esperar os processos");

        // processCount + 1 because coordenator also join the group
        //nn precisa ?
        while (joinedProcess != processCount) {

            try {
                entrada = new byte[1024];
                receivedPacket = new DatagramPacket(entrada, entrada.length);
                multicastSocket.receive(receivedPacket);
                message = new String(receivedPacket.getData(), 0, receivedPacket.getLength());

                if (message.equals("PROCESS READY")) {
                    joinedProcess++;
                }
            } catch (Exception ignored) {
                System.out.println("Timeout Exception....");
            }
        }

        message = "START";
        byte[] saida = new byte[1024];
        saida = message.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(saida, saida.length, grupo, 5000);
        multicastSocket.send(sendPacket);

        System.exit(1);

    }

    private static List<String> read(String filePath) throws FileNotFoundException {
        Path path = Paths.get(filePath);

        FileReader fr = new FileReader(path.toFile());

        BufferedReader reader = new BufferedReader(fr);

        return reader.lines().collect(Collectors.toList());
    }
}
