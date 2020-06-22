package OO2Lab1;

public class Test {

	public static void main(String[] args) {
		Number n1 = new Number("381", 61, "2280347", false);
		Number n2 = new Number("381", 34, "205906", true);
		Number n3 = new Number("123", 34, "1312420", false);

		Service service1 = new Call(n1, n2, 100);
		Service service2 = new Call(n2, n3, 121);

		System.out.println(service1);
		System.out.println(service1.Cost());
		System.out.println(service2);
		System.out.println(service2.Cost());
	}

}
