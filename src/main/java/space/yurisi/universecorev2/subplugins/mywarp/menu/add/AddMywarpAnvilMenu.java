package space.yurisi.universecorev2.subplugins.mywarp.menu.add;

import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.menu.BaseMenu;

import java.util.Arrays;
import java.util.Collections;

public class AddMywarpAnvilMenu implements BaseMenu {

    public void sendMenu(Player player){
        new AnvilGUI.Builder()
                .onClick((slot, stateSnapshot) -> {
                    if (slot != AnvilGUI.Slot.OUTPUT) {
                        return Collections.emptyList();
                    }

                    if (stateSnapshot.getText().equals("ワープ名を入力して下さい")) {
                        return Arrays.asList(AnvilGUI.ResponseAction.updateTitle("ワープ名を入力して下さい", true));
                    }
                    if (stateSnapshot.getText().length() > 20) {
                        return Arrays.asList(AnvilGUI.ResponseAction.updateTitle("20文字以下にして下さい。", true));
                    }

                    stateSnapshot.getPlayer().performCommand("mywarp add \"" + stateSnapshot.getText() + "\" false");
                    return Arrays.asList(AnvilGUI.ResponseAction.close());

                })
                .text("ワープ名を入力して下さい")
                .title("マイワープ作成")
                .plugin(UniverseCoreV2.getInstance())
                .open(player);
    }
}
