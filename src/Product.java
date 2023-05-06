import java.io.*;

public class Product implements java.io.Serializable{
	private String productName; // 상품 이름
	private String productCode; // 상품 코드
	private int productNumber; // 상품 개수
	private int price; // 상품 가격
	
	// 인수 있는 생성자
	Product(String productName, String productCode, int productNumber, int price)
	{
		this.productName = productName;
		this.productCode = productCode;
		this.productNumber = productNumber;
		this.price = price;
	}
	
	
	
	// equals를 재정의(API > java.lang->object->equals 참고)
	//productList의 코드와 User 객체의 code 비교하기 위한 equals 함수 재정의
	public boolean equals(Product p1) {
		if(!(p1 instanceof Product))				// User 객체가 아닌 경우 비교 불가
			return false;
		if(this.productCode.equals(p1.getCode())) {// 객체의 code와 productList 내의 code가 같은지 비교
			return true;
		}else return false;
	}
		
	
	// 상품 이름 반환
	public String getName()
	{
		return productName;
	}
	
	// 상품 코드 반환
	public String getCode()
	{
		return productCode;
	}
	
	// 상품 개수 반환
	public int getNumber()
	{
		return productNumber;
	}
	
	// 상품 개수 추가
	public void addNumber()
	{
		productNumber++;
	}
	
	// 상품 개수 n개만큼 추가
	public void addNumbers(int n)
	{
		productNumber += n;
	}
		
	
	// 상품 대여 가능한지 확인 후 상품 개수 1개 삭제
	public void subNumber() throws Exception{
		if(productNumber < 1) // 재고 개수가 1보다 작을 경우 
			throw new Exception("잘못된 방법의 체크인입니다."); // 익셉션 발생
		else // 재고 물건 숫자가 1이상일 경우
			productNumber--; // 재고 수 1개 감소
	}
	

	// 상품 대여 가능한지 확인 후 상품 개수 n개 삭제
	public void subNumbers(int n) throws Exception{
		if(productNumber < 1) // 재고 개수가 1보다 작을 경우 
			throw new Exception("잘못된 방법의 체크인입니다."); // 익셉션 발생
		else if(n > productNumber)	// 재고보다 많이 대여하려 하는 경우
			throw new Exception("대여할 재고가 부족합니다."); // 익셉션 발생
		else // 재고 물건 숫자가 1이상일 경우
			productNumber -= n; // 재고 수 n개 감소
	}
	
	// 상품 가격 반환
	public int getPrice()
	{
		return price;
	}
	
	// 재고 검색 함수 (재고가 있으면 true, 아니면 false 반환)
	public boolean isEmpty()
	{
		if(productNumber > 0)
			return true;
		else
			return false;
	}
	
}
