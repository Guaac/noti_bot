package bot;

import bot.listener.MessageListener;
import bot.listener.Ready;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Main {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		JDA jda = null;
		try {
			jda = new JDABuilder("NTcwNTMxMTk4MzQ3MjQ3NjE2.XMAilQ.6OgSi9T793dArWNLTtM5eidAe5I").build();
		} catch (Exception e) {
			System.out.println("연결실패");
		}
		jda.addEventListener(new MessageListener());
		jda.addEventListener(new Ready());
	}
}
