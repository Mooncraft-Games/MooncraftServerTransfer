package net.mooncraftgames.server.servertransferutils.commands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import net.mooncraftgames.server.servertransferutils.ServerTransferUtils;
import net.mooncraftgames.server.servertransferutils.Utility;
import net.mooncraftgames.server.servertransferutils.types.Destination;

public class CommandServerTransfer extends PluginCommand<ServerTransferUtils> {

    public CommandServerTransfer() {
        super("servertransfer", ServerTransferUtils.get());
        this.setDescription("Takes people to different servers.");
        this.setUsage("/servertransfer <string: IP> <int: port>");

        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                CommandParameter.newType("address", CommandParamType.STRING),
                CommandParameter.newType("port", CommandParamType.INT)
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
            Destination dest = new Destination();
            dest.setAddress(args[0]);

            // Get port. Only use default if parameter is not present.
            if(args.length > 1) {
                try {
                    String portString = args[1];
                    dest.setPort(Integer.parseInt(portString));
                } catch (Exception err) {
                    sender.sendMessage(Utility.generateServerMessage("ERROR", TextFormat.DARK_RED, "Argument 'port' was not of type 'int': " + getUsage(), TextFormat.RED));
                    return true;
                }
            }

            sender.sendMessage(Utility.generateServerMessage("TRANSFER", TextFormat.BLUE, "Preparing to server transfer...", TextFormat.GRAY));
            Utility.transfer(player, dest);
            sender.sendMessage(Utility.generateServerMessage("TRANSFER", TextFormat.BLUE, "Well if you're still here, something went wrong. :(", TextFormat.GRAY));
        }

        return true;
    }
}
