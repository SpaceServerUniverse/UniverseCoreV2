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
import java.util.Objects;
import java.util.UUID;

public class ReceiveBoxAPI {

    public static void AddReceiveItem(ItemStack itemStack, UUID uuid, Date expire_date, String description){
        if (itemStack == null || itemStack.getType().isAir()) return;

        int total = itemStack.getAmount();
        if (total <= 0) return;

        // 表示名は「分割しても同じ」なので一回だけ取る
        String displayName = null;
        if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName()) {
            displayName = PlainTextComponentSerializer.plainText()
                    .serialize(Objects.requireNonNull(itemStack.getItemMeta().displayName()));
        }

        final int CHUNK = 64;

        for (int remaining = total; remaining > 0; ) {
            int amount = Math.min(CHUNK, remaining);
            remaining -= amount;

            ItemStack chunkStack = itemStack.clone();
            chunkStack.setAmount(amount);

            ReceiveBox receiveBox = new ReceiveBox(
                    null,
                    uuid.toString(),
                    chunkStack.getType().toString(),
                    displayName,
                    description,
                    chunkStack.serializeAsBytes(),
                    JsonConverter.ItemStackSerializer(chunkStack),
                    JsonConverter.ItemMetaSerializer(chunkStack),
                    0,
                    expire_date
            );

            UniverseCoreV2API.getInstance()
                    .getDatabaseManager()
                    .getReceiveBoxRepository()
                    .createReceiveBox(receiveBox);
        }
    }
}
