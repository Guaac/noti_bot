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
				System.out.println("연결 성공");
				while(true) {
					File dir = new File("C:\\Temp\\noti");
					File files[] = dir.listFiles();
					
					for (File file : files) {
			            FileReader fileR = null;
			            Vector<String> rows = new Vector<String>();
			            
//			                        파일 내용 rows에 담기 
						try {
							fileR = new FileReader(file);
				            BufferedReader bufReader = new BufferedReader(fileR);
				            String line;
							while((line = bufReader.readLine()) != null){
								rows.add(line);
				            }
							bufReader.close();
						} catch (Exception e) {}
						
//						알림 보내기
						int currentTime = (int) (System.currentTimeMillis() / 1000);
						for(int i = 0; rows.size() > i ; i ++) {
//							예약시간 지났는지 확인
							if(currentTime > Integer.parseInt(rows.get(i))) {
								rows.set(i, null);
//								지났으면 메시지 보내기
								event
									.getJDA()
									.getTextChannelById(file.getName().substring(0, file.getName().length() -4))
									.sendMessage("예약한 알람이 울려요")
									.queue();
							}
						}
						
//						보낸 알림 대기열에서 삭제
						try {
							String fileContent = "";
							for(int i = 0; rows.size() > i ; i ++) {
								if(rows.get(i) != null){
									fileContent += rows.get(i)+"\n";
								}
							}
							
//							수정된 대기열 저장
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
