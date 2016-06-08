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
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class Timer
		extends JFrame {

	// TODO Einfärben je nach Zeit (Halbzeit / Endezeit!)

	// TODO Zeit dynamisch machen
	private static long MAX_TIME = 1 * 30 * 1000;

	private JPanel contentPane;
	private JLabel timerLabel;
	private JButton startButton;
	private JProgressBar progressBar;

	private DateFormat sdf = DateFormat.getTimeInstance();
	private DateFormat timerFormat = new SimpleDateFormat("mm:ss");

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

		JLabel clock = new JLabel(this.sdf.format(new Date()));
		clock.setHorizontalAlignment(SwingConstants.RIGHT);
		clock.setFont(new Font("Tahoma", Font.PLAIN, 60));

		this.progressBar = new JProgressBar();
		GroupLayout gl_contentPane = new GroupLayout(this.contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane
				.createSequentialGroup().addContainerGap().addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(this.progressBar, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 982, Short.MAX_VALUE)
						.addComponent(this.timerLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 982, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup().addComponent(this.startButton,
								GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE).addPreferredGap(
										ComponentPlacement.RELATED, 469, Short.MAX_VALUE).addComponent(clock,
												GroupLayout.PREFERRED_SIZE, 263, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane
				.createSequentialGroup().addContainerGap().addComponent(this.timerLabel, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.RELATED).addComponent(
								this.progressBar, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE).addGap(63).addGroup(
										gl_contentPane.createParallelGroup(Alignment.TRAILING).addComponent(this.startButton,
												GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE).addComponent(clock,
														GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE))
				.addContainerGap()));
		this.contentPane.setLayout(gl_contentPane);

		javax.swing.Timer clockTimer = new javax.swing.Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clock.setText(Timer.this.sdf.format(new Date()));
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
				Timer.this.progressBar.setMaximum(Math.toIntExact(MAX_TIME));

				Timer.this.timerTimer = new javax.swing.Timer(1000, new RecalculateTimerActionListener());
				Timer.this.timerTimer.start();

				Toolkit.getDefaultToolkit().beep();
				
				Timer.this.startButton.setText("STOP");
				Timer.this.startButton.setBackground(Color.RED);
				Timer.this.status = TimerState.RUNNING;

				Timer.this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

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
			Timer.this.timerLabel.setText(Timer.this.timerFormat.format(new Date(time)));
			Timer.this.progressBar.setValue(Math.toIntExact(time));

			// Ende
			if (time > MAX_TIME) {
				Timer.this.timerTimer.stop();

				Timer.this.progressBar.setForeground(Color.BLUE);
				Timer.this.contentPane.setBackground(Color.WHITE);

				Timer.this.startButton.setText("START");
				Timer.this.startButton.setBackground(Color.GREEN);
				Timer.this.status = TimerState.STOPPED;
				Timer.this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				// Ende (10 Sekunden vorher)
			} else if (time > (MAX_TIME - 10 * 1000)) {
				Timer.this.progressBar.setForeground(Color.RED);
				Timer.this.contentPane.setBackground(Color.ORANGE);
			} else {
				Timer.this.progressBar.setForeground(Color.BLUE);
				Timer.this.contentPane.setBackground(Color.WHITE);
			}
		}
	}
}
