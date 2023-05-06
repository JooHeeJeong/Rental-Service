import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.Color;
import javax.swing.JButton;

public class CheckSales extends JFrame {

	private JPanel contentPane;
	Manager act = null;
	ObjectInputStream ois = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CheckSales frame = new CheckSales();
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
	public CheckSales() {
		try {
			// 파일을 읽는 객체 생성
			ois = new ObjectInputStream(new FileInputStream("out.txt"));
			// 매니저 생성자에 ois 객체를 넘겨 파일 복원
			act = new Manager(100, 100, ois);
			
			// 파일 닫기
			ois.close();
		} catch (FileNotFoundException fnfe) {
			showMessageDialog(null,"파일을 찾을 수 없습니다.");			// 파일이 없는 경우 - 익셉션 발생하는 경우 경고창
		} catch (Exception ex) {
			showMessageDialog(null,"저장된 파일을 읽어들이지 못했습니다.");	// 파일을 읽어들이지 못하는 경우 - 익셉션  발생 경고창
		} 
		setTitle("매출 확인");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 389, 219);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel dateLabel = new JLabel("2022.11.22");
		dateLabel.setBounds(295, 10, 66, 15);
		contentPane.add(dateLabel);
		
		// 총매출 frame label
		JLabel saleLabel = new JLabel("총매출");
		saleLabel.setForeground(new Color(99, 99, 99));
		saleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		saleLabel.setBounds(44, 37, 64, 25);
		contentPane.add(saleLabel);
		
		// - 원 label
		JLabel wonLabel = new JLabel("원");
		wonLabel.setForeground(new Color(99, 99, 99));
		wonLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		wonLabel.setBounds(277, 105, 20, 25);
		contentPane.add(wonLabel);
		
		
		// 이전 화면 이동 버튼 (back button)
		JButton backButton = new JButton("Back");
		backButton.setForeground(Color.GRAY);
		backButton.setFont(new Font("Yu Gothic UI", Font.BOLD, 11));
		backButton.setBounds(162, 7, 60, 20);
		contentPane.add(backButton);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame back;
				try {
					back = new MainFrame();
					back.setVisible(true);
					dispose();
					
				} catch (Exception e1) {
					showMessageDialog(null,"화면 전환이 불가합니다.");
				}
			}
		});
		
		
		// MainFrame 이동 버튼 (Home button)
		JButton homeButton = new JButton("Home");
		homeButton.setForeground(Color.GRAY);
		homeButton.setFont(new Font("Yu Gothic UI", Font.BOLD, 9));
		homeButton.setBounds(222, 7, 60, 20);
		contentPane.add(homeButton);
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame home;
				try {
					home = new MainFrame();
					home.setVisible(true);
					dispose();
				} catch (Exception e2) {
					showMessageDialog(null,"창 전환이 불가합니다.");
				}
			}
		});
		
		// 매출 총액
		String revenue = Integer.toString(act.getRevenue());
		JLabel totalSaleLabel = new JLabel(revenue);
		totalSaleLabel.setForeground(new Color(94, 94, 94));
		totalSaleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		totalSaleLabel.setBounds(195, 105, 70, 25);
		contentPane.add(totalSaleLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(32, 61, 315, 97);
		contentPane.add(panel);
	}

}
