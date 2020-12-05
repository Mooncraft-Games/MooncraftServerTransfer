package net.mooncraftgames.server.servertransferutils;

import cn.nukkit.Player;
import cn.nukkit.network.protocol.TransferPacket;
import cn.nukkit.utils.TextFormat;
import net.mooncraftgames.server.servertransferutils.types.Destination;

import static net.mooncraftgames.server.servertransferutils.ServerTransferConstants.DEFAULT_TEXT_COLOUR;

public class Utility {

    public static String generateServerMessage(String topic, TextFormat topicColour, String text){
        return generateServerMessage(topic, topicColour, text, DEFAULT_TEXT_COLOUR);
    }

    public static String generateServerMessage(String topic, TextFormat topicColour, String text, TextFormat defaultTextColour){
        return String.format("%s%s%s %s%s>> %s%s%s", topicColour, TextFormat.BOLD, topic, TextFormat.DARK_GRAY, TextFormat.BOLD, TextFormat.RESET, defaultTextColour, text);
    }

    public static void transfer(Player player, Destination destination){

        TransferPacket packet = new TransferPacket();
        packet.address = destination.getAddress();
        packet.port = destination.getPort();
        player.directDataPacket(packet);
    }

}
