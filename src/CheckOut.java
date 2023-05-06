import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JCheckBox;

public class CheckOut extends JFrame {

	private JPanel contentPane;
	private JTextField numberTextField;
	private int count;
	Manager act = null;
	ObjectInputStream ois = null;
	ObjectOutputStream oos = null;
	Calendar getToday = Calendar.getInstance();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CheckOut frame = new CheckOut();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public CheckOut() throws Exception {
		
		try {
			// 파일을 읽는 객체 생성
			ois = new ObjectInputStream(new FileInputStream("out.txt"));
			// 매니저 생성자에 ois 객체를 넘겨 파일 복원
			act = new Manager(100, 100, ois);
			
			// 파일 닫기
			ois.close();
		} catch (FileNotFoundException fnfe) {
			showMessageDialog(null,"파일을 찾을 수 없습니다.");			// 파일이 없는 경우 - 익셉션 발생하는 경우 경고창
			act = new Manager(100, 100);
		} catch (Exception ex) {
			showMessageDialog(null,"저장된 파일을 읽어들이지 못했습니다.");	// 파일을 읽어들이지 못하는 경우 - 익셉션  발생 경고창
		} 
		
		setTitle("고객 체크아웃");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 430, 305);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// 체크 아웃 info
		JLabel infoLabel = new JLabel("** 반납하실 고객님의 전화번호를 입력해주세요 **");
		infoLabel.setForeground(new Color(94, 94, 94));
		infoLabel.setBounds(69, 20, 282, 15);
		contentPane.add(infoLabel);
		
		// 전화번호 입력 label
		JLabel phoneNumLabel = new JLabel("전화번호");
		phoneNumLabel.setForeground(new Color(99, 99, 99));
		phoneNumLabel.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		phoneNumLabel.setBounds(83, 47, 56, 15);
		contentPane.add(phoneNumLabel);
		
		// 전화번호 입력 TextField
		numberTextField = new JTextField();
		numberTextField.setBounds(135, 46, 116, 21);
		contentPane.add(numberTextField);
		numberTextField.setColumns(10);
		
		// 지불할 금액 label
		JLabel payLabel = new JLabel();
		payLabel.setText("지불하실 금액");
		payLabel.setForeground(new Color(80, 80, 80));
		payLabel.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		payLabel.setBounds(159, 198, 231, 21);
		contentPane.add(payLabel);
		
		// 고객 대여 상품명 보여주기 위한 label
		JLabel rentCodeLabel = new JLabel("대여 상품 :");
		rentCodeLabel.setForeground(new Color(99, 99, 99));
		rentCodeLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		rentCodeLabel.setBounds(48, 85, 301, 15);
		contentPane.add(rentCodeLabel);
		
		// 고객 대여 일자 보여주기 위한 label
		JLabel rentalDayLabel = new JLabel("대여일자 :");
		rentalDayLabel.setForeground(new Color(99, 99, 99));
		rentalDayLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		rentalDayLabel.setBounds(48, 110, 268, 15);
		contentPane.add(rentalDayLabel);
		
		// 고객 대여 물품 반납 예정 일자, 실제 반납일 보여주기 위한 label
		JLabel returnDayLabel = new JLabel("반납 예정 일자, 실제 반납일 : ");
		returnDayLabel.setForeground(new Color(99, 99, 99));
		returnDayLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		returnDayLabel.setBounds(47, 131, 343, 23);
		contentPane.add(returnDayLabel);
		
		// 해당 대여료를 반납하게 된 이유 보여주기 위한 label
		JLabel realReturnDayLabel = new JLabel("연체된 경우 지불할 비용에 연체료가 더해지게 됩니다.");
		realReturnDayLabel.setForeground(new Color(128, 136, 179));
		realReturnDayLabel.setFont(new Font("나눔고딕", Font.BOLD, 12));
		realReturnDayLabel.setBounds(48, 165, 343, 23);
		contentPane.add(realReturnDayLabel);
		
		
		// 전화번호 입력 확인 버튼
		JButton numberOkButton = new JButton("확인");
		numberOkButton.setBackground(new Color(211, 222, 233));
		numberOkButton.setForeground(new Color(128, 128, 128));
		numberOkButton.setBounds(263, 45, 72, 23);
		contentPane.add(numberOkButton);
		numberOkButton.addActionListener(new numOkActionListener(numberTextField, rentCodeLabel, rentalDayLabel, returnDayLabel, realReturnDayLabel, payLabel));
		numberTextField.addKeyListener(new keyAdapter(numberTextField, rentCodeLabel, rentalDayLabel, returnDayLabel, realReturnDayLabel, payLabel));	
		

