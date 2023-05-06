import java.io.*;
import java.util.*;


public class Manager {
	private ArrayList<Product> productList = null;
	private ArrayList<User> userList = null;
	private int revenue = 0; // 일일 매출 총액 변수
	

	ObjectInputStream ois = null;
	ObjectOutputStream oos = null;
	
	
	// 생성자 (상품 배열, 대여 배열 크기 설정)
	Manager (int maxProductCount, int maxUserCount) throws Exception{
		productList = new ArrayList<Product>(maxProductCount); 	// 상품 배열 크기 설정
		userList = new ArrayList<User>(maxUserCount); 				// 대여 배열 크기 설정	
	}
	
	
	// read를 위한 생성자(상품 배열, 대여 배열 크기 설정, ObjectInputStream ois)
	// read from file 함수 기능
	// 상품 배열 카운트 인덱스, 상품 정보, 유저 배열 카운트 인덱스, 유저 정보, 매출액 순으로 읽어들임
	Manager(int maxProductCount, int maxUserCount, ObjectInputStream ois) throws Exception{
		productList = new ArrayList<Product>(maxProductCount); // 상품 배열 크기 설정
		userList = new ArrayList<User>(maxUserCount); 	// 대여 배열 크기 설정
		try {
			// 상품 배열 인덱스 카운트
			int count = ois.readInt();					// 저장된 상품 개수 읽기				
			// 1. 상품 정보 불러오기(상품 배열 채우기)
			for(int i=0;i<count;i++)	{	
				Product p1 = (Product) ois.readObject();// 상품 추가		
				productList.add(p1);
			}		
			
			// 2. 고객 정보 불러오기 위한 대여 배열 카운트
			int userCount = ois.readInt();				// 저장된 유저 인원수 읽기
			// 고객 정보 저장
			for(int i=0;i<userCount;i++) {
				User u1 = (User) ois.readObject();
				userList.add(u1);						// 유저 추가
			}
				
			// 3. 일일 매출 총액 읽기
			int revenue = ois.readInt();				// 일일 매출 총액 읽기
			setRevenue(revenue);
			
		}
		catch(EOFException eofe) {}						//파일을 다읽은 경우
		catch(IOException e){
			throw new Exception("파일을 읽어오는 과정에서 오류가 발생했습니다.");
		}
		finally {
			try {
				ois.close();
			}
			catch(Exception e) {
				throw new Exception("오류가 발생했습니다.");
			}
		}
	}
	
	
	
	
	
	// 파일 저장 함수
	// 상품 배열 인덱스 카운트, 대여 배열, 상품 배열, 매출액 순으로 저장
	void saveToFile(ObjectOutputStream oos) throws Exception {
		try {
			// 상품 배열 인덱스 카운트
			//oos.writeInt(Integer(productList.size()));
			oos.writeInt(productList.size());			
			// 상품 정보 저장
			for(int i=0;i<productList.size();i++)		
			{
				oos.writeObject(productList.get(i));
			}
			
			// 고객 정보 저장을 위한 대여 배열 카운트
			oos.writeInt(userList.size());
			
			// 고객 정보 저장
			for(int i=0;i<userList.size();i++)
			{
				oos.writeObject(userList.get(i));
			}
		
			// 매출액 저장
			oos.writeInt(getRevenue());
		}
		catch(Exception e) {
			throw new Exception("파일을 작성하는 동안 오류가 발생했습니다.");
		}
	}
	
	
	

	// 상품 배열에 원소 추가
	public void addProduct(Product p) throws Exception {
		try {
			productList.add(p);
		}
		catch(IndexOutOfBoundsException iobe) {	// 범위를 넘어설 경우 익셉션 발생
			throw new Exception("잘못된 상품 등록입니다.");
		}
	}
	
	// 상품 추가
	public void add(Product p) throws Exception {
		try {
		//if (!(productList.contains(p))) // 코드 중복 검색
			checkCode(p); // 코드 중복 검색
			addProduct(p); 	// 상품 추가
		}
		catch(Exception e) {
			throw new Exception ("잘못된 상품 등록입니다.");}
	}
	
	
	// 상품 배열에 number번째 인덱스 원소 삭제
	public void subProduct(int number) throws Exception{
		try {
			productList.remove(number);
		}
		catch(IndexOutOfBoundsException e) {		// 인덱스의 범위가 벗어나는 경우 익셉션 발생
			throw new Exception ();
		}
	}
	
	
	// 상품 삭제
	public void delete(String productCode) throws Exception {
		try{
			int number = search(productCode); 	// 상품 배열에서 검색하기
			productList.remove(number);			// 상품 배열에서 삭제하기
		}
		catch (Exception e) {
			throw new Exception ("존재하지 않는 상품입니다.");
		}
	}
	
	// 코드 중복 검색
	public void checkCode(Product p) throws Exception {
		for (int i = 0; i < productList.size(); i++)
		{
			Product p1 = productList.get(i);
			// 중복된 키 검색
			if(p1 != null && p1.getCode().equals(p.getCode()))
			{
				throw new Exception ("잘못된 상품 등록입니다.");
			}
		}
	}


