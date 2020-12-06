package net.mooncraftgames.server.servertransferutils.types;

/**
 * Links a Destination type with a PermissionStore type.
 * @author CG360
 */
public final class ProtectedDestination {

    private Destination destination;
    private PermissionStore gatekeeper;

    public ProtectedDestination(Destination destination, PermissionStore gatekeeper) {
        this.destination = destination == null ? new Destination() : destination;
        this.gatekeeper = gatekeeper == null ? new PermissionStore() : gatekeeper;
    }

    public void setDestination(Destination destination) { this.destination = destination; }
    public void setGatekeeper(PermissionStore gatekeeper) { this.gatekeeper = gatekeeper; }

    public Destination getDestination() { return destination; }
    public PermissionStore getGatekeeper() { return gatekeeper; }
}
