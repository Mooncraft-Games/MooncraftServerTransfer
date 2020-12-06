package net.mooncraftgames.server.servertransferutils.types;

import cn.nukkit.Player;

import java.util.ArrayList;
import java.util.Optional;

/**
 * An object that stores permissions.
 * Required = Player MUST have all listed permissions.
 * Blocked = Players MUST NOT have ANY listed permissions.
 * @author CG360
 */
public class PermissionStore {

    private PermissionStore parent;

    private ArrayList<String> required;
    private ArrayList<String> blocked;

    public PermissionStore(){ this(null); }
    public PermissionStore(PermissionStore parent){
        this.parent = parent;
        this.required = new ArrayList<>();
        this.blocked = new ArrayList<>();
    }



    /** @return this for chaining. */
    public PermissionStore addRequiredPermission(String permission){
        if(permission != null) {
            blocked.remove(permission);
            required.add(permission);
        }
        return this;
    }

    /** @return this for chaining. */
    public PermissionStore removeRequiredPermission(String permission){
        required.remove(permission);
        return this;
    }

    /** @return this for chaining. */
    public PermissionStore addBlockedPermission(String permission){
        if(permission != null) {
            required.remove(permission);
            blocked.add(permission);
        }
        return this;
    }

    /** @return this for chaining. */
    public PermissionStore removeBlockedPermission(String permission){
        blocked.remove(permission);
        return this;
    }

    /** @return this for chaining. */
    public PermissionStore clearRequiredLocalPermissions(){
        required = new ArrayList<>();
        return this;
    }

    /** @return this for chaining. */
    public PermissionStore clearBlockedLocalPermissions(){
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



    public Optional<PermissionStore> getParent() { return Optional.ofNullable(parent); }
    public ArrayList<String> getLocalRequired() { return new ArrayList<>(required); }
    public ArrayList<String> getLocalBlocked() { return new ArrayList<>(blocked); }



    public ArrayList<String> getRequired() {
        Optional<PermissionStore> parentOptional = getParent();
        if(parentOptional.isPresent()){

            ArrayList<String> perms = getLocalRequired();
            for(String str: parentOptional.get().getRequired()){
                if(!perms.contains(str)) perms.add(str); // Avoid dupes.
            }
            return perms;

        } else return getLocalRequired();
    }

    public ArrayList<String> getBlocked() {
        Optional<PermissionStore> parentOptional = getParent();
        if(parentOptional.isPresent()){

            ArrayList<String> perms = getLocalBlocked();
            for(String str: parentOptional.get().getBlocked()){
                if(!perms.contains(str)) perms.add(str); // Avoid dupes.
            }
            return perms;

        } else return getLocalBlocked();
    }
}
