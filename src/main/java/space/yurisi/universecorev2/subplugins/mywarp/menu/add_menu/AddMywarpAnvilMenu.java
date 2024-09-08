package space.yurisi.universecorev2.subplugins.mywarp.menu.add_menu;

import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.menu.BaseMenu;

import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddMywarpAnvilMenu implements BaseMenu {

    public void sendMenu(Player player){
        new AnvilGUI.Builder()
                .onClick((slot, stateSnapshot) -> {

                    String warp_name = stateSnapshot.getText();
                    if (slot != AnvilGUI.Slot.OUTPUT) {
                        return Collections.emptyList();
                    }

                    if (warp_name.equals("ワープ名を入力して下さい")) {
                        return Arrays.asList(AnvilGUI.ResponseAction.updateTitle("ワープ名を入力して下さい", true));
                    }
                    if (warp_name.length() > 20) {
                        return Arrays.asList(AnvilGUI.ResponseAction.updateTitle("20文字以下にして下さい。", true));
                    }

                    Pattern pattern = Pattern.compile("[\\u3000\\u0020]");
                    Matcher matcher = pattern.matcher(warp_name);

                    if (matcher.find()) {
                        return Arrays.asList(AnvilGUI.ResponseAction.updateTitle("空白は入れないで下さい。", true));
                    }

                    stateSnapshot.getPlayer().performCommand("mywarp add " + stateSnapshot.getText() + " false");
                    return Arrays.asList(AnvilGUI.ResponseAction.close());

                })
                .text("ワープ名を入力して下さい")
                .title("マイワープ作成")
                .plugin(UniverseCoreV2.getInstance())
                .open(player);
    }
}
