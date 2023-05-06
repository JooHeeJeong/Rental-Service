import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.DropMode;

import static javax.swing.JOptionPane.showMessageDialog;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.jdatepicker.JDatePicker;

import eu.schudt.javafx.controls.calendar.DatePicker;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.awt.event.ActionEvent;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import javax.swing.UIManager;
import javax.swing.SwingConstants;
import javax.swing.SpringLayout;
import javax.swing.JFormattedTextField;

public class BuyerMainFrame extends JFrame {

	private JPanel totalContentPane;
	private JTable table;
	private JTable productTable;
	private JTable toBeRentedTable;

	Manager act = null;
	ObjectInputStream ois = null;
	ObjectOutputStream oos = null;
	Calendar getToday = Calendar.getInstance();
	int clickCount = 0;
	
	private JTable productTable1;
	private JTextField userNameTextField;
	private JTextField numberTextField;
	//private JTextField returndayTextField;
	private JTextField productSearchTF;
	private SpringLayout springLayout;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BuyerMainFrame frame = new BuyerMainFrame();
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
	public BuyerMainFrame() throws Exception {

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
			act = new Manager(100, 100);
		} 
		
		setTitle("구매자 Ui");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 626, 461);
		totalContentPane = new JPanel();
		totalContentPane.setBackground(new Color(232, 236, 242));
		totalContentPane.setBorder(null);

		setContentPane(totalContentPane);
		totalContentPane.setLayout(null);
		JLabel welcomeUserLabel = new JLabel("환영합니다. 렌탈샵 이용자님 ----");
		welcomeUserLabel.setBounds(12, 11, 184, 15);
		totalContentPane.add(welcomeUserLabel);
		// 오늘의 날짜 보여주기
		JLabel dateLabel = new JLabel("yyyy-MM-dd");
		dateLabel.setForeground(new Color(128, 128, 128));
		dateLabel.setBounds(538, 11, 69, 15);
		totalContentPane.add(dateLabel);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date getToday = new Date();
		String today = format.format(getToday);
		dateLabel.setText(today);

		
		// back 버튼 - 이전 화면으로 이동
		JButton backButton = new JButton("back");
		backButton.setBounds(370, 7, 74, 21);
		backButton.setFont(new Font("맑은 고딕", Font.BOLD, 11));
		backButton.setForeground(new Color(99, 99, 99));
		totalContentPane.add(backButton);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame back;
				try {
					back = new MainFrame();
					back.setVisible(true);
					dispose();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
		// 상품 목록
		JLabel productListLabel = new JLabel("상품 목록");
		productListLabel.setBounds(12, 33, 74, 21);
		productListLabel.setForeground(new Color(128, 128, 128));
		productListLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		totalContentPane.add(productListLabel);
		
		
		// productTable 보여주기
		JScrollPane productTablescrollPane = new JScrollPane();
		productTablescrollPane.setBounds(12, 62, 586, 154);
		totalContentPane.add(productTablescrollPane);
				
		productTable = new JTable();
		productTable.setFont(new Font("굴림", Font.PLAIN, 10));
		productTable.setForeground(new Color(0, 0, 0));
		productTablescrollPane.setViewportView(productTable);
		String colnames[] = {"상품 코드", "상품명", "상품 개수", "상품 가격"};
		productTable.setModel(new DefaultTableModel(colnames, 0));
		DefaultTableModel model = (DefaultTableModel) productTable.getModel();

		for(int i = 0; i < act.getProductCount(); i++)
		{
			// product 객체에서 필요한 정보 추출
			String name = act.productAt(i).getName();
			String code = act.productAt(i).getCode();
			int number = act.productAt(i).getNumber();
			int price = act.productAt(i).getPrice();

			// 출력
			String arr[] = new String[4];
			arr[0] = code;
			arr[1] = name;
			arr[2] = Integer.toString(number);
			arr[3] = Integer.toString(price);
					
			model.addRow(arr);
		}		
		
		
		

		// 렌트될 상품 테이블
		JLabel productToBeRentLabel = new JLabel("렌트할 상품");
		productToBeRentLabel.setForeground(new Color(128, 128, 128));
		productToBeRentLabel.setBounds(12, 240, 89, 15);
		productToBeRentLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		totalContentPane.add(productToBeRentLabel);
		
		
		// 렌트할 상품 table 보여주기 (장바구니 역할)
		JScrollPane toBeRentedScrollPane = new JScrollPane();
		toBeRentedScrollPane.setBounds(0, 265, 610, 118);
		totalContentPane.add(toBeRentedScrollPane);
		
		toBeRentedTable = new JTable();
		toBeRentedTable.setForeground(new Color(99, 99, 99));
		toBeRentedTable.setFont(new Font("Yu Gothic UI", Font.BOLD, 11));
		
		String colname[] = {"상품 코드", "상품명", "상품 개수", "상품 가격"};
		toBeRentedTable.setModel(new DefaultTableModel(colname, 0));
		DefaultTableModel model2 = (DefaultTableModel) toBeRentedTable.getModel();
		toBeRentedScrollPane.setViewportView(toBeRentedTable);
		productTable.addMouseListener(new Mouse(productTable, toBeRentedTable));		// 마우스로 테이블의 row 클릭했을 경우 이벤트 발생
		
						
				
		// 취소하기 버튼
		JButton cancelButton = new JButton("취소하기");
		cancelButton.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		cancelButton.setForeground(new Color(128, 128, 128));
		cancelButton.setBounds(494, 238, 101, 21);
		totalContentPane.add(cancelButton);
		cancelButton.addActionListener(new CancelActionListener(productTable));			// [취소하기] 버튼 누를 경우 이벤트 발생
		
		
		// 체크인을 위한 고객 정보
		// 고객 이름
		JLabel userName = new JLabel("고객 이름");
		userName.setForeground(new Color(97, 97, 97));
		userName.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		userName.setBounds(10, 389, 69, 28);
		totalContentPane.add(userName);
				
		// 고객 이름 textField
		userNameTextField = new JTextField();
		userNameTextField.setBounds(75, 389, 54, 28);
		totalContentPane.add(userNameTextField);
		userNameTextField.setColumns(10);
		
						
		// 고객 전화번호
		JLabel userNumber = new JLabel("전화번호");
		userNumber.setForeground(new Color(95, 95, 95));
		userNumber.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		userNumber.setBounds(141, 393, 63, 21);
		totalContentPane.add(userNumber);
				
		// 고객 전화번호 textField
		numberTextField = new JTextField();
		numberTextField.setColumns(10);
		numberTextField.setBounds(199, 389, 80, 28);
		totalContentPane.add(numberTextField);
						
		// 반납 예정 일자
		JLabel returnDay = new JLabel("반납 일자");
		returnDay.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		returnDay.setForeground(new Color(95, 95, 95));
		returnDay.setBounds(291, 389, 63, 29);
		totalContentPane.add(returnDay);
				
		
				
		//캘린더 보여주기
		UtilDateModel dateModel = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(dateModel);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
		datePicker.getJFormattedTextField().setForeground(new Color(128, 128, 128));
		datePicker.getJFormattedTextField().setText("... 클릭해주세요");
		datePicker.getJFormattedTextField().setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 11));
		datePicker.getJFormattedTextField().setBackground(new Color(255, 255, 255));
		totalContentPane.add(datePicker);
		datePicker.setBounds(355, 389, 118, 28);
		
		
		// 렌트하기 버튼
		JButton rentButton = new JButton("렌트하기");
		rentButton.setForeground(Color.GRAY);
		rentButton.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		rentButton.setBounds(494, 387, 113, 32);
		totalContentPane.add(rentButton);
		rentButton.addActionListener(new RentActionListener(toBeRentedTable, userNameTextField, numberTextField, dateModel));
		//datePicker.addKeyListener(new keyAdapter(toBeRentedTable, userNameTextField, numberTextField, dateModel));
		
		// 체크아웃 버튼
		JButton checkOutButton = new JButton("체크아웃");
		checkOutButton.setForeground(new Color(99, 99, 99));
		checkOutButton.setFont(new Font("맑은 고딕", Font.BOLD, 11));
		checkOutButton.setBounds(446, 7, 80, 21);
		totalContentPane.add(checkOutButton);
		
		
		
		// 상품 검색
		productSearchTF = new JTextField();
		productSearchTF.setColumns(10);
		productSearchTF.setBounds(446, 39, 89, 15);
		totalContentPane.add(productSearchTF);
		
		// 상품 검색 버튼
		JButton productSearch = new JButton("검색");
		productSearch.setForeground(Color.GRAY);
		productSearch.setFont(new Font("나눔고딕", Font.PLAIN, 8));
		productSearch.setBounds(538, 40, 54, 15);
		totalContentPane.add(productSearch);
		TableRowSorter<TableModel> rowSorter
        = new TableRowSorter<>(productTable.getModel());
		productTable.setRowSorter(rowSorter);
		
		
		
		// 물품 검색 리스너(상품코드 ,상품명 검색 모두 가능)
		// 대소문자 상관 없이 검색 가능
		productSearchTF.getDocument().addDocumentListener(new DocumentListener(){
	    public void insertUpdate(DocumentEvent e) {
	            String text = productSearchTF.getText();

	            if (text.trim().length() == 0) {	// 문자열 공백
	                rowSorter.setRowFilter(null);
	            } else {
	            	rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));	// 대소문자 상관 없이 검색
	            	//rowSorter.setRowFilter(RowFilter.regexFilter(text));			// 대소문자 구분하여 검색
	            }
	        }
	        public void removeUpdate(DocumentEvent e) {
	            String text = productSearchTF.getText();

	            if (text.trim().length() == 0) {
	                rowSorter.setRowFilter(null);	// row의 sort, filter 동시에 진행
	            } else {
	                rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));	// 대소문자 상관 없이 검색
	                //rowSorter.setRowFilter(RowFilter.regexFilter(text));			// 대소문자 구분하여 검색
	            }
	        }
	        public void changedUpdate(DocumentEvent e) {
	            throw new UnsupportedOperationException("해당 서비스는 지원되지 않습니다."); // ArrayList에 값을 추가 / 변경 / 삭제하려는 경우 익셉션 발생
	        }
	    });
		
		JPanel writeBackgroundPanel = new JPanel();
		writeBackgroundPanel.setBackground(new Color(255, 255, 255));
		writeBackgroundPanel.setBounds(0, 33, 607, 229);
		totalContentPane.add(writeBackgroundPanel);


		
		// 체크아웃 버튼
		checkOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CheckOut CheckOut;
				try {
					CheckOut = new CheckOut();
					CheckOut.setVisible(true);
					dispose();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
	
	
	// (상품 테이블 -> 렌트할 상품) 마우스 이벤트 - 테이블 클릭시 상품 코드와 상품명, 상품 개수, 상품 가격 보여주기 
	class Mouse implements MouseListener {
		JTable table1, table2;
		
			
		Mouse(JTable table1, JTable table2) {
			this.table1 = table1;
			this.table2 = table2;
		}
		
			
		public void mouseClicked(MouseEvent e) {
			clickCount++;
			DefaultTableModel model = (DefaultTableModel) table2.getModel();
			int row = table1.getSelectedRow();	
					
			Object code = table1.getValueAt(row, 0);
			Object name = table1.getValueAt(row, 1);
			Object num = table1.getValueAt(row, 2);
			Object price = table1.getValueAt(row, 3);
			int numb = Integer.parseInt(num.toString());	
			String productCode = code.toString();
			String productName = name.toString();
			int productNum = 1;							// 한 번의 클릭당 한 개의 상품이 장바구니에 들어가도록 하기 위함
			int productPrice = Integer.parseInt(price.toString());
			if(clickCount <= 3 & numb > 0) {			// 1인당 최대 3개의 상품만 & 상품 재고가 있어야 렌트 가능
				// 출력
				String arr[] = new String[4];
				arr[0] = productCode;
				arr[1] = productName;
				arr[2] = Integer.toString(productNum);
				arr[3] = Integer.toString(productPrice);
							
				model.addRow(arr);
			}
			else {
				showMessageDialog(null,"   장바구니에 상품을 추가할 수 없습니다.    \n   (최대 3개의 상품만 렌트 가능합니다.)");
			}
			
		}
			
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseDragged(MouseEvent e) {}
		public void mouseMoved(MouseEvent e) {}
	}
	
	

	// 물품 [취소하기] 버튼을 처리하는 리스너 클래스 - 체크박스 클릭하면 선택된 row의 상품을 삭제
	class CancelActionListener implements ActionListener {
		JTable table;
		CancelActionListener(JTable table) {		
			this.table = table;
		}
		public void actionPerformed(ActionEvent e) {
			try {
					DefaultTableModel model = (DefaultTableModel) toBeRentedTable.getModel();
					int row = toBeRentedTable.getSelectedRow();	
					
					model.removeRow(row);						// 렌트할 상품 테이블(장바구니) 내에서 해당 row 제거
					clickCount--;
			} catch (Exception e1) {
				// 물품 취소가 제대로 되지 않는 경우
				showMessageDialog(null,"취소할 물품을 선택해주세요.");
			}
		}
	}
	
	// 물품 [렌트하기] 버튼을 처리하는 리스너 클래스
	class RentActionListener implements ActionListener {
		JTable table;
		JTextField text1, text2;
		UtilDateModel returnD;

		RentActionListener(JTable table, JTextField text1, JTextField text2, UtilDateModel returnD){
			this.table = table;
			this.text1 = text1;				// 고객 이름
			this.text2 = text2;				// 고객 전화번호
			this.returnD = returnD;			// 반납 예정 일자
		}
			
		public void actionPerformed(ActionEvent e) {
			clickCount = 0;
			ObjectOutputStream oos = null;
			try {
				// User 객체 생성
				String userName = text1.getText();
				String userPhone = text2.getText();
				String rentalDay = new SimpleDateFormat("yyyy-MM-dd").format(getToday.getTime()); 				// 대여 일자
				String returnDay = returnD.getYear() + "-" + (returnD.getMonth() + 1) + "-" + returnD.getDay();	// 반납 예정 일자

				User u1;
				u1 = new User(userName, userPhone, rentalDay, returnDay);
				DefaultTableModel model = (DefaultTableModel) toBeRentedTable.getModel();
				for(int i = 0; i < model.getRowCount(); i++)
				{
					String code =  (String) table.getValueAt(i, 0);
					String name = (String) table.getValueAt(i, 1);
					int num = Integer.parseInt(table.getValueAt(i, 2).toString());
					int price = Integer.parseInt(table.getValueAt(i, 2).toString());
						
					int index = act.search(code);
					int amount = act.productAt(index).getPrice();
					u1.addProduct(code, amount); // 대여 물품 코드 배열에 코드 추가
				}
				
				act.checkIn(u1);
				// 파일 저장
				oos =new ObjectOutputStream(new FileOutputStream("out.txt"));	 // 객체 생성
				act.saveToFile(oos);											// manager객체 내의 saveToFile 함수 호출
				oos.close();
	
				text1.setText("");
				text2.setText("");

				showMessageDialog(null,"렌트가 완료되었습니다.");
			}catch (FileNotFoundException fnfe) {
				JOptionPane fnfes =new JOptionPane();
				fnfes.showMessageDialog(null, "파일을 찾을 수 없습니다.");			// 저장할 파일을 찾지 못한 경우 경고창 발생
			}catch (Exception ex) {
				showMessageDialog(null,"상품을 렌트하지 못 했습니다.");				// 상품을 렌트하지 못한 경우 익셉션 경고창 발생
			} 
		}
	}
		
		
	// 물품 [렌트하기] 버튼 대신 엔터키로 처리하는 리스너 클래스
	class keyAdapter implements KeyListener {
		JTable table;
		JTextField text1, text2;
		UtilDateModel returnD; 		
		
		keyAdapter(JTable table, JTextField text1, JTextField text2, UtilDateModel returnD){
			this.table = table;
			this.text1 = text1;				// 고객 이름
			this.text2 = text2;				// 고객 전화번호
			//this.text3 = text3;			// 반납 예정 일자
			this.returnD = returnD;			// 반납 예정 일자
		}
					
		public void keyPressed(KeyEvent e) {
			if (e.getKeyChar() == '\n') {
				clickCount = 0;
				ObjectOutputStream oos = null;
				try {
					// User 객체 생성
					String userName = text1.getText();
					String userPhone = text2.getText();
					String rentalDay = new SimpleDateFormat("yyyy-MM-dd").format(getToday.getTime()); 				// 대여 일자
					//String returnDay = text3.getText();
					String returnDay = returnD.getYear() + "-" + (returnD.getMonth() + 1) + "-" + returnD.getDay();	// 반납 예정 일자
					
					User u1;
					u1 = new User(userName, userPhone, rentalDay, returnDay);
					DefaultTableModel model = (DefaultTableModel) toBeRentedTable.getModel();
					for(int i = 0; i < model.getRowCount(); i++)
					{
						String code =  (String) table.getValueAt(i, 0);
						String name = (String) table.getValueAt(i, 1);
						int num = Integer.parseInt(table.getValueAt(i, 2).toString());
						int price = Integer.parseInt(table.getValueAt(i, 2).toString());
								
						int index = act.search(code);
						int amount = act.productAt(index).getPrice();
						u1.addProduct(code, amount); // 대여 물품 코드 배열에 코드 추가
					}
						
					act.checkIn(u1);
					// 파일 저장
					oos =new ObjectOutputStream(new FileOutputStream("out.txt"));	 // 객체 생성
					act.saveToFile(oos);											// manager객체 내의 saveToFile 함수 호출
					oos.close();
							
					text1.setText("");
					text2.setText("");
					//text3.setText("yyyy-MM-dd");
					
					showMessageDialog(null,"렌트가 완료되었습니다.");
				}catch (FileNotFoundException fnfe) {
					JOptionPane fnfes =new JOptionPane();
					fnfes.showMessageDialog(null, "파일을 찾을 수 없습니다.");			// 저장할 파일을 찾지 못한 경우 경고창 발생
				}catch (Exception ex) {
					showMessageDialog(null,"상품을 렌트하지 못 했습니다.");				// 상품을 렌트하지 못한 경우 익셉션 경고창 발생
				}
			}
		}
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}
	}
}
