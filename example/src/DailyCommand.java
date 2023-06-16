import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;

import dev.a8kj7sea.captcha.image.ImageCreator;
import dev.a8kj7sea.captcha.object.Captcha;
import dev.a8kj7sea.captcha.object.Captcha.CaptchaType;

public class DailyCommand extends ListenerAdapter {
    private final Map<String, Instant> cooldowns = new HashMap<>();
    private final Duration cooldownDuration = Duration.ofDays(1);

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        super.onGuildMessageReceived(event);

        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase("!daily")) {
            String userId = event.getAuthor().getId();

            if (cooldowns.containsKey(userId)) {
                Instant lastClaim = cooldowns.get(userId);
                Instant now = Instant.now();
                Duration timeSinceLastClaim = Duration.between(lastClaim, now);

                if (timeSinceLastClaim.compareTo(cooldownDuration) < 0) {
                    Duration remainingCooldown = cooldownDuration.minus(timeSinceLastClaim);
                    event.getChannel().sendMessage(
                            "You can claim your daily balance again in " + remainingCooldown.toHours() + " hours.")
                            .queue();
                    return;
                }
            }

            if (!(Manager.daily.containsKey(event.getMember()))) {

                cooldowns.put(userId, Instant.now());

                Captcha captcha = new Captcha(CaptchaType.DIGITS, 5);
                String captchaText = captcha.build();
                File captchaImage = ImageCreator.generateImage(captchaText);
                event.getChannel()
                        .sendMessage("Please enter the captcha to complete your process you have 10s " + captchaText)
                        .addFile(captchaImage, "captcha.png")
                        .queue();
                captchaImage.delete();
                Manager.daily.put(event.getMember(), captchaText);
                ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                executorService.schedule(() -> {
                    Manager.daily.remove(event.getMember());
                }, 10, TimeUnit.SECONDS);
            } else {
                event.getMessage().delete().queue();
                event.getMessage().reply("You can't !").queue();
            }

        }
    }
}