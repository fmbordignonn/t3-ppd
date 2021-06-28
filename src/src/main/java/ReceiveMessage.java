package src.main.java;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;


public class ReceiveMessage extends Thread {
    private Processo process;

    public ReceiveMessage(Processo process) throws SocketException {
        this.process = process;
    }


    @Override
    public void run() {
        System.out.println("Iniciando ReceiveMessage thread");

        while (true) {
            try {
                byte[] bytesPacote = new byte[1024];
                DatagramPacket packet = new DatagramPacket(bytesPacote, bytesPacote.length);

                this.process.getSocket().receive(packet);

                InetAddress senderHost = packet.getAddress();
                int senderProcessPort = packet.getPort();

                // array new int[] {1,2,3,4} chega assim:
                // [1, 2, 3, 4]
                String response = new String(packet.getData(), 0, packet.getLength());

                System.out.println("Recebido mensagem: " + response);

                int[] receivedClock = Arrays.stream(response
                        .replace("[", "")
                        .replace("]", "")
                        .split(", "))
                        .mapToInt(Integer::parseInt)
                        .toArray();


                System.out.println("Print do recebimento de evento");

                //soma evento
                this.process.getRelogio()[this.process.getIdProcesso()] =
                        this.process.getRelogio()[this.process.getIdProcesso()]++;

                for (int i = 0; i < this.process.getRelogio().length; i++) {
                    if (i == this.process.getIdProcesso()) {
                        continue;
                    }

                    //pega do array
                    this.process.getRelogio()[i] = receivedClock[i];
                }

            } catch (IOException e) {
                //e.printStackTrace();
            }
        }

    }
}