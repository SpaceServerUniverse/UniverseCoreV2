package space.yurisi.universecorev2.subplugins.mywarp.menu.visit_menu;

import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.menu.BaseMenu;
import space.yurisi.universecorev2.exception.MywarpNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.mywarp.menu.list_menu.item.WarpMenuItem;
import space.yurisi.universecorev2.subplugins.mywarp.menu.visit_menu.VisitMywarpInventoryMenu;
import space.yurisi.universecorev2.subplugins.mywarp.connector.UniverseCoreAPIConnector;
import xyz.xenondevs.invui.item.Item;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class VisitMywarpAnvilMenu implements BaseMenu {

    private UniverseCoreAPIConnector connector;

    public VisitMywarpAnvilMenu(UniverseCoreAPIConnector connector){
        this.connector = connector;
    }

    public void sendMenu(Player player){
        new AnvilGUI.Builder().onClick((slot, stateSnapshot) -> {
                    String warp_name = stateSnapshot.getText();
                    if (slot != AnvilGUI.Slot.OUTPUT) {
                        return Collections.emptyList();
                    }

                    if (warp_name.equals("プレイヤー名を入力して下さい")) {
                        return Arrays.asList(AnvilGUI.ResponseAction.updateTitle("プレイヤー名を入力して下さい", true));
                    }

                    if (warp_name.length() > 20) {
                        return Arrays.asList(AnvilGUI.ResponseAction.updateTitle("20文字以下にして下さい。", true));
                    }

                    Pattern pattern = Pattern.compile("[\\u3000\\u0020]");
                    Matcher matcher = pattern.matcher(warp_name);

                    if (matcher.find()) {
                        return Arrays.asList(AnvilGUI.ResponseAction.updateTitle("空白は入れないで下さい。", true));
                    }

                    String playerName = stateSnapshot.getText();
                    try {
                        Player targetPlayer = Bukkit.getOfflinePlayer(playerName).getPlayer();
                        if (targetPlayer == null) {
                            throw new UserNotFoundException("プレイヤーが見つかりませんでした。");
                        }
                        List<Item> items = this.connector.getMywarpList(targetPlayer).stream()
                                .map(mywarp -> new WarpMenuItem(connector, mywarp))
                                .collect(Collectors.toList());
                        VisitMywarpInventoryMenu visitMenu = new VisitMywarpInventoryMenu(this.connector, playerName);
                        visitMenu.sendMenu(player);
                    } catch (MywarpNotFoundException e) {
                        return Arrays.asList(AnvilGUI.ResponseAction.updateTitle("ワープが見つかりませんでした。", true));
                    }  catch (UserNotFoundException e) {
                        return Arrays.asList(AnvilGUI.ResponseAction.updateTitle("プレイヤーが見つかりませんでした。", true));
                    }
                    return Arrays.asList(AnvilGUI.ResponseAction.close());
                })
                .text("プレイヤー名を入力して下さい")
                .title("マイワープ訪問")
                .plugin(UniverseCoreV2.getInstance())
                .open(player);
    }
}
