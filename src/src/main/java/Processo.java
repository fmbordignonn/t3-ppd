package src.main.java;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Processo extends Thread {

    private final int id;
    private final String host;
    private final int port;
    private final double chance;
    private final int eventCount;
    private final int minDelay;
    private final int maxDelay;
    private final int[] relogio;

    private final DatagramSocket socket;

    private DatagramPacket packet;
    private final byte[] packetBytes = new byte[4028];

    private List<String> otherProcesses;

    public Processo(int id, String host, int port, int chance, int eventCount, int minDelay, int maxDelay, int[] relogio) throws SocketException {
        this.id = id;
        this.host = host;
        this.port = port;
        this.chance = chance;
        this.eventCount = eventCount;
        this.minDelay = minDelay;
        this.maxDelay = maxDelay;
        this.relogio = relogio;

        this.socket = new DatagramSocket(port);

        //this.otherProcesses;

        throw new RuntimeException("faz a lista de processos");
    }

    @Override
    public void run() {
        int delay;
        String destination;

        for (int i = 0; i < eventCount; i++) {
            delay = ThreadLocalRandom.current().nextInt(minDelay, maxDelay);

            //revisar dps
            if (ThreadLocalRandom.current().nextDouble(0, 1) < chance) {

                ObjectMapper mapper = new ObjectMapper();

                //packet = new DatagramPacket();


                continue;
            }

            System.out.println("ROLOU EVENTO LOCAL");
            relogio[id] = relogio[id]++;

            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }


}

//
//    private final int id;
//
//    private final String host;
//
//    private final int port;
//
//    private final int chance;
//
//    private int quantidadeEventos;
//
//    private final int min_delay;
//
//    private final int max_delay;
//
//    private final int[] relogio;
//
//    // NEED TO DEFINE FILE PATH
//    private final String filePath = "C:\\Users\\feeel\\Desktop\\arquivo.txt";
//
//    public Processo(int id, String host, int port, int chance, int quantidadeEventos, int min_delay, int max_delay, int[] relogio) {
//        this.id = id;
//        this.host = host;
//        this.port = port;
//        this.chance = chance;
//        this.quantidadeEventos = quantidadeEventos;
//        this.min_delay = min_delay;
//        this.max_delay = max_delay;
//        this.relogio = relogio;
//    }
//
//    @Override
//    public void run() {
//        System.out.println(id + " iniciado!");
//        try {
//            DatagramSocket socket = new DatagramSocket(port, InetAddress.getByName("localhost"));
//
//            socket.setSoTimeout(min_delay);
//
//            Evento[] events = new Evento[quantidadeEventos];
//
//            for (int i=0; i<quantidadeEventos; i++) {
//                switch (events) {
//                    case Evento.LOCAL:
//                        System.out.println("Evento local: " + id + "relogio" + relogio);
//                        break;
//
//                    case Evento.RECEBIMENTO:
//                        byte[] bytesPacote = "LOCK".getBytes();
//
//                        DatagramPacket packet = new DatagramPacket(bytesPacote, bytesPacote.length, InetAddress.getByName("localhost"), coordenatorPort);
//
//
//                        System.out.println("Mandando requisição de acesso...");
//
//                        socket.send(packet);
//
//                        String response = new String(packet.getData(), 0, packet.getLength());
//
//                        if (response.equals("ACK")) {
//
//                            List<String> lines = read();
//
//                            write(lines);
//
//                            bytesPacote = "UNLOCK".getBytes();
//
//                            System.out.println("Enviando resposta para liberar arquivo...");
//
//                            packet = new DatagramPacket(bytesPacote, bytesPacote.length, InetAddress.getByName("localhost"), coordenatorPort);
//
//                            socket.send(packet);
//
//                        }
//                        System.out.println("Envio de mensagem: " + id + "relogio" + relogio + "destinatario:" + id);
//                        break;
//
//                    case Evento.RECEBIMENTO:
//                        DatagramPacket packet = new DatagramPacket(bytesPacote, bytesPacote.length, InetAddress.getByName("localhost"), coordenatorPort);
//
//                        socket.receive(packet);
//
//                        String response = new String(packet.getData(), 0, packet.getLength());
//                        System.out.println("Recebimento mensagem: " + id + "relogio depois recebido: " + relogio + "enviador: " + id + "relogio + mensagem: " + relogio);
//                        break;
//
//                    default:
//                        throw new RuntimeException("deu merda");
//                }
//            }
//            socket.setSoTimeout(max_delay);
//        } catch(Exception ex){
//            ex.printStackTrace();
//        }
//    }
//
//    private List<String> read() throws FileNotFoundException {
//        Path path = Paths.get(filePath);
//
//        File file = path.toFile();
//
//        FileReader fr = new FileReader(file);
//
//        BufferedReader reader = new BufferedReader(fr);
//
//        return reader.lines().collect(Collectors.toList());
//    }
//
//    private void write(List<String> lines) throws IOException {
//        for (int i = 0; i < 50; i++) {
//            String line = processName + " - " + i + "\n";
//
//            lines.add(line);
//            Files.write(Paths.get(filePath), line.getBytes(), StandardOpenOption.APPEND);
//
//        }
//    }
