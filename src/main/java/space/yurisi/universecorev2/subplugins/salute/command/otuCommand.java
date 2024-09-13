package space.yurisi.universecorev2.subplugins.salute.command;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class otuCommand extends SaluteBaseCommand{

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        String name = commandSender.getName();
        setMessages(
                new Component[]{
                        Component.text(name + ">> " + "§6(≧∇≦) ｵﾂｶﾚｻﾏー♪"),
                        Component.text(name + ">> " + "§6お疲れ様(*･ω･*)ゞﾃﾞｼ!!"),
                        Component.text(name + ">> " + "§6ｵﾂｶﾚｰ！Σd(ゝ∀･)"),
                        Component.text(name + ">> " + "§6ヾ(*´I｀)ﾉ ｡ﾟ･+:.おつかれさま･.:+･ﾟ｡"),
                        Component.text(name + ">> " + "§6(。っ・Д・)っ 【お疲れさまぁ♪】"),
                        Component.text(name + ">> " + "§6ヽ(。ゝω・)ノﾎﾟｨｯ⌒【☆:*:･ｵﾂｶﾚｻﾏ･:*ﾟ☆】"),
                        Component.text(name + ">> " + "§6ｼｭｯ!!(´･ω･｀)ﾉ≡【☆:*:･おつかれさま･:*ﾟ☆】"),
                        Component.text(name + ">> " + "§6ｵﾂｶﾚｰ ヾ(=ﾟωﾟ)ゞ"),
                        Component.text(name + ">> " + "§6おつかれー！(*´Ｉ ｀*)ﾉｼ"),
                        Component.text(name + ">> " + "§6((*´ゝз･)ﾉﾞお疲れ様♪")
                }
        );

        Bukkit.broadcast(getMessage());
        return true;
    }
}
