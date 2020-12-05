package net.mooncraftgames.server.servertransferutils.commands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.network.protocol.TransferPacket;
import cn.nukkit.utils.TextFormat;
import net.mooncraftgames.server.servertransferutils.ServerTransferUtils;
import net.mooncraftgames.server.servertransferutils.Utility;

public class CommandServerTransfer extends PluginCommand<ServerTransferUtils> {

    public static final int DEFAULT_PORT = 19132;

    public CommandServerTransfer() {
        super("servertransfer", ServerTransferUtils.get());
        this.setDescription("Takes people to different servers.");
        this.setUsage("/servertransfer <string: IP> <int: port>");

        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                CommandParameter.newType("sessionid", CommandParamType.STRING)
        });
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {

        if (!sender.isPlayer()) {
            sender.sendMessage(Utility.generateServerMessage("ERROR", TextFormat.DARK_RED, "You must be a player to execute this command.", TextFormat.RED));
            return true;
        }

        Player player = (Player) sender;

        if(args.length < 1){
            sender.sendMessage(Utility.generateServerMessage("ERROR", TextFormat.DARK_RED, "Missing Arguments: "+getUsage(), TextFormat.RED));
        } else {
            String address = args[0];
            int port;

            // Get port. Only use default if parameter is not present.
            if(args.length > 1) {
                try {
                    String portString = args[1];
                    port = Integer.parseInt(portString);
                } catch (Exception err) {
                    sender.sendMessage(Utility.generateServerMessage("ERROR", TextFormat.DARK_RED, "Argument 'port' was not of type 'int': " + getUsage(), TextFormat.RED));
                    return true;
                }
            } else {
                port = DEFAULT_PORT;
            }

            sender.sendMessage(Utility.generateServerMessage("TRANSFER", TextFormat.BLUE, "Preparing to server transfer...", TextFormat.GRAY));
            Utility.transfer(player, address, port);
            sender.sendMessage(Utility.generateServerMessage("TRANSFER", TextFormat.BLUE, "Well if you're still here, something went wrong. :(", TextFormat.GRAY));
        }

        return true;
    }
}
