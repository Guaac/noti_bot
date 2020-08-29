package bot.listener;

import java.io.BufferedWriter;
import java.io.FileWriter;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter{

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String[] msg = event.getMessage().getContentRaw().split(" ");
		if (msg.length == 0) return;
		
		try {
			switch (msg[0]) {
			case "help":
				help(event);
				break;
			case "예약":
				add(event);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void help(MessageReceivedEvent event) {
		event.getChannel().sendMessage("명령어 목록\n"
				+ "예약 {단위} {시간} #해당 시간뒤에 알람 울려요").queue();
	}
	
	public void add(MessageReceivedEvent event) throws Exception {
		String[] msg = event.getMessage().getContentRaw().split(" ");
		if(msg.length != 3) return;
		
		int aTime = (int)(System.currentTimeMillis() / 1000);
		switch (msg[1]) {
		case "분":
			aTime += (60 * Integer.parseInt(msg[2]));
			break;
		case "시간":
			aTime += (60 * 60 * Integer.parseInt(msg[2]));
			break;
		}
		
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:\\Temp\\noti\\"+event.getChannel().getId()+".txt", true));
		bufferedWriter.append(aTime+"\n");
		bufferedWriter.close();
		
		event.getChannel().sendMessage(aTime+"에 알람이 울려요").queue();
	}
}
