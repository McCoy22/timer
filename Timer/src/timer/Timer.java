package timer;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class Timer
		extends JFrame {

	// TODO Zeit dynamisch machen
	private long MAX_TIME = 1;

	private JPanel contentPane;
	private JLabel timerLabel;
	private JButton startButton;
	JButton configTimeButton;
	private JProgressBar progressBar;

	private static DateFormat sdf = DateFormat.getTimeInstance();
	private static DateFormat timerFormat = new SimpleDateFormat("mm:ss");

	enum TimerState {
		STOPPED,
		RUNNING;
	}

	private TimerState status = TimerState.STOPPED;
	private long timerStartTime;
	private javax.swing.Timer timerTimer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Timer frame = new Timer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Timer() {
		this.setTitle("ISV Timer");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 1028, 717);
		this.contentPane = new JPanel();
		this.contentPane.setBackground(Color.WHITE);
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(this.contentPane);

		this.timerLabel = new JLabel("00:00");
		this.timerLabel.setVerticalAlignment(SwingConstants.CENTER);
		this.timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.timerLabel.setFont(new Font("Arial Black", this.timerLabel.getFont().getStyle(), 300));

		this.startButton = new JButton("START");
		this.startButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		this.startButton.setBackground(Color.GREEN);
		this.startButton.addActionListener(new StartButtonActionListener());

		JLabel clock = new JLabel(Timer.sdf.format(new Date()));
		clock.setHorizontalAlignment(SwingConstants.RIGHT);
		clock.setFont(new Font("Tahoma", Font.PLAIN, 60));

		this.progressBar = new JProgressBar();

		this.configTimeButton = new JButton("" + this.MAX_TIME);
		this.configTimeButton.setFont(new Font("Tahoma", Font.PLAIN, 30));

		GroupLayout gl_contentPane = new GroupLayout(this.contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane
				.createSequentialGroup().addContainerGap().addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(this.timerLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 982, Short.MAX_VALUE)
						.addComponent(this.progressBar, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 982, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup().addComponent(this.startButton,
								GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE).addGap(18).addComponent(
										this.configTimeButton, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
								.addGap(351).addComponent(clock, GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)))
				.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane
				.createSequentialGroup().addContainerGap().addComponent(this.timerLabel, GroupLayout.PREFERRED_SIZE, 394,
						Short.MAX_VALUE).addGap(36).addComponent(this.progressBar, GroupLayout.PREFERRED_SIZE, 60,
								GroupLayout.PREFERRED_SIZE).addGap(63).addGroup(gl_contentPane.createParallelGroup(
										Alignment.TRAILING, false).addComponent(clock, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(this.configTimeButton,
														GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(this.startButton, GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))
				.addContainerGap()));
		this.contentPane.setLayout(gl_contentPane);

		javax.swing.Timer clockTimer = new javax.swing.Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clock.setText(Timer.sdf.format(new Date()));
			}
		});
		clockTimer.start();
	}

	class StartButtonActionListener
			implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (Timer.this.status) {
			case STOPPED:
				Timer.this.timerStartTime = System.currentTimeMillis();
				Timer.this.progressBar.setMaximum(Math.toIntExact(Timer.this.MAX_TIME * 60 * 1000));
				Timer.this.progressBar.setValue(0);
				Timer.this.timerLabel.setText("00:00");

				Timer.this.timerTimer = new javax.swing.Timer(1000, new RecalculateTimerActionListener());
				Timer.this.timerTimer.start();

				Toolkit.getDefaultToolkit().beep();

				Timer.this.startButton.setText("STOP");
				Timer.this.startButton.setBackground(Color.RED);
				Timer.this.status = TimerState.RUNNING;

				Timer.this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
				Timer.this.configTimeButton.setEnabled(false);

				break;
			case RUNNING:
				int result = JOptionPane.showOptionDialog(Timer.this.contentPane,
						"Wollen Sie den Timer wirklich stoppen??\nDann wird die Zeit komplett auf 0:00 zurückgesetzt!",
						"Timer abbrechen?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, 1);
				if (result == JOptionPane.OK_OPTION) {
					Timer.this.timerTimer.stop();

					Timer.this.timerLabel.setText("00:00");
					Timer.this.progressBar.setValue(0);

					Timer.this.startButton.setText("START");
					Timer.this.startButton.setBackground(Color.GREEN);
					Timer.this.status = TimerState.STOPPED;

					Timer.this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					Timer.this.configTimeButton.setEnabled(true);
				}
				break;
			}
		}
	}

	class RecalculateTimerActionListener
			implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			long time = System.currentTimeMillis() - Timer.this.timerStartTime;
			Timer.this.timerLabel.setText(Timer.timerFormat.format(new Date(time)));
			Timer.this.progressBar.setValue(Math.toIntExact(time));

			// Ende
			if (time > (Timer.this.MAX_TIME * 60 * 1000)) {
				Timer.this.timerTimer.stop();

				Timer.this.progressBar.setForeground(Color.BLUE);
				Timer.this.contentPane.setBackground(Color.WHITE);

				Timer.this.startButton.setText("START");
				Timer.this.startButton.setBackground(Color.GREEN);
				Timer.this.status = TimerState.STOPPED;
				Timer.this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				Timer.this.configTimeButton.setEnabled(true);

				// Ende (10 Sekunden vorher)
			} else if (time > ((Timer.this.MAX_TIME * 60 * 1000) - 10 * 1000)) {
				Timer.this.progressBar.setForeground(Color.RED);
				Timer.this.contentPane.setBackground(Color.ORANGE);
				// Halbzeit (10 Sekunden vorher)
			} else if ((time > ((Timer.this.MAX_TIME * 30 * 1000) - 10 * 1000)) && (time < Timer.this.MAX_TIME * 30 * 1000)) {
				Timer.this.progressBar.setForeground(Color.ORANGE);
				Timer.this.contentPane.setBackground(Color.YELLOW);
			} else {
				Timer.this.progressBar.setForeground(Color.BLUE);
				Timer.this.contentPane.setBackground(Color.WHITE);
			}
		}
	}
}
