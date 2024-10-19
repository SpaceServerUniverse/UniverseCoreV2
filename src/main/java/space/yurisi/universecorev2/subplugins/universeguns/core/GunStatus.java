package space.yurisi.universecorev2.subplugins.universeguns.core;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.exception.AmmoNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.item.gun.Gun;
import space.yurisi.universecorev2.subplugins.universeguns.connector.UniverseCoreAPIConnector;

public class GunStatus {

    private Gun gun;

    private long inventoryAmmo;

    private int magazineAmmo;

    private boolean isReloading;

    private long reloadEndTime;

    private UniverseCoreAPIConnector connector;

    public GunStatus(Gun gun, UniverseCoreAPIConnector connector) {
        this.gun = gun;
        this.magazineAmmo = gun.getMagazineSize();
        this.connector = connector;
    }

    public void setInventoryAmmo(Player player, long inventoryAmmo) throws UserNotFoundException, AmmoNotFoundException {
        this.inventoryAmmo = inventoryAmmo;
        connector.setAmmoFromUserId(player, gun.getType(), inventoryAmmo);
    }

    public long getInventoryAmmo(Player player) throws UserNotFoundException, AmmoNotFoundException {
        return connector.getAmmoFromUserId(player, gun.getType());
    }

    public int getMagazineAmmo(){
        return this.magazineAmmo;
    }

    public boolean shoot() {
        if (this.magazineAmmo <= 0 || this.isReloading) {
            return false;
        }
        this.magazineAmmo--;
        return true;
    }

    public boolean startReload(int reloadTime, Player player) throws UserNotFoundException, AmmoNotFoundException {
        if(!connector.isExistsAmmoData(player)){
            connector.AmmoDataInit(player);
        }
        this.inventoryAmmo = getInventoryAmmo(player);
        if(inventoryAmmo <= 0){
            return false;
        }
        this.isReloading = true;
        this.reloadEndTime = System.currentTimeMillis() + reloadTime;
        return true;
    }

    public void finishReload(Player player) throws UserNotFoundException, AmmoNotFoundException {
        this.isReloading = false;
        long nowAllAmmo = getInventoryAmmo(player) + (long)magazineAmmo;
        connector.setAmmoFromUserId(player, gun.getType(), nowAllAmmo);
        if(nowAllAmmo <= gun.getMagazineSize()){
            this.magazineAmmo = (int) nowAllAmmo;
            setInventoryAmmo(player, 0);
        } else {
            setInventoryAmmo(player, nowAllAmmo - (long)(gun.getMagazineSize()));
            this.magazineAmmo = gun.getMagazineSize();
        }
    }

    public void updateAmmo(Player player, boolean isZoom) throws UserNotFoundException, AmmoNotFoundException {
        this.inventoryAmmo = getInventoryAmmo(player);
        updateActionBar(player, isZoom);
    }

    public String getAmmoDisplay() {
        if (isReloading) {
            long remainingTime = getReloadRemainingTime();
            double seconds = remainingTime / 1000.0;
            return String.format("<< Reload %.1f >>", seconds);
        }
        return String.format("<< %d/%d >>", magazineAmmo, this.inventoryAmmo);
    }

    public void cancelReload() {
        this.isReloading = false;
    }

    public boolean getIsReloading(){
        return this.isReloading;
    }

    public long getReloadRemainingTime() {
        return Math.max(0, reloadEndTime - System.currentTimeMillis());
    }

    public void updateActionBar(Player player, boolean isZoom) {
        String ammoDisplay = getAmmoDisplay();
        String messageText = (isZoom ? "ADS " : "") + ammoDisplay;
        Component message = Component.text(messageText);

        if (isReloading) {
            message = message.color(NamedTextColor.RED);
        }

        player.sendActionBar(message);
    }

}
