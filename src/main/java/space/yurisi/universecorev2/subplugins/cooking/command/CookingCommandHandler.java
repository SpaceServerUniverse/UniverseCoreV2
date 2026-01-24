package space.yurisi.universecorev2.subplugins.cooking.command;

import org.bukkit.entity.Player;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.item.cooking.constant.RecipeId;
import space.yurisi.universecorev2.subplugins.cooking.CookingAPI;
import space.yurisi.universecorev2.subplugins.cooking.util.RecipeFlagOps;
import space.yurisi.universecorev2.utils.Message;

import java.util.UUID;

public class CookingCommandHandler {

    private static final String helpMessage = """
            §6-- Recipe Help --
               §7/cooking add <player> <recipeID>:　レシピを追加します
               §7/cooking remove <player> <recipeID>: レシピを削除します
               §7/cooking check <player> <recipeID>: レシピを取得済みか確認します
               §7/cooking byte <player> (byteIndex): 取得済みのレシピを確認します(byte形式)
               §7/cooking help: ヘルプを表示します
            """;

    public static void handleAddRecipe(Player player, String[] args) {
        if(args.length < 3){
            handleHelpCommand(player);
            return;
        }
        try {
            Long primaryKey = UniverseCoreV2API.getInstance().getDatabaseManager().getUserRepository().getPrimaryKeyFromPlayerName(args[1]);
            UUID uuid = UniverseCoreV2API.getInstance().getDatabaseManager().getUserRepository().getUser(primaryKey).getFormattedUUID();
            CookingAPI.getInstance().cacheRepositoryData(uuid);
            for(RecipeId recipeId: RecipeId.values()){
                if(!args[2].equals(recipeId.getStringId())) continue;
                CookingAPI.getInstance().addRecipe(uuid, recipeId.getFlagId());
                Message.sendSuccessMessage(player, "[料理AI]", "レシピ「§e" + recipeId.getStringId() + "§a」を§e" + args[1] + "§aに追加しました。");
                return;
            }
        } catch (UserNotFoundException | IllegalStateException e) {
            if(e instanceof IllegalStateException){
                Message.sendErrorMessage(player, "[料理AI]", "ユーザーデータが存在しません。管理者にお問い合わせください。");
                return;
            }
            Message.sendErrorMessage(player, "[料理AI]", "指定されたプレイヤーは存在しません。");
            return;
        }
    }

    public static void handleRemoveRecipe(Player player, String[] args) {
        if(args.length < 3){
            handleHelpCommand(player);
            return;
        }
        try {
            Long primaryKey = UniverseCoreV2API.getInstance().getDatabaseManager().getUserRepository().getPrimaryKeyFromPlayerName(args[1]);
            UUID uuid = UniverseCoreV2API.getInstance().getDatabaseManager().getUserRepository().getUser(primaryKey).getFormattedUUID();
            CookingAPI.getInstance().cacheRepositoryData(uuid);
            for(RecipeId recipeId: RecipeId.values()){
                if(!args[2].equals(recipeId.getStringId())) continue;
                CookingAPI.getInstance().removeRecipe(uuid, recipeId.getFlagId());
                Message.sendSuccessMessage(player, "[料理AI]", "レシピ「§e"+recipeId.getStringId()+"§a」を§e"+args[1]+"§aから削除しました。");
                return;
            }
        } catch (UserNotFoundException | IllegalStateException e) {
            if(e instanceof IllegalStateException){
                Message.sendErrorMessage(player, "[料理AI]", "ユーザーデータが存在しません。管理者にお問い合わせください。");
                return;
            }
            if(e instanceof UserNotFoundException){
                Message.sendErrorMessage(player, "[料理AI]", "指定されたプレイヤーは存在しません。");
            }
            return;
        }
    }

