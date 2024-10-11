package space.yurisi.universecorev2.subplugins.receivebox;

import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.ReceiveBox;
import space.yurisi.universecorev2.utils.JsonConverter;
import space.yurisi.universecorev2.utils.Message;

import java.util.Date;

public class ReceiveBoxAPI {

    public static void AddReceiveItem(ItemStack itemStack, Player player, Date expire_date, String description){
        ReceiveBox receiveBox = new ReceiveBox(
                null,
                player.getUniqueId().toString(),
                itemStack.getType().toString(),
                itemStack.getItemMeta().hasDisplayName() ? PlainTextComponentSerializer.plainText().serialize(itemStack.getItemMeta().displayName()) : null,
                description,
                itemStack.serializeAsBytes(),
                JsonConverter.ItemStackSerializer(itemStack),
                JsonConverter.ItemMetaSerializer(itemStack),
                0,
                expire_date
        );
        UniverseCoreV2API.getInstance().getDatabaseManager().getReceiveBoxRepository().createReceiveBox(receiveBox);
    }


}
