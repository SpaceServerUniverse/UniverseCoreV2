package space.yurisi.universecorev2.subplugins.cooking.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.yurisi.universecorev2.api.LuckPermsWrapper;
import space.yurisi.universecorev2.item.cooking.constant.RecipeId;
import space.yurisi.universecorev2.utils.Message;

import java.util.ArrayList;
import java.util.List;

public class CookingCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage("このコマンドはプレイヤーのみ実行可能です。");
            return true;
        }
        if(!LuckPermsWrapper.isUserInAdminOrDevGroup(player)){
            Message.sendErrorMessage(player, "[料理AI]", "このコマンドを実行する権限がありません。");
            return true;
        }
        if(args.length == 0){
            CookingCommandHandler.handleHelpCommand(player);
            return true;
        }
        switch (args[0]){
            case "add" -> CookingCommandHandler.handleAddRecipe(player, args);
            case "remove" -> CookingCommandHandler.handleRemoveRecipe(player, args);
            case "check" -> CookingCommandHandler.handleCheckRecipe(player, args);
            case "byte" -> CookingCommandHandler.handleCheckRecipeByByte(player, args);
            case "help" -> CookingCommandHandler.handleHelpCommand(player);
            default -> CookingCommandHandler.handleHelpCommand(player);
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(args.length == 1){
            return List.of("add", "remove", "check", "byte", "help");
        }
        if(args.length == 2){
            return null;
        }
        if(args.length == 3 && (args[0].equals("add") || args[0].equals("remove") || args[0].equals("check"))){
            List<String> completions = new ArrayList<>();
            for (RecipeId recipeId : RecipeId.values()) {
                completions.add(recipeId.getStringId());
            }
            return completions;
        }
        return List.of();
    }
}
