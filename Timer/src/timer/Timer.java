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
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class Timer
		extends JFrame {

	private long MAX_TIME = 13;

	private JPanel contentPane;
	private JLabel timerLabel;
	private JButton startButton;
	private JProgressBar progressBar;
	private JLabel achtungLabel;
	private static DateFormat sdf = DateFormat.getTimeInstance();
	private static DateFormat timerFormat = new SimpleDateFormat("mm:ss");

	enum TimerState {
		STOPPED,
		RUNNING;
	}

	private TimerState status = TimerState.STOPPED;
	private long timerStartTime;
	private javax.swing.Timer timerTimer;
	private JLabel timerTimeLabel;
	private JButton minusBtn;
	private JButton plusBtn;

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
		setIconImage(Toolkit.getDefaultToolkit().getImage(Timer.class.getResource("/timer/favicon.ico")));
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
		
		JLabel label = new JLabel("        ");
		
		minusBtn = new JButton("-");
		minusBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (MAX_TIME > 1) {
					MAX_TIME--;
					timerTimeLabel.setText("" + MAX_TIME);
				}
			}
		});
		minusBtn.setFont(new Font("Tahoma", Font.PLAIN, 30));
		
		timerTimeLabel = new JLabel("13");
		timerTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timerTimeLabel.setFont(new Font("Tahoma", Font.PLAIN, 40));
		
		plusBtn = new JButton("+");
		plusBtn.setIcon(null);
		plusBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (MAX_TIME < 31) {
					MAX_TIME++;
					timerTimeLabel.setText("" + MAX_TIME);
				}
			}
		});
		plusBtn.setFont(new Font("Tahoma", Font.PLAIN, 30));
		
		achtungLabel = new JLabel("");
		achtungLabel.setForeground(Color.RED);
		achtungLabel.setFont(new Font("Tahoma", Font.BOLD, 50));
		achtungLabel.setHorizontalAlignment(SwingConstants.CENTER);

		GroupLayout gl_contentPane = new GroupLayout(this.contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(timerLabel, GroupLayout.DEFAULT_SIZE, 976, Short.MAX_VALUE)
						.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 976, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(startButton, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(minusBtn, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(timerTimeLabel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(plusBtn)
							.addPreferredGap(ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
							.addComponent(clock, GroupLayout.PREFERRED_SIZE, 281, GroupLayout.PREFERRED_SIZE))
						.addComponent(achtungLabel, GroupLayout.DEFAULT_SIZE, 976, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(timerLabel, GroupLayout.PREFERRED_SIZE, 325, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(achtungLabel, GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
					.addGap(32)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(startButton, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
						.addComponent(clock)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(minusBtn, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
								.addComponent(timerTimeLabel, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
								.addComponent(plusBtn, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
							.addComponent(label, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)))
					.addContainerGap())
		);
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
				Timer.this.achtungLabel.setText("");
				
				Timer.this.timerTimer = new javax.swing.Timer(1000, new RecalculateTimerActionListener());
				Timer.this.timerTimer.start();

				Toolkit.getDefaultToolkit().beep();

				Timer.this.startButton.setText("STOP");
				Timer.this.startButton.setBackground(Color.RED);
				Timer.this.status = TimerState.RUNNING;

				Timer.this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
				
				Timer.this.minusBtn.setEnabled(false);
				Timer.this.plusBtn.setEnabled(false);

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
					Timer.this.minusBtn.setEnabled(true);
					Timer.this.plusBtn.setEnabled(true);
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
				Timer.this.achtungLabel.setText("");

				Toolkit.getDefaultToolkit().beep();

				Timer.this.progressBar.setForeground(Color.BLUE);
				Timer.this.contentPane.setBackground(Color.WHITE);

				Timer.this.startButton.setText("START");
				Timer.this.startButton.setBackground(Color.GREEN);
				Timer.this.status = TimerState.STOPPED;
				Timer.this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				Timer.this.minusBtn.setEnabled(true);
				Timer.this.plusBtn.setEnabled(true);

				// Ende (10 Sekunden vorher)
			} else if (time > ((Timer.this.MAX_TIME * 60 * 1000) - 10 * 1000)) {
				Timer.this.progressBar.setForeground(Color.RED);
				Timer.this.contentPane.setBackground(Color.YELLOW);
				Timer.this.achtungLabel.setText("ACHTUNG! Spielende!!!");
				Timer.this.achtungLabel.setForeground(Color.RED);
				// Halbzeit (10 Sekunden vorher)
			} else if ((time > ((Timer.this.MAX_TIME * 30 * 1000) - 10 * 1000)) && (time < Timer.this.MAX_TIME * 30 * 1000)) {
				Timer.this.progressBar.setForeground(Color.ORANGE);
				Timer.this.contentPane.setBackground(Color.GREEN);
				Timer.this.achtungLabel.setForeground(Color.MAGENTA);
				Timer.this.achtungLabel.setText("ACHTUNG! Halbzeit!!!");
			} else {
				Timer.this.progressBar.setForeground(Color.BLUE);
				Timer.this.contentPane.setBackground(Color.WHITE);
				Timer.this.achtungLabel.setText("");
			}
		}
	}
}
