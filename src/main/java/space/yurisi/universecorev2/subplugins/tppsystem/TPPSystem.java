package space.yurisi.universecorev2.subplugins.tppsystem;

import org.bukkit.entity.Player;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.DatabaseManager;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.tppsystem.command.TPPCommand;
import space.yurisi.universecorev2.subplugins.tppsystem.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.tppsystem.file.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class TPPSystem implements SubPlugin {

    private final HashMap<UUID, List<UUID>> tppRequests = new HashMap<>();
    private final HashMap<UUID, UUID> searchReceiver = new HashMap<>();

    private UniverseCoreAPIConnector connector;

    private Config config;


    public void onEnable(UniverseCoreV2 core) {
        // Plugin startup logic
        DatabaseManager manager = UniverseCoreV2API.getInstance().getDatabaseManager();
        this.connector = new UniverseCoreAPIConnector(manager);
        this.config = new Config(core);
        core.getCommand("tpp").setExecutor(new TPPCommand(this, connector));
    }

    public Config getPluginConfig(){
        return this.config;
    }

    public UniverseCoreAPIConnector getConnector(){
        return this.connector;
    }

    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public String getName() {
        return "TPPSystem";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    private UUID playerToUUID(Player player) {
        return player.getUniqueId();
    }

    public void setRequest(Player sender, Player receiver) {
        UUID senderUUID = playerToUUID(sender);
        UUID receiverUUID = playerToUUID(receiver);

        if (this.tppRequests.containsKey(receiverUUID)) {
            this.tppRequests.get(receiverUUID).add(senderUUID);
            return;
        }

        this.tppRequests.put(receiverUUID, List.of(senderUUID));

    }

    public void updateRequest(Player receiver) {
        if (! this.tppRequests.containsKey(playerToUUID(receiver))) {
            return;
        }
        // オフラインになっているプレイヤーを削除
        for (UUID sender : this.tppRequests.get(playerToUUID(receiver))) {
            if (receiver.getServer().getPlayer(sender) == null) {
                receiver.sendMessage("§c" + sender + "はオフラインです。");
                List<UUID> requestsForReceiver = new ArrayList<>(this.tppRequests.get(receiver.getUniqueId()));
                requestsForReceiver.remove(sender);
                this.tppRequests.put(receiver.getUniqueId(), requestsForReceiver);
            }
        }
    }

    public List<UUID> getRequest(Player receiver) {
        return this.tppRequests.get(playerToUUID(receiver));
    }

    public void removeRequest(Player sender, Player receiver) {
        if (! this.tppRequests.containsKey(receiver.getUniqueId())) {
            return;
        }

        List<UUID> requestsForReceiver = new ArrayList<>(this.tppRequests.get(receiver.getUniqueId()));

        requestsForReceiver.remove(sender.getUniqueId());

        if (requestsForReceiver.isEmpty()) {
            // リクエストが空ならマップからエントリを削除
            this.tppRequests.remove(receiver.getUniqueId());
        } else {
            // リクエストがまだ存在する場合、更新されたリストをマップに再設定
            this.tppRequests.put(receiver.getUniqueId(), requestsForReceiver);
        }
    }

    public void removeAllRequest(UUID receiver) {
        this.tppRequests.remove(receiver);
    }

    public void setSearchReceiver(Player sender, Player receiver) {
        this.searchReceiver.put(playerToUUID(sender), playerToUUID(receiver));
    }

    public UUID getSearchReceiver(Player sender) {
        return this.searchReceiver.get(playerToUUID(sender));
    }

    public void removeSearchReceiver(Player sender) {
        this.searchReceiver.remove(playerToUUID(sender));
    }

    public boolean hasRequest(Player sender) {
        return this.searchReceiver.containsKey(playerToUUID(sender));
    }
}
