package space.yurisi.universecorev2.subplugins.universeguns.core;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.item.gun.Gun;

public class GunStatus {

    private Gun gun;

    private int currentAmmo;

    private boolean isReloading;

    private long reloadEndTime;

    public GunStatus(Gun gun){
        this.gun = gun;
        this.currentAmmo = gun.getMagazineSize();
    }

    public int getCurrentAmmo(){
        return this.currentAmmo;
    }

    public boolean shoot() {
        if (this.currentAmmo <= 0 || this.isReloading) {
            return false;
        }
        this.currentAmmo--;
        return true;
    }

    public void startReload(int reloadTime) {
        this.isReloading = true;
        this.reloadEndTime = System.currentTimeMillis() + reloadTime;
    }

    public void finishReload() {
        this.isReloading = false;
        this.currentAmmo = gun.getMagazineSize();
    }

    public String getAmmoDisplay() {
        if (isReloading) {
            long remainingTime = getReloadRemainingTime();
            double seconds = remainingTime / 1000.0;
            return String.format("<< Reload %.1f >>", seconds);
        }
        return String.format("<< %d/%d >>", currentAmmo, gun.getMagazineSize());
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
