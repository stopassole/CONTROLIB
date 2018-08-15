package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.controlsfx.control.Notifications;

import dao.EmprestimoDAO;
import entity.EmprestimoDTO;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class ScheduleEmprestimosAtrasados {

	public static final String END_POINT = "ScheduleEmprestimosAtrasados";

	private static final Logger log = Logger.getLogger(ScheduleEmprestimosAtrasados.END_POINT);

	Timer timer;

	EmprestimoDAO dao = new EmprestimoDAO();

	public void scheduleNotifications() {

		timer = new Timer();

		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.HOUR_OF_DAY, 10);
		calendar.set(Calendar.MINUTE, 15);
		calendar.set(Calendar.SECOND, 0);

		Date time = calendar.getTime();

		timer.schedule(new tarefasDiarias(time), time);

	}

	public void parar() {
		timer.cancel();
	}

	class tarefasDiarias extends TimerTask {

		private String time;
		private String agora;
		DateUtil date = new DateUtil();

		List<EmprestimoDTO> list = new ArrayList<>();

		public tarefasDiarias(Date time) {
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			this.time = format.format(time);
		}

		@SuppressWarnings("static-access")
		public void run() {

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			agora = sdf.format(new Date(System.currentTimeMillis()));

			Platform.runLater(() -> {

				if (this.time.equals(agora)) {

					try {
						list = dao.buscarEmprestimos();
					} catch (Exception e) {
						e.printStackTrace();
					}

					if (list != null && !list.isEmpty()) {

						SimpleDateFormat formatHoje = new SimpleDateFormat("dd/MM/yyyy");
						String hoje = formatHoje.format(new Date(System.currentTimeMillis()));

						for (EmprestimoDTO dto : list) {
							try {
								if (date.getMaiorData(hoje, dto.getDataVencimento())) {

									log.info(END_POINT + "/notificacaoemprestimo -> Inicio");

									ImageView img = new ImageView("./images/falha.png");
									Notifications notificationBuilder = Notifications.create()
											.title("Emprestimo Atrasado").text("Teste").graphic(img)
											.hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);

									notificationBuilder.show();

									log.info(END_POINT + "/notificacaoemprestimo -> Fim");
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			});
			parar();
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public String getAgora() {
			return agora;
		}

		public void setAgora(String agora) {
			this.agora = agora;
		}
	}

}
