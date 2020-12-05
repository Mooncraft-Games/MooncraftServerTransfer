package net.mooncraftgames.server.servertransferutils;

import cn.nukkit.Player;
import cn.nukkit.network.protocol.TransferPacket;
import cn.nukkit.utils.TextFormat;

public class Utility {

    public static final TextFormat DEFAULT_TEXT_COLOUR = TextFormat.GRAY;

    public static String generateServerMessage(String topic, TextFormat topicColour, String text){
        return generateServerMessage(topic, topicColour, text, DEFAULT_TEXT_COLOUR);
    }

    public static String generateServerMessage(String topic, TextFormat topicColour, String text, TextFormat defaultTextColour){
        return String.format("%s%s%s %s%s>> %s%s%s", topicColour, TextFormat.BOLD, topic, TextFormat.DARK_GRAY, TextFormat.BOLD, TextFormat.RESET, defaultTextColour, text);
    }

    public static void transfer(Player player, String address, Integer port){
        int realPort = port == null ? 19132 : port;

        TransferPacket packet = new TransferPacket();
        packet.address = address;
        packet.port = realPort;
        player.directDataPacket(packet);
    }

}
