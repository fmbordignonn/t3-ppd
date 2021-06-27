package src.main.java;

//import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class Processo extends Thread {

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


    public Processo(int idProcesso, String host, int port, double chance, int eventCount, int minDelay, int maxDelay, String[] otherHosts, int[] otherPorts) throws SocketException {
        this.idProcesso = idProcesso;
        this.host = host;
        this.port = port;
        this.chance = chance;
        this.eventCount = eventCount;
        this.minDelay = minDelay;
        this.maxDelay = maxDelay;

        this.otherHosts = otherHosts;
        this.otherPorts = otherPorts;

        this.relogio = new int[otherHosts.length];

        this.socket = new DatagramSocket(port);
    }

    @Override
    public void run() {
        int delay;
        int destination;

        for (int i = 0; i < eventCount; i++) {
            delay = ThreadLocalRandom.current().nextInt(minDelay, maxDelay);

            relogio[idProcesso] = relogio[idProcesso]++;

            //revisar dps
            if (ThreadLocalRandom.current().nextDouble(0, 1) < chance) {

                do {
                    destination = ThreadLocalRandom.current().nextInt(otherHosts.length);
                }
                while (destination == idProcesso);

                try {

                    System.out.println("ID processo: " + this.idProcesso +
                            " Envio de mensagem " + Arrays.toString(this.relogio) +
                            " ID destinatário: " + destination);

                    packet = new DatagramPacket(Arrays.toString(relogio).getBytes(), packetBytes.length,
                            InetAddress.getByName(otherHosts[destination]), otherPorts[destination]);

                    socket.send(packet);

                } catch (IOException ex) {
                    System.out.println("Erro ao enviar msg para host remoto: " + ex.getMessage());
                }

                continue;
            }

            //evento local
            System.out.println("ID processo: " + this.idProcesso + " Evento local " + Arrays.toString(relogio));

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


