package space.yurisi.universecorev2.subplugins.cooking.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.yurisi.universecorev2.subplugins.cooking.utils.RecipeIds;

import java.util.ArrayList;
import java.util.List;

public class RecipeCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(args.length == 2){
            List<String> completions = new ArrayList<>();
            for (RecipeIds recipeId : RecipeIds.values()) {
                completions.add(recipeId.getName());
            }
            return completions;
        }
        return List.of();
    }
}
