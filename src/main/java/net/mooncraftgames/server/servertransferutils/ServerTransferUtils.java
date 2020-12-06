package net.mooncraftgames.server.servertransferutils;

import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginLogger;
import net.mooncraftgames.server.servertransferutils.commands.CommandServerTransfer;

/**
 * Main plugin class. Can get the quicktransfer manager through here or directly.
 * @author CG360
 */
public class ServerTransferUtils extends PluginBase implements Listener {

    private static ServerTransferUtils plg;

    @Override
    public void onEnable(){
        plg = this;

        this.getServer().getCommandMap().register("ngapi", new CommandServerTransfer());
    }

    public PluginLogger getLogger(){ return super.getLogger(); }

    public static ServerTransferUtils get(){ return plg; }
    public static PluginLogger getPlgLogger(){ return plg.getLogger(); }

}
