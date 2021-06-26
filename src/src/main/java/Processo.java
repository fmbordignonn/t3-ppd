package src.main.java;

//import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Array;
import java.net.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Processo extends Thread {

    private final int idProcesso;
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

    private final String[] otherHosts;
    private final int[] otherPorts;


    public Processo(int idProcesso, String host, int port, int chance, int eventCount, int minDelay, int maxDelay, int[] relogio) throws SocketException {
        this.idProcesso = idProcesso;
        this.host = host;
        this.port = port;
        this.chance = chance;
        this.eventCount = eventCount;
        this.minDelay = minDelay;
        this.maxDelay = maxDelay;
        this.relogio = relogio;

        this.socket = new DatagramSocket(port);

        /*
            adicionar o processo atual nessa lista tbm, pq ai no recebimento conseguimos saber pelo host:port qual é o id
            do processo que enviou
        */
        //this.otherProcesses;

        throw new RuntimeException("faz a lista de processos");
    }

    @Override
    public void run() {
        int delay;
        int destination;

        for (int i = 0; i < eventCount; i++) {
            delay = ThreadLocalRandom.current().nextInt(minDelay, maxDelay);

            //revisar dps
            if (ThreadLocalRandom.current().nextDouble(0, 1) < chance) {

                destination = ThreadLocalRandom.current().nextInt(otherHosts.length);

                try {
                    packet = new DatagramPacket(Arrays.toString(relogio).getBytes(), packetBytes.length,
                            InetAddress.getByName(otherHosts[destination]), otherPorts[destination]);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }

                relogio[idProcesso] = relogio[idProcesso]++;

                continue;
            }

            System.out.println("Evento local " + Arrays.toString(relogio));

            relogio[idProcesso] = relogio[idProcesso]++;

            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }


    public int getIdProcesso() {
        return idProcesso;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public double getChance() {
        return chance;
    }

    public int getEventCount() {
        return eventCount;
    }

    public int getMinDelay() {
        return minDelay;
    }

    public int getMaxDelay() {
        return maxDelay;
    }

    public int[] getRelogio() {
        return relogio;
    }

    public DatagramSocket getSocket() {
        return socket;
    }

    public DatagramPacket getPacket() {
        return packet;
    }

    public byte[] getPacketBytes() {
        return packetBytes;
    }

    public String[] getOtherHosts() {
        return otherHosts;
    }

    public int[] getOtherPorts() {
        return otherPorts;
    }
}


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
