import java.awt.EventQueue;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JScrollBar;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JTabbedPane;
import javax.swing.JCheckBox;
import javax.swing.JToolBar;
import javax.swing.RowFilter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;

import static javax.swing.JOptionPane.*;


import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
public class SellerMainFrame extends JFrame {


	private JPanel contentPane;
	private JTable productTable;
	private JTable lenderInfoTable;
	private JTextField productSearchTF;
	private JTextField addProductCodeTF;
	private JTextField addProductNameTF;
	private JTextField addProductNumTF;
	private JTextField addProductPriceTF;
	private JTextField ManagementStockCodeTF;
	private JTextField ManagementStockNumTF;
	
	private JButton saveButton;
	private JButton addProductButton;
	private JButton deleteProductButton;

	
	Manager act = null;
	ObjectInputStream ois = null;
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					SellerMainFrame frame = new SellerMainFrame();
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

	
	public SellerMainFrame() throws Exception {
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
		
		
		setTitle("판매자 Ui");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 702, 440);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 240, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// 유저(판매자) 환영문구 label
		JLabel sellerwelcome = new JLabel("환영합니다. 판매자님");
		sellerwelcome.setBounds(12, 10, 126, 15);
		contentPane.add(sellerwelcome);
		
		// 오늘의 날짜 label
		JLabel dateLabel = new JLabel("yyyy-MM-dd");
		dateLabel.setForeground(new Color(128, 128, 128));
		dateLabel.setBounds(608, 10, 66, 15);
		contentPane.add(dateLabel);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date getToday = new Date();
		String today = format.format(getToday);
		dateLabel.setText(today);
		
		// MainFrame 이동 버튼 (Home button)
		JButton homeButton = new JButton("Home");
		homeButton.setFont(new Font("Yu Gothic UI", Font.BOLD, 11));
		homeButton.setForeground(Color.GRAY);
		homeButton.setBounds(514, 5, 70, 23);
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
		
