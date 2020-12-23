package io.github.willqi.christmasgame;


import org.slf4j.LoggerFactory;

public class ChristmasGameMain {

    public static void main (String[] args) {

        int port = 80;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException exception) {
                LoggerFactory.getLogger(ChristmasGameMain.class).error("Defaulting to port 80 because an invalid port was given");
            }
        }

        ChristmasGameServer gameServer = new ChristmasGameServer();
        gameServer.startServer(port);
        System.out.println(String.format("Started Christmas game on port: %s", port));
    }

}
