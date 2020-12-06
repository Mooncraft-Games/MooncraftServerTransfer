package net.mooncraftgames.server.servertransferutils.types;

import cn.nukkit.Player;
import net.mooncraftgames.server.servertransferutils.ServerTransferConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Pattern;

public class PermissionGate {

    private PermissionGate parent;

    private ArrayList<String> required;
    private ArrayList<String> blocked;

    public PermissionGate(){ this(null); }
    public PermissionGate(PermissionGate parent){
        this.parent = parent;
        this.required = new ArrayList<>();
        this.blocked = new ArrayList<>();
    }



    public boolean hasParent(){ return getParent().isPresent(); }
    public boolean checkPass(Player player){
        ArrayList<String> allowed = getRequired();
        ArrayList<String> denied = getBlocked();

        for(String b : denied) if(player.hasPermission(b)) return false; // If permission IS present: not allowed.
        for (String r: allowed) if(!player.hasPermission(r)) return false; // If permission ISN'T present: not allowed.

        return true;
    }



    public Optional<PermissionGate> getParent() { return Optional.ofNullable(parent); }
    public ArrayList<String> getLocalRequired() { return new ArrayList<>(required); }
    public ArrayList<String> getLocalBlocked() { return new ArrayList<>(blocked); }



    public ArrayList<String> getRequired() {
        Optional<PermissionGate> parentOptional = getParent();
        if(parentOptional.isPresent()){

            ArrayList<String> perms = getLocalRequired();
            perms.addAll(parentOptional.get().getRequired());
            return perms;

        } else return getLocalRequired();

    }

    public ArrayList<String> getBlocked() {
        Optional<PermissionGate> parentOptional = getParent();
        if(parentOptional.isPresent()){

            ArrayList<String> perms = getLocalBlocked();
            perms.addAll(parentOptional.get().getBlocked());
            return perms;

        } else return getLocalBlocked();
    }

}