		// 대여료 계산법 제시(고객이 왜 토탈 금액이 이렇게 나오게 되었는지 알 수 있도록)
		JLabel principleOfCalPay = new JLabel("# 상품 금액 총 합 x 실제 대여 기간 + (연체료(연체되었을 경우))");
		principleOfCalPay.setForeground(new Color(192, 192, 192));
		principleOfCalPay.setFont(new Font("맑은 고딕", Font.BOLD, 10));
		principleOfCalPay.setBounds(102, 251, 300, 15);
		contentPane.add(principleOfCalPay);

	
		// 돈 지불 확인 label
		JLabel payOkLabel = new JLabel("돈을 지불하시겠습니까?");
		payOkLabel.setForeground(new Color(128, 128, 128));
		payOkLabel.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		payOkLabel.setBounds(80, 229, 160, 15);
		contentPane.add(payOkLabel);
		
		
		// 돈 지불 확인 버튼
		JButton payOkButton = new JButton("Yes");
		payOkButton.setBackground(new Color(222, 231, 239));
		payOkButton.setForeground(new Color(128, 128, 128));
		payOkButton.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		payOkButton.setBounds(248, 226, 56, 21);
		contentPane.add(payOkButton);
		payOkButton.addActionListener(new payOkActionListener(numberTextField));
		payOkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuyerMainFrame back;
				try {
					back = new BuyerMainFrame();
					back.setVisible(true);
					dispose();
					
				} catch (Exception e1) {
					showMessageDialog(null,"기존 고객 메인 화면 전환이 불가합니다.");
				}
			}
		});
		
		
		// 돈 지불 취소 버튼
		JButton noButton = new JButton("No");
		noButton.setForeground(Color.GRAY);
		noButton.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		noButton.setBackground(new Color(222, 231, 239));
		noButton.setBounds(310, 226, 56, 21);
		contentPane.add(noButton);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(22, 41, 368, 209);
		contentPane.add(panel);
		noButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showMessageDialog(null,"체크아웃이 취소되었습니다.");
				BuyerMainFrame back;
				try {
					back = new BuyerMainFrame();
					back.setVisible(true);
					dispose();
					
				} catch (Exception e1) {
					showMessageDialog(null,"기존 고객 메인 화면 전환이 불가합니다.");
				}
			}
		});
	}
	
	// 고객 전화번호 확인 버튼 대신 엔터 눌렀을 경우 이벤트 발생 클래스 - 엔터 누르면 고객의 렌트 목록 보여주기
	class keyAdapter implements KeyListener {
		JLabel label1, label2, label3, label4, label5;
		JTextField text;
		keyAdapter(JTextField text, JLabel label1, JLabel label2, JLabel label3, JLabel label4, JLabel label5) {	
			this.text = text;
			this.label1 = label1;	// 대여 상품명 보여주기 위한 label
			this.label2 = label2;	// 대여 일자 보여주기 위한 label
			this.label3 = label3;	// 반납 예정 일자, 실제 반납 일자 보여주기 위한 label
			this.label4 = label4;	// 반납하게 된 이유 설명해주기 위한 label
			this.label5 = label5;	// 지불 금액 보여주기 위한 label
		}
		public void keyPressed(KeyEvent e) {
			if (e.getKeyChar() == '\n') {
				try {
					// 일치하는 전화번호 확인
					String number = text.getText();
					int index = act.searchUser(number);
					User u = act.userAt(index);
						
					// user 객체에서 필요한 정보 추출
					String name = act.userAt(index).getName();
					String phone = act.userAt(index).getPhone();
					String rentalDay = act.userAt(index).getRentalDay();
					String returnDay = act.userAt(index).getReturnDay();
						
					// 반납 일자 입력하기
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date getToday = new Date();
					String today = format.format(getToday);
						
					if(count != 0) {	// 대여 상품 목록을 중복으로 보여주지 않도록
						label1.setText("대여 상품 :");
					}
					
					// 출력
					for(int k = 0; k < act.userAt(index).getRentalCount(); k++)
					{
						String code = act.userAt(index).codeAt(k);
						int pCodeIndex = act.search(code);
						String productName = act.productAt(pCodeIndex).getName();
						label1.setText(label1.getText() + "   "+ productName);
					}
						
					label2.setText((String)"대여 일자 :   " + rentalDay);
					label3.setText((String)"반납 예정일 : " + returnDay + "    반납일 : " + today); 
						
						
					// 돈 금액 계산하기
					int money = act.userAt(index).pay();
					label5.setText((String) "지불하실 금액은 " + money + "원입니다."); // 지불 금액 출력
						
					// 해당 지불 비용이 나온 원인 제시
					int overdueDay = u.realReturnDayCal() - u.returnDayCal();
					if(overdueDay <= 0) {
						overdueDay = 0;
						label4.setText("");}	
					else
						label4.setText("연체일 :   " + overdueDay + " 일  " + "     연체료 :   " + (overdueDay * money) + " 원");
					// 파일 저장
					oos = new ObjectOutputStream(new FileOutputStream("out.txt"));	 // 객체 생성
					act.saveToFile(oos);											// manager객체 내의 saveToFile 함수 호출
					oos.close();
					count++;
				} 
				catch (Exception e1) {
					// 전화번호를 통한 고객 정보 확인이 되지 않는 경우 익셉션 발생
					showMessageDialog(null,"해당 고객 정보를 찾을 수 없습니다.\n            다시 입력해주세요.");
				}
			}
		}
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}
	}
	
	
	// 고객 전화번호 확인 버튼을 처리하는 리스너 클래스 - 버튼 클릭하면 고객의 렌트 목록 보여주기
	class numOkActionListener implements ActionListener {
		JLabel label1, label2, label3, label4, label5;
		JTextField text;
		
		numOkActionListener(JTextField text, JLabel label1, JLabel label2, JLabel label3, JLabel label4, JLabel label5) {	
			this.text = text;
			this.label1 = label1;	// 대여 상품명 보여주기 위한 label
			this.label2 = label2;	// 대여 일자 보여주기 위한 label
			this.label3 = label3;	// 반납 예정 일자, 실제 반납 일자 보여주기 위한 label
			this.label4 = label4;	// 반납하게 된 이유 설명해주기 위한 label
			this.label5 = label5;	// 지불 금액 보여주기 위한 label
		}
		public void actionPerformed(ActionEvent e) {
			try {
				// 일치하는 전화번호 확인
				String number = text.getText();
				int index = act.searchUser(number);
				User u = act.userAt(index);
				
				// user 객체에서 필요한 정보 추출
				String name = act.userAt(index).getName();
				String phone = act.userAt(index).getPhone();
				String rentalDay = act.userAt(index).getRentalDay();
				String returnDay = act.userAt(index).getReturnDay();
				
				
				// 반납 일자 입력하기
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date getToday = new Date();
				String today = format.format(getToday);
				
				if(count != 0) {
					label1.setText("대여 상품 :");
				}
				
				// 출력
				for(int k = 0; k < act.userAt(index).getRentalCount(); k++)
				{
					String code = act.userAt(index).codeAt(k);
					int pCodeIndex = act.search(code);
					String productName = act.productAt(pCodeIndex).getName();
					label1.setText(label1.getText() + "   "+ productName);
				}
				
				label2.setText((String)"대여 일자 :   " + rentalDay);
				label3.setText((String)"반납 예정일 : " + returnDay + "    반납일 : " + today); 
				
				
				// 돈 금액 계산하기
				int money = act.userAt(index).pay();
				label5.setText((String) "지불하실 금액은 " + money + "원입니다."); // 지불 금액 출력
				
				// 해당 지불 비용이 나온 원인 제시
				int overdueDay = u.realReturnDayCal() - u.returnDayCal();
				if(overdueDay <= 0) {
					overdueDay = 0;
					label4.setText("");
				}
				
				else
					label4.setText("연체일 :   " + overdueDay + " 일  " + "     연체료 :   " + (overdueDay * money) + " 원");
				
				
				// 파일 저장
				oos = new ObjectOutputStream(new FileOutputStream("out.txt"));	 // 객체 생성
				act.saveToFile(oos);											// manager객체 내의 saveToFile 함수 호출
				oos.close();
				
			} 
			catch (Exception e1) {
				// 전화번호를 통한 고객 정보 확인이 되지 않는 경우 익셉션 발생
				showMessageDialog(null,"해당 고객 정보를 찾을 수 없습니다.\n            다시 입력해주세요.");
			}
		}
	}
			
		
	// 고객 전화번호 확인 버튼을 처리하는 리스너 클래스 - 버튼 클릭하면 고객의 렌트 목록 보여주기
	class payOkActionListener implements ActionListener {
		JTextField text;
		payOkActionListener(JTextField text) {	
			this.text = text;
		}
		public void actionPerformed(ActionEvent e) {
			try {
				// 일치하는 전화번호 확인
				String number = text.getText();
				int index = act.searchUser(number);
					
				act.checkOut(index);
					
				// 파일 저장
				oos =new ObjectOutputStream(new FileOutputStream("out.txt"));	 // 객체 생성
				act.saveToFile(oos);											// manager객체 내의 saveToFile 함수 호출
				oos.close();
				showMessageDialog(null,"체크아웃이 완료되었습니다.");
			} 
			catch (Exception e1) {
				// 체크아웃되지 않는 경우 익셉션 발생
				showMessageDialog(null,"잘못된 방법의 체크아웃입니다.");
			}
		}

	}
}
