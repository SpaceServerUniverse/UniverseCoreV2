package space.yurisi.universecorev2.subplugins.cooking.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.api.LuckPermsWrapper;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.cooking.CookingAPI;
import space.yurisi.universecorev2.subplugins.cooking.util.RecipeFlagOps;
import space.yurisi.universecorev2.item.cooking.constant.RecipeId;
import space.yurisi.universecorev2.utils.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CookingCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        String[] helpMessage = """
            §6-- Recipe Help --
               §7/cooking add <player> <RecipeID>:　レシピを追加します
               §7/cooking remove <player> <RecipeID>: レシピを削除します
               §7/cooking check <player>: 取得済みのレシピを確認します(byte形式)
               §7/cooking help: ヘルプを表示します
            """.split("\n");
        if(args.length == 0){
            sender.sendMessage(helpMessage);
            return true;
        }
        if(!(sender instanceof Player player)) {
            sender.sendMessage("このコマンドはプレイヤーのみ実行可能です。");
            return true;
        }
        if(!LuckPermsWrapper.isUserInAdminOrDevGroup(player)){
            Message.sendErrorMessage(player, "[料理AI]", "このコマンドを実行する権限がありません。");
            return true;
        }
        switch (args[0]){
            case "add":
                if(args.length < 3){
                    sender.sendMessage(helpMessage);
                    return true;
                }
                try {
                    Long primaryKey = UniverseCoreV2API.getInstance().getDatabaseManager().getUserRepository().getPrimaryKeyFromPlayerName(args[1]);
                    UUID uuid = UniverseCoreV2API.getInstance().getDatabaseManager().getUserRepository().getUser(primaryKey).getFormattedUUID();
                    CookingAPI.getInstance().cacheRepositoryData(uuid);
                    for(RecipeId recipeId: RecipeId.values()){
                        if(!args[2].equals(recipeId.getStringId())) continue;
                        CookingAPI.getInstance().addRecipe(uuid, recipeId.getFlagId());
                        Message.sendSuccessMessage(player, "[料理AI]", "レシピ「§e"+recipeId.getStringId()+"§a」を§e"+args[1]+"§aに追加しました。");
                        return true;
                    }
                } catch (UserNotFoundException | IllegalStateException e) {
                    if(e instanceof IllegalStateException){
                        Message.sendErrorMessage(player, "[料理AI]", "ユーザーデータが存在しません。管理者にお問い合わせください。");
                        return true;
                    }
                    Message.sendErrorMessage(player, "[料理AI]", "指定されたプレイヤーは存在しません。");
                    return true;
                }
                break;
            case "remove":
                if(args.length < 3){
                    sender.sendMessage(helpMessage);
                    return true;
                }
                try {
                    Long primaryKey = UniverseCoreV2API.getInstance().getDatabaseManager().getUserRepository().getPrimaryKeyFromPlayerName(args[1]);
                    UUID uuid = UniverseCoreV2API.getInstance().getDatabaseManager().getUserRepository().getUser(primaryKey).getFormattedUUID();
                    CookingAPI.getInstance().cacheRepositoryData(uuid);
                    for(RecipeId recipeId: RecipeId.values()){
                        if(!args[2].equals(recipeId.getStringId())) continue;
                        CookingAPI.getInstance().removeRecipe(uuid, recipeId.getFlagId());
                        Message.sendSuccessMessage(player, "[料理AI]", "レシピ「§e"+recipeId.getStringId()+"§a」を§e"+args[1]+"§aから削除しました。");
                        return true;
                    }
                } catch (UserNotFoundException | IllegalStateException e) {
                    if(e instanceof IllegalStateException){
                        Message.sendErrorMessage(player, "[料理AI]", "ユーザーデータが存在しません。管理者にお問い合わせください。");
                        return true;
                    }
                    Message.sendErrorMessage(player, "[料理AI]", "指定されたプレイヤーは存在しません。");
                    return true;
                }
                break;
            case "check":
                if(args.length < 2){
                    sender.sendMessage(helpMessage);
                    return true;
                }
                try {
                    Long primaryKey = UniverseCoreV2API.getInstance().getDatabaseManager().getUserRepository().getPrimaryKeyFromPlayerName(args[1]);
                    UUID uuid = UniverseCoreV2API.getInstance().getDatabaseManager().getUserRepository().getUser(primaryKey).getFormattedUUID();
                    CookingAPI.getInstance().cacheRepositoryData(uuid);
                    byte[] recipeBytes = CookingAPI.getInstance().getRecipeBytes(uuid);
                    Message.sendSuccessMessage(player, "[料理AI]", "§e"+args[1]+"§aの取得済みレシピ: §b"+ RecipeFlagOps.toBitStringLSBRight(recipeBytes));
                    return true;
                } catch (UserNotFoundException | IllegalStateException e) {
                    if(e instanceof IllegalStateException){
                        Message.sendErrorMessage(player, "[料理AI]", "ユーザーデータが存在しません。管理者にお問い合わせください。");
                        return true;
                    }
                    Message.sendErrorMessage(player, "[料理AI]", "指定されたプレイヤーは存在しません。");
                    return true;
                }
            case "help":
            default:
                sender.sendMessage(helpMessage);
                break;
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(args.length == 1){
            return List.of("add", "remove", "check", "help");
        }
        if(args.length == 2){
            return null;
        }
        if(args.length == 3 && (args[0].equals("add") || args[0].equals("remove"))){
            List<String> completions = new ArrayList<>();
            for (RecipeId recipeId : RecipeId.values()) {
                completions.add(recipeId.getStringId());
            }
            return completions;
        }
        return List.of();
    }
}
