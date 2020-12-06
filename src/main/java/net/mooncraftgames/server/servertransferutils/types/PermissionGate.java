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



    /** @return this for chaining. */
    public PermissionGate addRequiredPermission(String permission){
        blocked.remove(permission);
        required.add(permission);
        return this;
    }

    /** @return this for chaining. */
    public PermissionGate removeRequiredPermission(String permission){
        required.remove(permission);
        return this;
    }

    /** @return this for chaining. */
    public PermissionGate addBlockedPermission(String permission){
        required.remove(permission);
        blocked.add(permission);
        return this;
    }

    /** @return this for chaining. */
    public PermissionGate removeBlockedPermission(String permission){
        blocked.remove(permission);
        return this;
    }

    /** @return this for chaining. */
    public PermissionGate clearRequiredLocalPermissions(){
        required = new ArrayList<>();
        return this;
    }

    /** @return this for chaining. */
    public PermissionGate clearBlockedLocalPermissions(){
        blocked = new ArrayList<>();
        return this;
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
            for(String str: parentOptional.get().getRequired()){
                if(!perms.contains(str)) perms.add(str); // Avoid dupes.
            }
            return perms;

        } else return getLocalRequired();
    }

    public ArrayList<String> getBlocked() {
        Optional<PermissionGate> parentOptional = getParent();
        if(parentOptional.isPresent()){

            ArrayList<String> perms = getLocalBlocked();
            for(String str: parentOptional.get().getBlocked()){
                if(!perms.contains(str)) perms.add(str); // Avoid dupes.
            }
            return perms;

        } else return getLocalBlocked();
    }
}
