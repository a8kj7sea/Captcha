import java.util.HashMap;
import java.util.Map;

import net.dv8tion.jda.api.entities.Member;

public class Manager {
    public static Map<Member, String> membersWithCaptcha = new HashMap<>();
    public static Map<Member, String> daily = new HashMap<>();

}
