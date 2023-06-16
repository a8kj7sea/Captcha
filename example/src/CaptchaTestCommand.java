import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;

import dev.a8kj7sea.captcha.image.ImageCreator;
import dev.a8kj7sea.captcha.object.Captcha;
import dev.a8kj7sea.captcha.object.Captcha.CaptchaType;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CaptchaTestCommand extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {

        if (event.getAuthor().isBot())
            return;

        String[] args = event.getMessage().getContentRaw().split(" ");
        if (args.length != 1) {
            return;
        } else {
            if (args[0].equalsIgnoreCase("!captcha")) {
                if (!(Manager.membersWithCaptcha.containsKey(event.getMember()))) {
                    Captcha captcha = new Captcha(CaptchaType.TEXT, 4);
                    String captchaText = captcha.build();
                    File captchaImage = ImageCreator.generateImage(captchaText);
                    event.getChannel()
                            .sendMessage("Please enter the captcha to complete your process you have 10s " + captchaText)
                            .addFile(captchaImage, "captcha.png")
                            .queue();
                    captchaImage.delete();
                    Manager.membersWithCaptcha.put(event.getMember(), captchaText);
                    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                    executorService.schedule(() -> {
                        Manager.membersWithCaptcha.remove(event.getMember());
                    }, 10, TimeUnit.SECONDS);
                } else {
                    event.getMessage().delete().queue();
                    event.getMessage().reply("You can't !").queue();
                }
            }
        }
    }
}
