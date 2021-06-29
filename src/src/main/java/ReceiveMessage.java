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

                int senderIndex = 0;

                for (int i = 0; i < this.process.getOtherPorts().length; i++) {
                    if (senderProcessPort == this.process.getOtherPorts()[i]) {
                        senderIndex = i;
                        break;
                    }
                }

                System.out.println("Id local (i): " + this.process.getIdProcesso() +
                        " Relogio depois recebimento: " + Arrays.toString(this.process.getRelogio()) +
                        " Id remetente (s): " + senderIndex +
                        " Valor relógio recebido com a mensagem: " + Arrays.toString(receivedClock));

            } catch (IOException e) {
                //e.printStackTrace();
            }
        }

    }
}