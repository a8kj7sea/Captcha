import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nonnull;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CheckListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        if (Manager.membersWithCaptcha.containsKey(event.getMember())) {
            if (event.getMessage().getContentDisplay()
                    .equalsIgnoreCase(Manager.membersWithCaptcha.get(event.getMember()))) {

                event.getMessage().delete().queue();
                event.getMessage().reply("You have been passed successfully !").queue();
                Manager.membersWithCaptcha.remove(event.getMember());
            }
        } else if (Manager.daily.containsKey(event.getMember())) {
            if (event.getMessage().getContentDisplay()
                    .equalsIgnoreCase(Manager.daily.get(event.getMember()))) {
                long balance = ThreadLocalRandom.current().nextLong(100, 3001);
                event.getChannel().sendMessage("You received a daily balance of `" + balance + "$` NiCoins.").queue();
                event.getMessage().delete().queue();
                Manager.daily.remove(event.getMember());
            }
        }
    }

}