	// 상품 객체 검색
	public int search(String productCode) throws Exception {
		// 상품 검색
				for(int i = 0; i < productList.size(); i++)
				{
					Product p1 = productList.get(i); // 객체 생성
					// 원소 코드와 인수 일치
					if((productCode.equals(p1.getCode())))return i;
				} throw new Exception("일치하는 코드를 찾을 수 없습니다."); // 코드가 일치하지 않으면 익셉션 발생
	}

	
	// 상품 배열 i번째 리턴
	public Product productAt(int i) throws Exception{
		try {
			return productList.get(i); // 상품 배열 i번째 상품 객체 return
		}
		catch(IndexOutOfBoundsException e) {
			throw new Exception("상품을 찾을 수 없습니다.");
		}
	}
	
	
	// productCount 값 반환
	public int getProductCount() {
		return productList.size();
	}
	
	
	// 전화번호 중복 검색
	public void checkPhone(User u) throws Exception {
		for (int i = 0; i < userList.size(); i++)
		{
			User u1 = userList.get(i);
			// 중복된 전화번호 검색
			if(u1 != null && u1.equals(u))
			{
				throw new Exception ("잘못된 방법의 체크인입니다."); // 중복된 전화번호일 경우 익셉션 발생
			}
		}
	}
	
	
	// 재고 개수에서 대여 개수 제외
	public void subStock(User u) throws Exception {
		// 재고 개수에서 대여 개수 제외하기
		for(int i = 0; i < u.getRentalCount(); i++)
		{
			String code = u.codeAt(i); // 해당 User 객체의 i번째 대여 물품 코드
			int searchNum;
			try {
				searchNum = search(code); // productList에서 해당 코드의 인덱스 번호 검색
			} 
			catch (Exception e) {
				throw new Exception("잘못된 방법의 체크인입니다.");
			}
			Product p = productList.get(searchNum);	//해당 인덱스의 product 객체
			p.subNumber(); // 대여가 가능한지 확인 후 빌리기 (재고 1개 삭제)
		}
	}
	
	// 재고 개수에서 대여 개수 제외
	// 대여 개수(제거 개수) : n, 재고가 없으면 익셉션 발생시키기
	// User인덱스가 아닌 Product인덱스 - 인덱스 오프. 서치한 인덱스
	// GUI에서 사용하는 함수로 새롭게 생성
	public void subStock(int index, int n) throws Exception {
		// 재고 개수에서 대여 개수 제외하기
			Product p = productList.get(index);	// 해당 인덱스의 product 객체
			p.subNumbers(n); // 대여가 가능한지 확인 후 빌리기 (재고 n개 삭제)
	}
	
	// 대여 배열에 원소 추가
	public void addUser(User u) {
		userList.add(userList.size(), u);

	}
	
	// 체크인	- Ui에서 사용
	public void checkIn(User u) throws Exception {
		try {
			if (!(userList.contains(u))) // 전화번호 중복 검색
			{
				subStock(u); // 재고 개수에서 대여 개수 제외
				addUser(u); // 대여 배열에 대여 정보 넣기
			}
		}
		catch(Exception e) {
			throw new Exception("잘못된 방법의 체크인입니다.");
		}
	}
	
	
	// userCount 값 반환 -Ui에서 사용
	public int getUserCount() {
		return userList.size();
	}
	
	// 대여 배열 i번째 리턴
	public User userAt(int i) throws Exception
	{
		try {
			return userList.get(i); // 대여 배열 i번째 유저 객체 return
		}
		catch(IndexOutOfBoundsException e) {
			throw new Exception("해당 고객 정보를 찾을 수 없습니다.");
		}
	}
	
	// 일치하는 회원번호 검색
	public int searchUser(String phone) throws Exception {
		for(int i = 0; i < userList.size(); i++)
		{
			User u = userList.get(i);	// 일치하는 정보가 있으면 인덱스 번호 반환
			if (u.getPhone().equals(phone))
				return i;
		}throw new Exception ("회원정보가 없습니다."); // 일치하는 정보가 없으면 익셉션 발생
	}
	
	
	
	
	// 상품 재고 다시 추가
	public void addStock(int index) throws Exception {
		User u = userAt(index);
		for(int i = 0; i < u.getRentalCount(); i++) {
			String code = u.codeAt(i); // 해당 User 객체의 i번째 대여 물품 코드
			try {
				int number = search(code); // productList에서 해당 코드의 인덱스 번호 검색
				productAt(number).addNumber(); //해당 인덱스의 product 객체의 재고 추가하기
			}
			catch (Exception e) {
				throw new Exception ("잘못된 방법의 체크아웃입니다.");
			}
		}
	}
	
	// 상품 재고 다시 추가
	// 상품 재고 개수(추가 개수) : n, 재고가 없으면 익셉션 발생시키기
		public void addStock(int index, int n) throws Exception {
			try {
				productAt(index).addNumbers(n); //해당 인덱스의 product 객체의 n개의 재고 추가하기
				//int price = 
			}
			catch (Exception e) {
				throw new Exception ("재고가 존재하지 않습니다.");	
			}
		}
	
	
	// 체크아웃
	public void checkOut(int index) throws Exception{
		try{
			addStock(index);// 상품 재고 다시 추가하기
			int money = userList.get(index).pay();	// 금액 반환받기
			//subUser(index); 
			userList.remove(index);					// userList에서 삭제, 배열 정리하기
			revenue += money; // 매출에 추가하기
		}
		catch(Exception e) {
			throw new Exception ("잘못된 방법의 체크아웃입니다.");
		}
	}
	
	// 매출 반환
	public int getRevenue() {
		return revenue;
	}
	
	// 매출값 할당
	public void setRevenue(int revenue) {
		this.revenue = revenue;
	}
	
}