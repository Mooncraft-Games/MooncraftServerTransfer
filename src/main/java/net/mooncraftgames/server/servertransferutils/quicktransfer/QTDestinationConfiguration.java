package net.mooncraftgames.server.servertransferutils.quicktransfer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.mooncraftgames.server.servertransferutils.types.PermissionStore;

import java.io.File;

public abstract class QTDestinationConfiguration {

    private File configLocation;
    private PermissionStore parentPermissionStore;

    public QTDestinationConfiguration(File configLocation) {
        this.configLocation = configLocation;
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
