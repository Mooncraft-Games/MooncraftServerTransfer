package net.mooncraftgames.server.servertransferutils.quicktransfer;

import java.util.ArrayList;

public abstract class QTDestinationConfiguration {

    private ArrayList<String> loadedConfigs;

    public QTDestinationConfiguration() {
        this.loadedConfigs = new ArrayList<>();
    }

    //TODO: Multiple configs can be loaded cause why not.
    // - Each config can have a parent permission group defined.
    // - Each destination can have sub-perms.

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