    public static void handleCheckRecipe(Player player, String[] args) {
        if(args.length < 3){
            handleHelpCommand(player);
            return;
        }
        try {
            Long primaryKey = UniverseCoreV2API.getInstance().getDatabaseManager().getUserRepository().getPrimaryKeyFromPlayerName(args[1]);
            UUID uuid = UniverseCoreV2API.getInstance().getDatabaseManager().getUserRepository().getUser(primaryKey).getFormattedUUID();
            CookingAPI.getInstance().cacheRepositoryData(uuid);
            for(RecipeId recipeId: RecipeId.values()){
                if(!args[2].equals(recipeId.getStringId())) continue;
                boolean hasRecipe = CookingAPI.getInstance().hasRecipe(uuid, recipeId.getFlagId());
                if(hasRecipe){
                    Message.sendSuccessMessage(player, "[料理AI]", "§e" + args[1] + "§aはレシピ「§e" + recipeId.getStringId() + "§a」を取得済みです。");
                }else{
                    Message.sendErrorMessage(player, "[料理AI]", "§e" + args[1] + "§aはレシピ「§e" + recipeId.getStringId() + "§a」を取得していません。");
                }
                return;
            }
        } catch (UserNotFoundException | IllegalStateException e) {
            if(e instanceof IllegalStateException){
                Message.sendErrorMessage(player, "[料理AI]", "ユーザーデータが存在しません。管理者にお問い合わせください。");
                return;
            }
            Message.sendErrorMessage(player, "[料理AI]", "指定されたプレイヤーは存在しません。");
            return;
        }
    }

    public static void handleCheckRecipeByByte(Player player, String[] args) {
        if(args.length < 2){
            handleHelpCommand(player);
            return;
        }
        try {
            Long primaryKey = UniverseCoreV2API.getInstance().getDatabaseManager().getUserRepository().getPrimaryKeyFromPlayerName(args[1]);
            UUID uuid = UniverseCoreV2API.getInstance().getDatabaseManager().getUserRepository().getUser(primaryKey).getFormattedUUID();
            CookingAPI.getInstance().cacheRepositoryData(uuid);
            byte[] recipeBytes = CookingAPI.getInstance().getRecipeBytes(uuid);
            Message.sendSuccessMessage(player, "[料理AI]", "§e" + args[1] + "§aの取得済みレシピ:");
            if (args.length >= 3) {
                String args2 = args[2];
                int byteIndex = Integer.parseInt(args2);
                if(!(0 <= byteIndex && byteIndex < RecipeFlagOps.RECIPE_BYTES)){
                    throw new NumberFormatException();
                }
                byte[] b = new byte[1];
                b[0] = recipeBytes[byteIndex];
                String bitString = RecipeFlagOps.toBitStringLSBRight(b);
                if(byteIndex <= 9){
                    Message.sendSuccessMessage(player, "[料理AI]", "byte§e[_" + byteIndex + "]: §b" + bitString);
                }else {
                    Message.sendSuccessMessage(player, "[料理AI]", "byte§e[" + byteIndex + "]: §b" + bitString);
                }
                return;
            }
            for(int i = 0; i < RecipeFlagOps.RECIPE_BYTES; i++){
                byte[] b = new byte[1];
                b[0] = recipeBytes[i];
                String bitString = RecipeFlagOps.toBitStringLSBRight(b);
                if(i <= 9){
                    Message.sendSuccessMessage(player, "[料理AI]", "byte§e[_" + i + "]: §b" + bitString);
                }else{
                    Message.sendSuccessMessage(player, "[料理AI]", "byte§e[" + i + "]: §b" + bitString);
                }
            }
            return;
        } catch (UserNotFoundException | IllegalStateException | NumberFormatException e) {
            if(e instanceof IllegalStateException){
                Message.sendErrorMessage(player, "[料理AI]", "ユーザーデータが存在しません。管理者にお問い合わせください。");
                return;
            }
            if(e instanceof NumberFormatException){
                Message.sendErrorMessage(player, "[料理AI]", "第3引数は省略可能ですが、指定する場合は整数(0 ~ " + (RecipeFlagOps.RECIPE_BYTES - 1) + ")を入力してください。");
                return;
            }
            if(e instanceof UserNotFoundException){
                Message.sendErrorMessage(player, "[料理AI]", "指定されたプレイヤーは存在しません。");
                return;
            }
        }
    }

    public static void handleHelpCommand(Player player) {
        player.sendMessage(helpMessage);
    }
}