		// 저장하기
		// 파일 저장 버튼 (save button)
		JButton saveButton = new JButton("저장");
		saveButton.setForeground(Color.GRAY);
		saveButton.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 10));
		saveButton.setBounds(373, 6, 70, 23);
		contentPane.add(saveButton);
		saveButton.addActionListener(new saveActionListener());
		
		
		
		// 이전 화면 이동 버튼 (back button)
		JButton backButton = new JButton("Back");
		backButton.setForeground(Color.GRAY);
		backButton.setFont(new Font("Yu Gothic UI", Font.BOLD, 11));
		backButton.setBounds(447, 5, 64, 23);
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
		
		
		// 상품 관리 & 고객 관리 탭
		JTabbedPane sellerTab = new JTabbedPane(JTabbedPane.TOP);
		sellerTab.setBounds(0, 38, 686, 353);
		contentPane.add(sellerTab);
		
		// 상품 관리 탭 - 상품 관리 panel
		JPanel productManagementPanel = new JPanel();
		productManagementPanel.setBackground(new Color(255, 255, 255));
		sellerTab.addTab("상품 관리", null, productManagementPanel, null);
		productManagementPanel.setLayout(null);
		
		// 상품 관리 탭 - 물품 등록 label
		JLabel productList = new JLabel("물품 목록");
		productList.setForeground(new Color(39, 39, 39));
		productList.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		productList.setBounds(12, 10, 77, 23);
		productManagementPanel.add(productList);
		
		// 상품 관리 스크롤러
		JScrollPane productTablescrollPane = new JScrollPane();
		productTablescrollPane.setBounds(0, 37, 681, 177);
		productManagementPanel.add(productTablescrollPane);
		
		// 상품 관리 탭 - 물폼 목록 테이블
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
		
		
		
		/**/
		// 상품 관리 탭 - 물품 검색 textField
		productSearchTF = new JTextField();
		productSearchTF.setBounds(508, 17, 100, 15);
		productManagementPanel.add(productSearchTF);
		productSearchTF.setColumns(10);
		
		// 상품 관리 탭 - 물품 검색 button
		JButton productSearch = new JButton("검색");
		productSearch.setForeground(Color.GRAY);
		productSearch.setFont(new Font("나눔고딕", Font.PLAIN, 8));
		productSearch.setBounds(615, 18, 54, 15);
		productManagementPanel.add(productSearch);
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
		
		/**/
		//물품 등록
		// 상품 관리 탭 - 물품 등록 label
		JLabel addProduct = new JLabel("물품 등록");
		addProduct.setForeground(new Color(78, 78, 78));
		addProduct.setFont(new Font("굴림", Font.BOLD, 15));
		addProduct.setBounds(12, 222, 68, 26);
		productManagementPanel.add(addProduct);
		// 상품 관리 탭 - 물품 등록 : 상품 코드
		JLabel addProductCode = new JLabel("상품 코드");
		addProductCode.setForeground(new Color(99, 99, 99));
		addProductCode.setFont(new Font("나눔고딕", Font.BOLD, 14));
		addProductCode.setBounds(92, 223, 58, 23);
		productManagementPanel.add(addProductCode);
		// 상품 관리 탭 - 물품 등록 : 상품 코드 textField
		addProductCodeTF = new JTextField();
		addProductCodeTF.setColumns(10);
		addProductCodeTF.setBounds(152, 225, 66, 21);
		productManagementPanel.add(addProductCodeTF);
		// 상품 관리 탭 - 물품 등록 : 상품명
		JLabel addProductName = new JLabel("상품명");
		addProductName.setForeground(new Color(99, 99, 99));
		addProductName.setFont(new Font("나눔고딕", Font.BOLD, 14));
		addProductName.setBounds(225, 224, 45, 23);
		productManagementPanel.add(addProductName);
		// 상품 관리 탭 - 물품 등록 : 상품명 textField
		addProductNameTF = new JTextField();
		addProductNameTF.setColumns(10);
		addProductNameTF.setBounds(266, 225, 66, 21);
		productManagementPanel.add(addProductNameTF);		
		// 상품 관리 탭 - 물품 등록 : 상품 개수
		JLabel addProductNum = new JLabel("개수");
		addProductNum.setForeground(new Color(99, 99, 99));
		addProductNum.setFont(new Font("나눔고딕", Font.BOLD, 14));
		addProductNum.setBounds(344, 224, 26, 23);
		productManagementPanel.add(addProductNum);
		// 상품 관리 탭 - 물품 등록 : 상품 개수 textField
		addProductNumTF = new JTextField();
		addProductNumTF.setColumns(10);
		addProductNumTF.setBounds(372, 224, 26, 21);
		productManagementPanel.add(addProductNumTF);
		// 상품 관리 탭 - 물품 등록 : 상품 가격
		JLabel addProductPrice = new JLabel("가격");
		addProductPrice.setForeground(new Color(99, 99, 99));
		addProductPrice.setFont(new Font("나눔고딕", Font.BOLD, 14));
		addProductPrice.setBounds(410, 223, 26, 23);
		productManagementPanel.add(addProductPrice);
		// 상품 관리 탭 - 물품 등록 : 상품 가격 textField
		addProductPriceTF = new JTextField();
		addProductPriceTF.setColumns(10);
		addProductPriceTF.setBounds(438, 224, 58, 21);
		productManagementPanel.add(addProductPriceTF);
		// 상품 관리 탭 - 물품 등록 button
		JButton addProductButton = new JButton("등록");
		addProductButton.setForeground(Color.GRAY);
		addProductButton.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 13));
		addProductButton.setBounds(508, 218, 161, 30);
		
		productManagementPanel.add(addProductButton);
		addProductButton.addActionListener(
				new AddActionListener(productTable, addProductCodeTF, addProductNameTF, addProductNumTF, addProductPriceTF));		// 상품 등록 : (추가 버튼) 누르면 상품 추가
		
		addProductPriceTF.addKeyListener(
				new keyAdapter(productTable, addProductCodeTF, addProductNameTF, addProductNumTF, addProductPriceTF));				// 상품 등록 : (추가 버튼) 대신 엔터 누르면 상품 추가
		
		
		
		/**/
		// 상품 관리 탭 - 물품 삭제 label
		JLabel deleteProduct = new JLabel("물품 삭제");
		deleteProduct.setForeground(new Color(78, 78, 78));
		deleteProduct.setFont(new Font("굴림", Font.BOLD, 15));
		deleteProduct.setBounds(12, 250, 70, 29);
		productManagementPanel.add(deleteProduct);
		
		// 상품 관리 탭 - 물품 삭제 : 상품 코드 label
		JLabel deleteProductCode = new JLabel("상품 코드 :");
		deleteProductCode.setForeground(new Color(99, 99, 99));
		deleteProductCode.setFont(new Font("굴림", Font.PLAIN, 12));
		deleteProductCode.setBounds(92, 256, 66, 15);
		productManagementPanel.add(deleteProductCode);
		
		JLabel deleteProductCodeLabel = new JLabel("");
		deleteProductCodeLabel.setForeground(new Color(128, 128, 128));
		deleteProductCodeLabel.setBounds(162, 256, 57, 15);
		productManagementPanel.add(deleteProductCodeLabel);
		
		// 상품 관리 탭 - 물품 삭제 : 상품명 label
		JLabel deleteProductName = new JLabel("상품명 :");
		deleteProductName.setForeground(new Color(99, 99, 99));
		deleteProductName.setFont(new Font("굴림", Font.PLAIN, 12));
		deleteProductName.setBounds(227, 257, 57, 15);
		productManagementPanel.add(deleteProductName);
		
		JLabel deleteProductNameLabel = new JLabel("");
		deleteProductNameLabel.setForeground(new Color(128, 128, 128));
		deleteProductNameLabel.setBounds(276, 256, 57, 15);
		productManagementPanel.add(deleteProductNameLabel);
		
		
		// 상품 관리 탭 - 물품 삭제 여부 질문 체크박스
		JCheckBox askToDelete = new JCheckBox("삭제하시겠습니까?", false);
		askToDelete.setForeground(Color.GRAY);
		askToDelete.setFont(new Font("나눔고딕", Font.BOLD, 12));
		askToDelete.setBounds(363, 254, 121, 23);
		productManagementPanel.add(askToDelete);
		
		// 상품 관리 탭 - 물품 삭제 button
		JButton deleteProductButton = new JButton("삭제");
		deleteProductButton.setForeground(Color.GRAY);
		deleteProductButton.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 13));
		deleteProductButton.setBounds(508, 249, 161, 30);
		productManagementPanel.add(deleteProductButton);
		productTable.addMouseListener(new Mouse(productTable, deleteProductCodeLabel, deleteProductNameLabel));		// 마우스로 테이블의 row 클릭했을 경우 이벤트 발생
		deleteProductButton.addActionListener(new RemoveActionListener(productTable, askToDelete));					// 체크박스 누르고, 삭제 버튼 누를 경우 이벤트 발생
		
		
		
		
		/**/
		// 상품 관리 탭 - 재고 관리 label
		JLabel ManagementStock = new JLabel("재고 관리");
		ManagementStock.setForeground(new Color(78, 78, 78));
		ManagementStock.setFont(new Font("굴림", Font.BOLD, 15));
		ManagementStock.setBounds(12, 284, 70, 26);
		productManagementPanel.add(ManagementStock);
	
		// 상품 관리 탭 - 재고 관리 : 상품 코드
		JLabel ManagementStockCode = new JLabel("상품 코드");
		ManagementStockCode.setForeground(new Color(99, 99, 99));
		ManagementStockCode.setFont(new Font("나눔고딕", Font.BOLD, 14));
		ManagementStockCode.setBounds(92, 285, 58, 23);
		productManagementPanel.add(ManagementStockCode);
		// 상품 관리 탭 - 재고 관리 : 상품 코드 textField
		ManagementStockCodeTF = new JTextField();
		ManagementStockCodeTF.setColumns(10);
		ManagementStockCodeTF.setBounds(152, 287, 94, 21);
		productManagementPanel.add(ManagementStockCodeTF);
		// 상품 관리 탭 - 재고 관리 : 상품 개수 textField
		ManagementStockNumTF = new JTextField();
		ManagementStockNumTF.setColumns(10);
		ManagementStockNumTF.setBounds(266, 287, 36, 21);
		productManagementPanel.add(ManagementStockNumTF);
		// 상품 관리 탭 - 재고 관리 : 상품 개수 (개) label
		JLabel ManagementStockNum = new JLabel("개");
		ManagementStockNum.setForeground(new Color(99, 99, 99));
		ManagementStockNum.setFont(new Font("나눔고딕", Font.BOLD, 14));
		ManagementStockNum.setBounds(306, 285, 26, 23);
		productManagementPanel.add(ManagementStockNum);
		// 상품 관리 탭 - 재고 관리 : 추가 button
		JButton stockAddButton = new JButton("재고 추가");
		stockAddButton.setForeground(Color.GRAY);
		stockAddButton.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 11));
		stockAddButton.setBounds(508, 282, 77, 30);
		productManagementPanel.add(stockAddButton);
		stockAddButton.addActionListener(new AddStockListener(productTable, ManagementStockCodeTF, ManagementStockNumTF));		// 재고 추가 버튼 누르는 경우 이벤트 발생
		
		// 상품 관리 탭 - 재고 관리 : 삭제 button
		JButton stockDeleteButton = new JButton("재고 삭제");
		stockDeleteButton.setForeground(Color.GRAY);
		stockDeleteButton.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 11));
		stockDeleteButton.setBounds(585, 282, 84, 30);
		productManagementPanel.add(stockDeleteButton);
		stockDeleteButton.addActionListener(new SubStockListener(productTable, ManagementStockCodeTF, ManagementStockNumTF));	// 재고 삭제 버튼 누르는 경우 이벤트 발생
		productTable.addMouseListener(new StockMouse(productTable, ManagementStockCodeTF));		// 마우스로 테이블의 row 클릭했을 경우 이벤트 발생
		
		
		
		/**/
		// 고객 관리 탭 - 고객 관리 panel
		JPanel LenderManagementPanel = new JPanel();
		LenderManagementPanel.setBackground(new Color(255, 255, 255));
		sellerTab.addTab("고객 관리", null, LenderManagementPanel, null);
		LenderManagementPanel.setLayout(null);
		// 고객 관리 탭 - 고객 정보 테이블 스크롤러
		JScrollPane lenderInfoTableScrollPane = new JScrollPane();
		lenderInfoTableScrollPane.setBounds(12, 42, 657, 223);
		LenderManagementPanel.add(lenderInfoTableScrollPane);
		// 고객 관리 탭 - 고객 정보 테이블
		lenderInfoTable = new JTable();
		lenderInfoTable.setForeground(new Color(99, 99, 99));
		lenderInfoTable.setFont(new Font("Yu Gothic UI", Font.BOLD, 11));
		
		String colname[] = {"이름", "전화번호", "대여 일자", "반납 예정 일자", "상품 코드", "상품 개수"};
		lenderInfoTable.setModel(new DefaultTableModel(colname, 0));
		DefaultTableModel model2 = (DefaultTableModel) lenderInfoTable.getModel();
		lenderInfoTableScrollPane.setViewportView(lenderInfoTable);
		for(int i = 0; i < act.getUserCount(); i++)
		{
			// user 객체에서 필요한 정보 추출
			String name =act.userAt(i).getName();
			String phone =act.userAt(i).getPhone();
			String rentalDay = act.userAt(i).getRentalDay();
			String returnDay = act.userAt(i).getReturnDay();
			int count = act.userAt(i).getRentalCount();
			for(int k = 0; k < count; k++)
			{
			String arr1[] = new String[6];
			arr1[0] = name;
			arr1[1] = phone;
			arr1[2] = rentalDay;
			arr1[3] = returnDay;

			for(int j = 0; j < act.userAt(i).getRentalCount(); j++)
			{
				String code = act.userAt(i).codeAt(k);
				arr1[4] = code;
			}
			arr1[5] = String.valueOf(1);
			model2.addRow(arr1);
			}
		}
		
		
		
		// 고객 관리 탭 - 대여 정보 확인 label
		JLabel lenderInfoList = new JLabel("대여 정보 확인");
		lenderInfoList.setForeground(new Color(39, 39, 39));
		lenderInfoList.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		lenderInfoList.setBounds(12, 10, 108, 23);
		LenderManagementPanel.add(lenderInfoList);
		// 고객 관리 탭 - 매출 확인 button
		JButton revenueButton = new JButton("매출 확인");
		revenueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CheckSales revenue;
				try {
					revenue = new CheckSales();
					revenue.setVisible(true);
					dispose();
					
				} catch (Exception e1) {
					showMessageDialog(null,"화면 전환이 불가합니다.");
				}
			}
		});
		revenueButton.setBounds(530, 279, 139, 35);
		LenderManagementPanel.add(revenueButton);
		revenueButton.setForeground(new Color(74, 74, 74));
		revenueButton.setFont(new Font("굴림", Font.PLAIN, 12));
		
		
		
		
		// 새로고침 (파일 정보 재로딩)
		JButton refreshButton = new JButton("전체보기");
		/*refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {}});*/
		refreshButton.setForeground(Color.GRAY);
		refreshButton.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 10));
		refreshButton.setBounds(298, 6, 70, 23);
		contentPane.add(refreshButton);
		refreshButton.addActionListener(
				new FileReadActionListener());
	}
	
	
	
	

	// (물품 삭제) 마우스 이벤트 - 테이블 클릭시 상품 코드와 상품명 보여주기 
	class Mouse implements MouseListener{
		JTable table;
		JLabel label1, label2;
		
		Mouse(JTable table, JLabel label1, JLabel label2) {		// 상품 테이블, 삭제할 상품 코드, 삭제할 상품명
			this.table = table;
			this.label1 = label1;
			this.label2 = label2;
		}
		
		public void mouseClicked(MouseEvent e) {
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			int row = table.getSelectedRow();	
				
			Object delCode = table.getValueAt(row, 0);
			Object delName = table.getValueAt(row, 1);
			String deleteCode = delCode.toString();
				
				
			label1.setText((String) delCode);					// 삭제할 상품 코드 띄워주기
			label2.setText((String) delName);					// 삭제할 상품명 띄워주기
		}
		
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseDragged(MouseEvent e) {}
		public void mouseMoved(MouseEvent e) {}
	}
	
	// 물품 추가 버튼 대신 엔터 눌렀을 경우 발생하는 키 리스너 클래스 + 추가된 물품 전체 보기
	class keyAdapter implements KeyListener{
		JTable table;
		JTextField text1, text2, text3, text4;
		keyAdapter(JTable table, JTextField text1, JTextField text2, JTextField text3, JTextField text4) {
			this.table = table;
			this.text1 = text1;
			this.text2 = text2;
			this.text3 = text3;
			this.text4 = text4;
		}
		
		public void keyPressed(KeyEvent e) {
			if (e.getKeyChar() == '\n') {
				String arr[] = new String[4];
				arr[0] = text1.getText();
				arr[1] = text2.getText();
				arr[2] = text3.getText();
				arr[3] = text4.getText();
				DefaultTableModel model = (DefaultTableModel) productTable.getModel();
				String addCode = arr[0];
				String addName = arr[1];
				int addCount = Integer.parseInt(arr[2]);
				int addPrice = Integer.parseInt(arr[3]);
				Product p1 = new Product(addName, addCode, addCount, addPrice);
				try {
					act.add(p1);
					model.addRow(arr);
					showMessageDialog(null,"상품 등록을 성공하였습니다.");						// 물품 등록, 저장을 성공하였을 때 알림창	

				}catch(NumberFormatException ne) {
					showMessageDialog(null,"추가할 상품 정보를 제대로 입력해주세요.");
				}
				catch (Exception e1) {
					showMessageDialog(null,"상품 추가 과정에서 문제가 발생하였습니다.");
				}
				text1.setText("");
				text2.setText("");
				text3.setText("");
				text4.setText("");
			}
		}
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}
	}
	
	
	
	// 물품 추가 버튼을 처리하는 리스너 클래스 + 추가된 물품 전체 보기
	class AddActionListener implements ActionListener{
		JTable table;
		JTextField text1, text2, text3, text4;
		AddActionListener(JTable table, JTextField text1, JTextField text2, JTextField text3, JTextField text4) {
			this.table = table;
			this.text1 = text1;
			this.text2 = text2;
			this.text3 = text3;
			this.text4 = text4;
		}

		public void actionPerformed(ActionEvent e) {
			String arr[] = new String[4];
			arr[0] = text1.getText();
			arr[1] = text2.getText();
			arr[2] = text3.getText();
			arr[3] = text4.getText();
			DefaultTableModel model = (DefaultTableModel) productTable.getModel();
			String addCode = arr[0];
			String addName = arr[1];
			int addCount = Integer.parseInt(arr[2]);
			int addPrice = Integer.parseInt(arr[3]);
			Product p1 = new Product(addName, addCode, addCount, addPrice);
			try {
				act.add(p1);
				model.addRow(arr);
				showMessageDialog(null,"상품 등록을 성공하였습니다.");						// 물품 등록, 저장을 성공하였을 때 알림창	

			}catch(NumberFormatException ne) {
				showMessageDialog(null,"추가할 상품 정보를 제대로 입력해주세요.");
			}
			/*catch(CodeExistsExcepion cex) {
				showMessageDialog(null,"동일한 코드를 가진 상품이 존재합니다.");			// 동일한 상품 코드를 가진 물품 있는 경우 익셉션 발생 경고창
			}
			*/catch (Exception e1) {
				showMessageDialog(null,"상품 추가 과정에서 문제가 발생하였습니다.");
			}
			text1.setText("");
			text2.setText("");
			text3.setText("");
			text4.setText("");
		}
	}


	
	// 재고 관리 (재고 추가) 리스너 클래스
	// 상품 코드를 입력 받고 -> 해당 상품을 n 개만큼 재고 추가
	class AddStockListener implements ActionListener {
		JTable table;												// 상품 테이블
		JTextField text1, text2;									// 재고를 추가할 상품코드, 추가할 상품의 개수
		AddStockListener(JTable table, JTextField text1, JTextField text2) {		
			this.table = table;
			this.text1 = text1;
			this.text2 = text2;
		}
		public void actionPerformed(ActionEvent e) {
			try {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				String code = text1.getText();
				int num = Integer.parseInt(text2.getText());

				int index = act.search(code);
				act.addStock(index, num);
				
				int row = act.search(code);
				Object n = table.getValueAt(row, 2);
				int number = Integer.parseInt(n.toString());
				table.setValueAt(number+num, index, 2);
				
				showMessageDialog(null, code + " 상품의 재고를 성공적으로 추가하였습니다.");		// 추가적인 물품 재고 등록을 성공하였을 때 알림창
				text1.setText("");
				text2.setText("");
			}
			catch(Exception ex) {
				showMessageDialog(null,"존재하지 않는 상품 코드입니다.");						// 존재하지 않는 상품 코드를 입력하는 경우 경고창
			}
		}
	}
	
		
	// 재고 관리 (재고 삭제) 리스너 클래스
	// 상품 코드를 입력 받고 -> 해당 상품을 n 개만큼 재고 삭제
	class SubStockListener implements ActionListener {
		JTable table;													// 상품 테이블
		JTextField text1, text2;										// 재고를 삭제할 상품코드, 제거할 상품의 개수
		SubStockListener(JTable table, JTextField text1, JTextField text2) {		
			this.table = table;
			this.text1 = text1;
			this.text2 = text2;
		}
			
		public void actionPerformed(ActionEvent e) {
			try {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				String code = text1.getText();
				int num = Integer.parseInt(text2.getText());
				int index = act.search(code);
				act.subStock(index, num);								// productList 내의 해당 인덱스를 가진 상품 재고 n개만큼 삭제
					
				int row = act.search(code);
				Object n = table.getValueAt(row, 2);
				int number = Integer.parseInt(n.toString());
				table.setValueAt(number-num, index, 2);
				showMessageDialog(null, code + " 상품의 재고를 " + num + " 개 삭제하였습니다.");	// 추가적인 물품 재고 등록을 성공하였을 때 알림창
			}
			catch(Exception ex) {
				showMessageDialog(null,"존재하지 않는 상품 코드입니다.");							// 해당 상품 코드를 가진 물품이 없는 경우 익셉션 발생 경고창
			}
		}
	}

	
	// (재고 관리) 마우스 이벤트 - 테이블 클릭시 상품 코드와 상품명 보여주기 
	class StockMouse implements MouseListener{
			JTable table;
			JTextField label1;
			
			StockMouse(JTable table, JTextField label1) {		// 상품 테이블, 상품 코드
				this.table = table;
				this.label1 = label1;
			}

			public void mousePressed(MouseEvent e1) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int row = table.getSelectedRow();	
					
				Object code = table.getValueAt(row, 0);
				String pCode = code.toString();	
				label1.setText((String) pCode);					// 재고 추가 or 삭제할 상품 코드 띄워주기
			}
			public void mouseEntered(MouseEvent e1) {}
			public void mouseExited(MouseEvent e1) {}
			public void mouseReleased(MouseEvent e1) {}
			public void mouseDragged(MouseEvent e1) {}
			public void mouseMoved(MouseEvent e1) {}
			public void mouseClicked(MouseEvent e1) {}
		}
	
	
	
	// 물품 저장 버튼을 처리하는 리스너 클래스
	class saveActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ObjectOutputStream oos = null;
			try {
				// 파일 저장
				oos =new ObjectOutputStream(new FileOutputStream("out.txt"));	 // 객체 생성
				act.saveToFile(oos);											// manager객체 내의 saveToFile 함수 호출
				showMessageDialog(null,"물품 저장을 성공하였습니다.");
				oos.close();
				
			}catch (FileNotFoundException fnfe) {
				JOptionPane fnfes =new JOptionPane();
				fnfes.showMessageDialog(null, "파일을 찾을 수 없습니다.");			// 저장할 파일을 찾지 못한 경우 경고창 발생
			}catch (Exception ex) {
				showMessageDialog(null,"파일을 저장하지 못 했습니다.");				// 파일을 저장하지 못한 경우 익셉션 경고창 발생
			} 
		}
	}
	
	
	
	// 저장된 물품 목록(파일) 보여주기
	// [전체보기] 버튼을 누르면 파일에 저장된 상품 목록 보여줌
	// 전체 보기 버튼을 통해 파일에 저장된 목록 보여주기 이벤트
	class FileReadActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				// 파일을 읽는 객체 생성
				ois = new ObjectInputStream(new FileInputStream("out.txt"));
				// 매니저 생성자에 ois 객체를 넘겨 파일 복원
				act = new Manager(100, 100, ois);
				DefaultTableModel model = (DefaultTableModel) productTable.getModel();
				model.setRowCount(0);
				for(int i = 0; i < act.getProductCount(); i++)
				{
					// product 객체에서 필요한 정보 추출
					String name = act.productAt(i).getName();
					String code = act.productAt(i).getCode();
					int number = act.productAt(i).getNumber();
					int price = act.productAt(i).getPrice();
					
					// 상품 정보 테이블을 통해 보여주기
					String productArr[] = new String[4];
					productArr[0] = code;
					productArr[1] = name;
					productArr[2] = String.valueOf(number);
					productArr[3] = String.valueOf(price);
					model.addRow(productArr);
				}
				
				
				showMessageDialog(null,"전체 불러오기를 성공하였습니다.");		// 새로고침을 성공하였을 때 알림창
				
				// 파일 닫기
				ois.close();
			} catch (FileNotFoundException fnfe) {
				showMessageDialog(null,"파일을 찾을 수 없습니다.");			// 파일이 없는 경우 - 익셉션 발생하는 경우 경고창
			} catch (Exception ex) {
				showMessageDialog(null,"저장된 파일을 읽어들이지 못했습니다.");	// 파일을 읽어들이지 못하는 경우 - 익셉션  발생 경고창
			} 
		}
	}
	
	
	
	// 물품 삭제 버튼을 처리하는 리스너 클래스 - 체크박스 클릭하면 선택된 row의 상품을 삭제
	class RemoveActionListener implements ActionListener {
		JTable table;
		JCheckBox check;
		RemoveActionListener(JTable table, JCheckBox check) {		// 상품 테이블, 체크박스
			this.table = table;
			this.check = check;
		}
		public void actionPerformed(ActionEvent e) {
			try {
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					int row = table.getSelectedRow();	
				
					Object delCode = table.getValueAt(row, 0);
					Object delName = table.getValueAt(row, 1);
					String deleteCode = delCode.toString();
				
					if(check.isSelected())							// 체크박스가 체크되는 경우 물품 삭제, 테이블에서 제거되어 보여주기
					{
						act.delete(deleteCode);						// productList 상품 삭제
						model.removeRow(row);						// 테이블 내에서 해당 row 제거
					}
			} catch (Exception e1) {
				// 삭제되지 않는 경우 익셉션 발생
				showMessageDialog(null,"물품을 성공적으로 삭제하지 못했습니다.");
			}
		}
	}
}

