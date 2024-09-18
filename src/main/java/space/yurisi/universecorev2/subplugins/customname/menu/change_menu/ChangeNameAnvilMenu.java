package space.yurisi.universecorev2.subplugins.customname.menu.change_menu;

import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.DatabaseManager;
import space.yurisi.universecorev2.database.models.CustomName;
import space.yurisi.universecorev2.database.models.User;
import space.yurisi.universecorev2.database.repositories.CustomNameRepository;
import space.yurisi.universecorev2.database.repositories.UserRepository;
import space.yurisi.universecorev2.exception.CustomNameNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.menu.BaseMenu;
import space.yurisi.universecorev2.subplugins.mywarp.utils.MessageHelper;
import space.yurisi.universecorev2.utils.Message;
import space.yurisi.universecorev2.utils.PlayerState;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangeNameAnvilMenu implements BaseMenu {

    public void sendMenu(Player player){
        new AnvilGUI.Builder()
                .onClick((slot, stateSnapshot) -> {

                    String warp_name = stateSnapshot.getText();
                    if (slot != AnvilGUI.Slot.OUTPUT) {
                        return Collections.emptyList();
                    }

                    if (warp_name.equals("入力して下さい")) {
                        return List.of(AnvilGUI.ResponseAction.updateTitle("称号を入力して下さい", true));
                    }
                    if (warp_name.length() > 20) {
                        return List.of(AnvilGUI.ResponseAction.updateTitle("20文字以下にして下さい。", true));
                    }

                    DatabaseManager dbManager = UniverseCoreV2API.getInstance().getDatabaseManager();
                    UserRepository userRepo = dbManager.getUserRepository();
                    CustomNameRepository customNameRepo = dbManager.getCustomNameRepository();

                    try {
                        User user = userRepo.getUserFromUUID(player.getUniqueId());
                        if(!customNameRepo.existsCustomNameFromUserId(user.getId())) {
                            customNameRepo.createCustomName(user);
                        }
                        CustomName customName = customNameRepo.getCustomNameFromUserId(user.getId());
                        customName.setDisplay_custom_tag(stateSnapshot.getText().replace(":", "§"));
                        customNameRepo.updateCustomName(customName);

                        Message.sendSuccessMessage(player,"[名誉AI]", "設定しました！");
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, 1);

                        PlayerState.setDisplayName(player);
                    } catch (UserNotFoundException e) {
                        Message.sendErrorMessage(player,"[異常なエラー]", "ユーザーデータが見つかりませんでした。");
                    } catch (CustomNameNotFoundException e) {
                        Message.sendErrorMessage(player,"[異常なエラー]", "称号データが見つかりませんでした。");
                    }

                    return List.of(AnvilGUI.ResponseAction.close());

                })
                .text("入力して下さい")
                .title("称号(10000$)")
                .plugin(UniverseCoreV2.getInstance())
                .open(player);
    }
}
