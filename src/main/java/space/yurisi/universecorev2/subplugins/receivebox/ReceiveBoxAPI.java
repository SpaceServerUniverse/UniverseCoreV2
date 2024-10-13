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
import java.util.UUID;

public class ReceiveBoxAPI {

    public static void AddReceiveItem(ItemStack itemStack, UUID uuid, Date expire_date, String description){
        String displayName = null;
        if(itemStack.getItemMeta() != null){
            displayName = itemStack.getItemMeta().hasDisplayName() ? PlainTextComponentSerializer.plainText().serialize(itemStack.getItemMeta().displayName()) : null;
        }
        ReceiveBox receiveBox = new ReceiveBox(
                null,
                uuid.toString(),
                itemStack.getType().toString(),
                displayName,
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
