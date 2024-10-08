package space.yurisi.universecorev2.subplugins.playerhead.menu;

import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.exception.MoneyNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.menu.BaseMenu;
import space.yurisi.universecorev2.subplugins.universeeconomy.UniverseEconomyAPI;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotReduceMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.ParameterException;
import space.yurisi.universecorev2.utils.Message;

public final class PlayerHeadMenu implements BaseMenu {

    public void sendMenu(Player player) {
        new AnvilGUI.Builder()
                .onClick((slot, stateSnapshot) -> {
                    if (slot == AnvilGUI.Slot.OUTPUT) {
                        try {

                            Long price = 10000L;
                            Long money = UniverseEconomyAPI.getInstance().getMoney(player);

                            if(money >= price){
                                UniverseEconomyAPI.getInstance().reduceMoney(player, price, "プレイヤーヘッド購入");

                                String inputName = stateSnapshot.getText();

                                ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD, 1);
                                SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
                                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(inputName);
                                skullMeta.setOwningPlayer(offlinePlayer);
                                playerHead.setItemMeta(skullMeta);

                                player.getInventory().addItem(playerHead);

                                Message.sendSuccessMessage(player, "[CloneAI]", "プレイヤーヘッドを購入しました");
                            }else{
                                Message.sendErrorMessage(player, "[CloneAI]", "決済処理に失敗しました:　u have no money :(");
                            }
                        } catch (UserNotFoundException e) {
                            Message.sendErrorMessage(player, "[CloneAI]", "決済処理に失敗しました: ユーザーデータが見つかりません");
                        } catch (MoneyNotFoundException e) {
                            Message.sendErrorMessage(player, "[CloneAI]", "決済処理に失敗しました: お金データが見つかりません");
                        } catch (ParameterException e) {
                            Message.sendErrorMessage(player, "[CloneAI]", "決済処理に失敗しました: マイナスを指定しています");
                        } catch (CanNotReduceMoneyException e) {
                            Message.sendErrorMessage(player, "[CloneAI]", "決済処理に失敗しました: 終わりです");
                        }

                        return AnvilGUI.Response.close();
                    }
                    return AnvilGUI.Response.text(stateSnapshot.getText());
                })
                .text("名前を入力")
                .itemLeft(new ItemStack(Material.PAPER))
                .title("プレイヤー名を入力")
                .plugin(UniverseCoreV2.getInstance())
                .open(player);
    }
}