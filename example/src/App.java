
import net.dv8tion.jda.api.requests.GatewayIntent;

public class App {

    private static BotObject bot;

    public static void main(String[] args) throws Exception {
        bot = new BotObject("YOUR_BOT_TOKEN");

        bot.registerListeners(new CaptchaTestCommand(), new CheckListener(), new DailyCommand());
        bot.getJdaBuilder().enableIntents(GatewayIntent.GUILD_MEMBERS);
        bot.build();
    }

    public static BotObject getBot() {
        return bot;
    }
}
