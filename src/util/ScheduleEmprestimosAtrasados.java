package util;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ScheduleEmprestimosAtrasados {

	Timer timer;

	public void scheduleNotifications() {

		timer = new Timer();

		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.HOUR_OF_DAY, 17);
		calendar.set(Calendar.MINUTE, 31);
		calendar.set(Calendar.SECOND, 0);

		Date time = calendar.getTime();

		timer.schedule(new tarefasDiarias(), time);

	}

	public void parar() {
		timer.cancel();
	}

	class tarefasDiarias extends TimerTask {

		public void run() {
			
		}
	}

}
