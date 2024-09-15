package space.yurisi.universecorev2.subplugins.universeutilcommand.salute;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class kCommand extends SaluteBaseCommand{

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        String name = commandSender.getName();
        setMessages(new Component[]{
                Component.text( name + ">> " + "§a|^・ω・)/ ﾊﾛｰ♪"),
                Component.text(name + ">> " + "§aｺﾝﾆﾁ波！( ゜o）＜≡≡((((☆ｶﾞｺﾞｰﾝ☆）>o<)ノ ｵｩｰ"),
                Component.text(name + ">> " + "§a＝＝((( (/* ^^)/ ﾊﾛｰｰｰ!!"),
                Component.text(name + ">> " + "§aｺﾝﾁｬｯ(/∀＼*)ｷｬｯｷｬｯ"),
                Component.text(name + ">> " + "§a(｡･ω･)ﾉ こんてぃわー"),
                Component.text(name + ">> " + "§a(*´_｀)ﾉ ﾔﾎﾟｰ♪"),
                Component.text(name + ">> " + "§aこん(/・ω・)/にゃちゎ"),
                Component.text(name + ">> " + "§a(。･o･｡)ﾉ こんにちゎぁ♪"),
                Component.text(name + ">> " + "§a(*◎Ｕ∀Ｕp)q♪ﾁﾜｧ♪"),
                Component.text(name + ">> " + "§a(√･ω･) ちーっす")
        });

        Bukkit.broadcast(getMessage());
        return true;
    }
}
