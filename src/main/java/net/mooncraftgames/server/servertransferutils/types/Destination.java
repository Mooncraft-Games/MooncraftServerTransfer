package net.mooncraftgames.server.servertransferutils.types;

import net.mooncraftgames.server.servertransferutils.ServerTransferConstants;

import java.util.regex.Pattern;

public final class Destination {

    private String address;
    private Integer port;

    public Destination(){ this(null, null); }
    public Destination(String address) { this(address, null); }
    public Destination(String address, Integer port) {
        this.setAddress(address);
        this.setPort(port);
    }

    public void resetAddress() { this.address = ServerTransferConstants.DEFAULT_ADDRESS; }
    public void resetPort() { this.port = ServerTransferConstants.DEFAULT_PORT; }

    public void setAddress(String address) {
        if(address == null) {
            resetAddress();
        } else {
            String addr;

            if(address.contains(":")){
                String[] split = address.split(Pattern.quote(":"));
                addr = split[0];

                try {
                    String portString = split[1];
                    this.setPort(Integer.parseInt(portString));
                } catch (Exception ignored){ }

            } else {
                 addr = address;
            }

            this.address = addr;
        }
    }

    public void setPort(Integer port) {
        if(port == null){
            resetPort();
        } else {
            this.port = port;
        }
    }

    public String getAddress() { return address; }
    public Integer getPort() { return port; }
}
