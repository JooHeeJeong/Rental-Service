import java.awt.EventQueue;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.*;
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;


public class MainFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */

	
	public static void main(String[] args) {
		
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					MainFrame frame = new MainFrame();
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
	

	
	public MainFrame() throws Exception {
		
		setTitle("판매자 / 고객");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 428, 225);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel welcomeLabel = new JLabel("환영합니다.  렌탈샵입니다.");
		welcomeLabel.setForeground(Color.GRAY);
		welcomeLabel.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		welcomeLabel.setBounds(128, 61, 166, 15);
		contentPane.add(welcomeLabel);
		
		// 판매자 버튼
		JButton sellerButton = new JButton("판매자");
		sellerButton.setForeground(Color.GRAY);
		sellerButton.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		sellerButton.setBounds(50, 116, 117, 48);
		contentPane.add(sellerButton);
		sellerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SellerMainFrame window1;
				try {
					window1 = new SellerMainFrame();
					window1.setVisible(true);
					dispose();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
				
				
		// 렌탈샵 유저 버튼
		JButton rentShopUserButton = new JButton("렌탈샵 이용자");
		rentShopUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		rentShopUserButton.setForeground(new Color(128, 128, 128));
		rentShopUserButton.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		rentShopUserButton.setBounds(249, 116, 117, 48);
		contentPane.add(rentShopUserButton);
		rentShopUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuyerMainFrame window2;
				try {
					window2 = new BuyerMainFrame();
					window2.setVisible(true);
					dispose();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		// 오늘의 날짜 출력
		JLabel dateLabel = new JLabel("2022.11.22");
		dateLabel.setForeground(new Color(128, 128, 128));
		dateLabel.setBounds(326, 10, 74, 15);
		contentPane.add(dateLabel);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date getToday = new Date();
		String today = format.format(getToday);
		dateLabel.setText(today);
	}

}
