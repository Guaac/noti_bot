package bot.listener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Ready extends ListenerAdapter{

	@Override
	public void onReady(ReadyEvent event) {
		Thread thread = new Thread(new Runnable() {
			public void run(){
				System.out.println("���� ����");
				while(true) {
					File dir = new File("C:\\Temp\\noti");
					File files[] = dir.listFiles();
					
					for (File file : files) {
			            FileReader fileR = null;
			            Vector<String> rows = new Vector<String>();
			            
//			                        ���� ���� rows�� ��� 
						try {
							fileR = new FileReader(file);
				            BufferedReader bufReader = new BufferedReader(fileR);
				            String line;
							while((line = bufReader.readLine()) != null){
								rows.add(line);
				            }
							bufReader.close();
						} catch (Exception e) {}
						
//						�˸� ������
						int currentTime = (int) (System.currentTimeMillis() / 1000);
						for(int i = 0; rows.size() > i ; i ++) {
//							����ð� �������� Ȯ��
							if(currentTime > Integer.parseInt(rows.get(i))) {
								rows.set(i, null);
//								�������� �޽��� ������
								event
									.getJDA()
									.getTextChannelById(file.getName().substring(0, file.getName().length() -4))
									.sendMessage("������ �˶��� �����")
									.queue();
							}
						}
						
//						���� �˸� ��⿭���� ����
						try {
							String fileContent = "";
							for(int i = 0; rows.size() > i ; i ++) {
								if(rows.get(i) != null){
									fileContent += rows.get(i)+"\n";
								}
							}
							
//							������ ��⿭ ����
							FileWriter fileW = new FileWriter(file);
							fileW.write(fileContent);
							fileW.flush();
							fileW.close();
							
						} catch (IOException e) {}
					}
					try {
						Thread.sleep(1000 * 5);
					} catch (InterruptedException e) {e.printStackTrace();}
				}
			}
		});
		thread.start();
	}
}
