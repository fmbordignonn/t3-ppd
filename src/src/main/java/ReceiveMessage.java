package src.main.java;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;


public class ReceiveMessage extends Thread {

    public DatagramSocket connectionSocket;



    @Override
    public void run() {

        while (true) {
            try {
                byte[] bytesPacote = new byte[1024];
                DatagramPacket packet = new DatagramPacket(bytesPacote, bytesPacote.length);
                connectionSocket.receive(packet);

                int senderProcessPort = packet.getPort();

                String response = new String(packet.getData(), 0, packet.getLength());

                System.out.println("Recebido mensagem: " + response);

                //int clockValues[] = response.split(",");


//                int actualClockValues[] = Processo.getClockValues();
//                int otherProcessPorts [] = Processo.getProcessPorts();

                //int senderProcessID = Arrays.asList(otherProcessPorts).indexOf(senderProcessPort);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}