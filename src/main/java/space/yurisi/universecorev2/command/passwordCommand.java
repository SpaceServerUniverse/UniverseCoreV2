package space.yurisi.universecorev2.command;

import at.favre.lib.crypto.bcrypt.BCrypt;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.User;
import space.yurisi.universecorev2.database.repositories.UserRepository;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.utils.Message;

import java.util.regex.Pattern;

public class passwordCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player player)){
            return false;
        }

        if (strings.length == 0){
            return false;
        }

        UserRepository userRepo = UniverseCoreV2API.getInstance().getDatabaseManager().getUserRepository();

        if(!userRepo.existsUserFromUUID(player.getUniqueId())){
            userRepo.createUser(player);
        }
        User user;
        try {
            user = userRepo.getUserFromUUID(player.getUniqueId());
        } catch (UserNotFoundException e){
            Message.sendErrorMessage(player, "[管理AI]", "エラーが発生しました。ユーザーデータが存在しません");
            return false;
        }

        //大文字小文字数字含む8~32文字
        //記号も可
        String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,32}$";
        Pattern pattern = Pattern.compile(regex);
        String password = strings[0];

        if(!pattern.matcher(password).matches()){
            Message.sendErrorMessage(player, "[管理AI]", "パスワードが条件を満たしていません。大文字小文字数字含む8~32文字で設定してください");
            return false;
        }

        String hashed_password = BCrypt.withDefaults().hashToString(8, password.toCharArray());

        user.setPassword(hashed_password);

        userRepo.updateUser(user);
        Message.sendSuccessMessage(player, "[管理AI]", "パスワードを設定しました。Webからログインできるようになりました");
        return true;
    }
}
