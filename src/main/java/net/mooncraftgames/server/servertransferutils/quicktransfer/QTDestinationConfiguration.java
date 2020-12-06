package net.mooncraftgames.server.servertransferutils.quicktransfer;

import com.google.gson.*;
import net.mooncraftgames.server.servertransferutils.ServerTransferUtils;
import net.mooncraftgames.server.servertransferutils.types.Destination;
import net.mooncraftgames.server.servertransferutils.types.PermissionStore;
import net.mooncraftgames.server.servertransferutils.types.ProtectedDestination;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Handles configuration specifically for the QuickTransfer feature.
 * @author CG360
 */
public abstract class QTDestinationConfiguration {

    private File configLocation;
    private PermissionStore parentPermissionStore;
    private ArrayList<ProtectedDestination> destinations;

    public QTDestinationConfiguration(File configLocation) {
        this.configLocation = configLocation;
        this.reloadFileFromDisk();
    }

    public boolean reloadFileFromDisk(){
        if(configLocation.exists() && configLocation.isFile()){
            try {
                FileReader reader = new FileReader(configLocation);
                BufferedReader read = new BufferedReader(reader);

                String jsonText = "";
                Iterator<String> r = read.lines().iterator();
                while (r.hasNext()){
                    jsonText = jsonText.concat(r.next());
                }

                JsonElement root = new JsonParser().parse(jsonText);
                return applyJsonToConfig(root);

            } catch (Exception err) {
                ServerTransferUtils.getPlgLogger().emergency("There was an error reading the config: "+configLocation.getAbsolutePath());
                err.printStackTrace();
            }
        }
        return false;
    }

    protected boolean applyJsonToConfig(JsonElement rootElement){
        if(rootElement instanceof JsonObject) {
            JsonObject root = (JsonObject) rootElement;
            JsonElement parentStore = root.get("permissions");
            JsonElement destinationStore = root.get("destinations");

            this.parentPermissionStore = parsePermissionStore(parentStore);
            this.destinations = parseDestinations(destinationStore, parentPermissionStore);
        }
        return false;
    }

    //TODO: Multiple configs can be loaded cause why not.
    // - Each config can have a parent permission group defined.
    // - Each destination can have sub-perms.
    public static PermissionStore parsePermissionStore(JsonElement permissions){ return parsePermissionStore(permissions, null); }
    public static PermissionStore parsePermissionStore(JsonElement permissions, PermissionStore parent){
        PermissionStore store = new PermissionStore(parent);

        if(permissions instanceof JsonObject){
            JsonObject storeObject = (JsonObject) permissions;
            JsonElement requiredElement = storeObject.get("required");
            JsonElement blockedElement = storeObject.get("blocked");
            // !! does not load any parent entries from config. Those are internal.

            if(requiredElement instanceof JsonArray){

                for(JsonElement reqEl : (JsonArray) requiredElement){

                    if(reqEl instanceof JsonPrimitive){
                        JsonPrimitive reqPrim = (JsonPrimitive) reqEl;
                        store.addRequiredPermission(reqPrim.getAsString());
                    }
                }
            }

            if(blockedElement instanceof JsonArray){
                for(JsonElement blkEl : (JsonArray) blockedElement){

                    if(blkEl instanceof JsonPrimitive){
                        JsonPrimitive blkPrim = (JsonPrimitive) blkEl;
                        store.addBlockedPermission(blkPrim.getAsString());
                    }
                }
            }
        }

        return store;
    }

    private static ArrayList<ProtectedDestination> parseDestinations (JsonElement destinations, PermissionStore parent){
        //TODO: load destinations.
    }

    /*


    {
        "permissions": {
            "required": [
                "blah.blahblah.blah",
                "blah2.electric.boogaloo"
            ],
            "blocked": [
                ""
            ]
        },

        "destinations": [
            "home": {
                "permissions": {
                    "required": [
                        "blah.blahblah.blah",
                        "blah2.electric.boogaloo"
                    ],
                    "blocked": [
                        ""
                    ]
                },
                "address": "127.1.69.420",
                "port": 19245
            },
            "shorter": {
                "address": "132.434.2.43:9999"
            }
        ]
    }


     */

}
