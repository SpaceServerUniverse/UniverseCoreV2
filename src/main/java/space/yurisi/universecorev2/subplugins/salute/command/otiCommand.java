package space.yurisi.universecorev2.subplugins.salute.command;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class otiCommand extends SaluteBaseCommand{

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        String name = commandSender.getName();
        setMessages(
                new Component[]{
                        Component.text(  name + ">> " + "§b(@＾皿＾@)ゞ『ォチﾏｽ!!』*｡+ﾟ★"),
                        Component.text(name + ">> " + "§b(○´∇｀)ﾉ☆ﾟ+.退室ｲﾀｼﾏｽ.+ﾟ☆"),
                        Component.text(name + ">> " + "§b★ﾟ+o｡(☆´･ω･)σ《ぉちます》a(･ω･｀★)｡o+ﾟ☆"),
                        Component.text(name + ">> " + "§bp【+ﾟ*退室ｼﾁｬｳｮ:ﾟ+】qД｀｡)｡o.ﾟ｡"),
                        Component.text(name + ">> " + "§bヾ(´Д｀q･ﾟ･ﾊﾞィﾊﾞィ! おちるﾈェｯ!!*:ﾟ･☆"),
                        Component.text(name + ">> " + "§bヾ(★´Å｀★)σ【ｵﾁﾙﾈェ～】｡o+☆"),
                        Component.text(name + ">> " + "§b人･∀･*).o0((ｿﾛｿﾛ☆落ﾁﾏｽ★))"),
                        Component.text(name + ">> " + "§bｵﾁﾙﾈ～☆εε==≡ヾ(★,,´∀'｀)ﾉ")

                }
        );


        Bukkit.broadcast(getMessage());
        return true;
    }
}
