package space.yurisi.universecorev2.subplugins.birthdaycard.command.subcommand;

import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.BirthdayData;
import space.yurisi.universecorev2.database.models.BirthdayMessages;
import space.yurisi.universecorev2.database.repositories.BirthdayCardRepository;
import space.yurisi.universecorev2.exception.BirthdayDataNotFoundException;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.ticket.GachaTicket;
import space.yurisi.universecorev2.subplugins.birthdaycard.BirthdayCard;
import space.yurisi.universecorev2.subplugins.birthdaycard.utils.PageJsonUtils;
import space.yurisi.universecorev2.subplugins.receivebox.ReceiveBoxAPI;
import space.yurisi.universecorev2.utils.Message;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

public class giftSubCommand implements BirthdayCardSubCommand{
    @Override
    public boolean execute(Player player, String[] args) {
        BirthdayCardRepository repo = UniverseCoreV2API.getInstance().getDatabaseManagerV2().get(BirthdayCardRepository.class);

        BirthdayData birthdayData;
        try {
            birthdayData = repo.getBirthdayData(player.getUniqueId());
        } catch (BirthdayDataNotFoundException e) {
            Message.sendErrorMessage(player, BirthdayCard.PREFIX, "誕生日が登録されていません");
            return true;
        }

        if (birthdayData.isGiftReceived()) {
            Message.sendNormalMessage(player, BirthdayCard.PREFIX, "もうすでに誕生日カードを受け取っています");
            return true;
        }

        LocalDate today = LocalDate.now();
        if (!isBirthdayTodayOrLeapAlternative(birthdayData.getMonth(), birthdayData.getDay(), today)) {
            Message.sendErrorMessage(player, BirthdayCard.PREFIX, "まだ誕生日じゃないよ。誕生日を確認して、後で戻ってきてね！");
            return true;
        }

        List<BirthdayMessages> birthdayMessagesList = repo.getBirthdayMessages(birthdayData.getId());

        Date expire_date = new Date(
                System.currentTimeMillis() + Duration.ofDays(10).toMillis()
        );

        ItemStack bookItem = ItemStack.of(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) bookItem.getItemMeta();
        bookMeta.title(Component.text("お誕生日カード " + player.getName() + "さんへ"));
        bookMeta.author(Component.text("お誕生日カード (" + LocalDate.now().getYear() + ")")
                .color(NamedTextColor.GOLD)
                .decorate(TextDecoration.BOLD));

        List<Component> pageComponents = new ArrayList<>();
        if (birthdayMessagesList.isEmpty()) {
            List<String> selectedMessages = getRandomMessage();
            for (String message : selectedMessages) {
                pageComponents.add(Component.text(message));
            }
            Message.sendSuccessMessage(player, BirthdayCard.PREFIX, "お誕生日カードをお贈りしました");
        } else {
            birthdayMessagesList.forEach(birthdayMessages -> {
                pageComponents.addAll(PageJsonUtils.deserializePageJson(birthdayMessages.getMessage()));
                repo.deleteBirthdayMessage(birthdayMessages);
            });
        }

        bookMeta.pages(pageComponents);
        bookItem.setItemMeta(bookMeta);

        ItemStack ticket = UniverseItem.getItem(GachaTicket.id).getItem();
        ticket.setAmount(20);

        ReceiveBoxAPI.AddReceiveItem(bookItem, player.getUniqueId(), expire_date, "お誕生日プレゼント");
        ReceiveBoxAPI.AddReceiveItem(ticket, player.getUniqueId(), expire_date, "お誕生日プレゼント");
        Message.sendSuccessMessage(player, BirthdayCard.PREFIX, "お誕生日プレゼントが受け取りボックスに送られました");

        Bukkit.getServer().broadcast(Component.text("🎉 今日は ", NamedTextColor.YELLOW)
                .append(Component.text(player.getName(), NamedTextColor.GOLD))
                .append(Component.text(" さんの誕生日です！🎂\n", NamedTextColor.YELLOW))
                .append(Component.text("素晴らしい一年になりますように！おめでとう！", NamedTextColor.GREEN)));

        birthdayData.setGiftReceived(true);
        repo.updateBirthdayData(birthdayData);
        return true;
    }

    private List<String> getRandomMessage(){
        List<String> birthdayMessages = Arrays.asList(
                "お誕生日おめでとう！今日は特別な日だから、素敵なことがたくさんありますように！",
                "ハッピーバースデー！どんな日になるか楽しみだね。素晴らしい一年にしよう！",
                "お誕生日おめでとう！あなたの笑顔がもっと見られる一年になりますように！",
                "お誕生日おめでとうございます。新しい一年が素晴らしい成長と幸福に満ちたものとなりますように。",
                "この特別な日を迎えられたことを心よりお祝い申し上げます。素晴らしい一年になりますように。",
                "お誕生日おめでとうございます。今後の一年が健康で幸せに満ちたものでありますよう、心より願っております。",
                "お誕生日おめでとう！年齢はただの数字…でも、ケーキの数は本物だよ！",
                "ハッピーバースデー！年を取ることは避けられないけれど、心はいつまでも若々しく！",
                "お誕生日おめでとう！歳を重ねるのも悪くない、特にケーキがあるときはね！"
        );

        Random random = new Random();
        int numberOfMessages = 1 + random.nextInt(birthdayMessages.size());
        Collections.shuffle(birthdayMessages);
        return birthdayMessages.subList(0, numberOfMessages);
    }

    // 2/29が存在しない年は落ちる問題
    private boolean isBirthdayTodayOrLeapAlternative(int month, int day, LocalDate today) {
        //2/29がない年は2/28または3/1に受け取れるようにする。
        if (month == 2 && day == 29 && !today.isLeapYear()) {
            return (today.getMonthValue() == 2 && today.getDayOfMonth() == 28)
                    || (today.getMonthValue() == 3 && today.getDayOfMonth() == 1);
        }

        return today.getMonthValue() == month && today.getDayOfMonth() == day;
    }
}
