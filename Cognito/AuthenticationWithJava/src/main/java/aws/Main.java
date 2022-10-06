package aws;

import aws.services.AwsCognito;
import software.amazon.awssdk.services.cognitoidentityprovider.model.UserPoolType;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		String clientName = "App01";
		AwsCognito awsCognito = new AwsCognito();
//		UserPoolType userPoolType = awsCognito.createUserPool("Pool01");
//		awsCognito.createUserPoolClient(clientName, userPoolType.id());

		// email: nguyenminhcuong2412@gmail
		// password: H322732h322732
//		awsCognito.createAccount("cuongnm24121996@gmail.com", "H322732h322732");
		
//		
//		for (int i = 0; i < 20; i++) {
//			awsCognito.login("cuongnm24121996@gmail.com", "CuongNM1234");
//			Thread.sleep(15 * 1000);
//		}

		awsCognito.login("cuongnm24121996@gmail.com", "CuongNM1234");

		// CuongNM123
//		System.out.println(awsCognito.changePassword("cuongnm24121996@gmail.com", "H322732h322732", "CuongNM123"));;
//		awsCognito.forgotPassword("cuongnm24121996@gmail.com");
//		try {
//			awsCognito.confirmChangePassword("cuongnm24121996@gmail.com", "24838a0f-7b79-4b1f-8ca5-033295c9b20b", "CuongNM1234");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}
