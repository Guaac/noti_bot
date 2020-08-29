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
			case "����":
				add(event);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void help(MessageReceivedEvent event) {
		event.getChannel().sendMessage("��ɾ� ���\n"
				+ "���� {����} {�ð�} #�ش� �ð��ڿ� �˶� �����").queue();
	}
	
	public void add(MessageReceivedEvent event) throws Exception {
		String[] msg = event.getMessage().getContentRaw().split(" ");
		if(msg.length != 3) return;
		
		int aTime = (int)(System.currentTimeMillis() / 1000);
		switch (msg[1]) {
		case "��":
			aTime += (60 * Integer.parseInt(msg[2]));
			break;
		case "�ð�":
			aTime += (60 * 60 * Integer.parseInt(msg[2]));
			break;
		}
		
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:\\Temp\\noti\\"+event.getChannel().getId()+".txt", true));
		bufferedWriter.append(aTime+"\n");
		bufferedWriter.close();
		
		event.getChannel().sendMessage(aTime+"�� �˶��� �����").queue();
	}
}
