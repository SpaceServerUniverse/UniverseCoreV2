package space.yurisi.universecorev2.subplugins.birthdaycard.command.subcommand;

import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.BirthdayData;
import space.yurisi.universecorev2.database.repositories.BirthdayCardRepository;
import space.yurisi.universecorev2.exception.BirthdayDataNotFoundException;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.ticket.GachaTicket;
import space.yurisi.universecorev2.subplugins.birthdaycard.BirthdayCard;
import space.yurisi.universecorev2.subplugins.birthdaycard.utils.PageJsonUtils;
import space.yurisi.universecorev2.subplugins.birthdaycard.utils.PlayerUtils;
import space.yurisi.universecorev2.subplugins.receivebox.ReceiveBoxAPI;
import space.yurisi.universecorev2.utils.Message;

import java.time.Duration;
import java.util.*;

public class sendSubCommand implements BirthdayCardSubCommand {
    @Override
    public boolean execute(Player player, String[] args) {
        ItemStack mainHandItem = player.getInventory().getItemInMainHand();
        if (mainHandItem.getType() != Material.WRITTEN_BOOK) {
            Message.sendErrorMessage(player, BirthdayCard.PREFIX, "著名した本にしてください");
            return true;
        }

        NamespacedKey nk = new NamespacedKey(UniverseCoreV2.getInstance(), "BIRTHDAY_DATA");
        PersistentDataContainer container = mainHandItem.getItemMeta().getPersistentDataContainer();

        String uuidString = container.get(nk, PersistentDataType.STRING);
        if (uuidString == null) {
            Message.sendErrorMessage(player, BirthdayCard.PREFIX, "/birthday getで入手した本か確認してください");
            return true;
        }

        UUID targetUuid;
        try {
             targetUuid = UUID.fromString(uuidString);
        } catch (IllegalArgumentException e) {
            Message.sendErrorMessage(player, BirthdayCard.PREFIX, "本が壊れています。もう一度最初からやり直して下さい。");
            return true;
        }

        BirthdayCardRepository repo = UniverseCoreV2API.getInstance().getDatabaseManagerV2().get(BirthdayCardRepository.class);
        BirthdayData birthdayData;
        try {
            birthdayData = repo.getBirthdayData(targetUuid);
        } catch (BirthdayDataNotFoundException e) {
            Message.sendErrorMessage(player, BirthdayCard.PREFIX, "誕生日が登録されていません");
            return true;
        }

        if (birthdayData.getUuid().equals(player.getUniqueId().toString())) {
            Message.sendErrorMessage(player, BirthdayCard.PREFIX, "自分自身にメッセージを送信することはできません");
            return true;
        }

        Date expire_date = new Date(
                System.currentTimeMillis() + Duration.ofDays(10).toMillis()
        );

        BookMeta bookMeta = (BookMeta) mainHandItem.getItemMeta();
        Component lastPage = bookMeta.pages().getLast();
        Component updatedLastPage = lastPage.append(Component.text("\n" + player.getName() + " より"));
        List<Component> newPages = new ArrayList<>(bookMeta.pages());
        newPages.set(newPages.size() - 1, updatedLastPage);
        String pageJson = PageJsonUtils.serializePageJson(newPages);

        var birthdayMessage = repo.createBirthdayMessage(birthdayData.getId(), player, pageJson);

        Message.sendSuccessMessage(player, BirthdayCard.PREFIX, "お誕生日カードを送信しました");
        player.getInventory().remove(mainHandItem);

        ItemStack ticket = UniverseItem.getItem(GachaTicket.id).getItem();
        ticket.setAmount(5);
        Message.sendSuccessMessage(player, BirthdayCard.PREFIX, "お誕生日カードを書いてくれてありがとう\nガチャチケを5枚プレゼント!!");
        Message.sendSuccessMessage(player, BirthdayCard.PREFIX, "報酬受け取りボックスに追加しました");
        ReceiveBoxAPI.AddReceiveItem(ticket, player.getUniqueId(), expire_date, "お誕生日カードを書いてくれたから");

        birthdayMessage.setReceivedGachaTicket(true);
        repo.updateBirthdayMessage(birthdayMessage);

        return true;
    }
}
