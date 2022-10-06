package aws.services;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminCreateUserRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminCreateUserResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminGetUserRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminGetUserResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminInitiateAuthRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminInitiateAuthResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminSetUserPasswordRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminSetUserPasswordResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminUpdateUserAttributesRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthFlowType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthenticationResultType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.ChallengeNameType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CreateUserPoolClientRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CreateUserPoolClientResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CreateUserPoolRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CreateUserPoolResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.ForgotPasswordRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.NotAuthorizedException;
import software.amazon.awssdk.services.cognitoidentityprovider.model.PasswordPolicyType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.UserPoolPolicyType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.UserPoolType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.UsernameAttributeType;

public class AwsCognito {
    private int TEMPORARY_PASSWORD_VALIDITY_DAYS = 365;
    private int MINIMUM_LENGTH = 8;
    private String USER_POOL_ID = "ap-southeast-1_javHiZ00B";
    private String CLIENT_ID = "6qb9aop93qg12gmgmdv0d2mm34";
    private static final String EMAIL_VERIFIED = "email_verified";
    private CognitoIdentityProviderClient cognitoIdentityProviderClient;
    private static final String EMAIL = "email";
    private static final String TEMPORARY_PASSWORD = "custom:temporary_password";
    private static final String PASSWORD_EXRIRED = "custom:password_expired";
    private static final String TIME_EXRIRED = "custom:time_expired";

    public AwsCognito() {
        this.cognitoIdentityProviderClient = getCognitoIdentityProviderClient();
    }

    public DefaultCredentialsProvider getCredentials() {
        return DefaultCredentialsProvider.builder().build();
    }

    public CognitoIdentityProviderClient getCognitoIdentityProviderClient() {
        return CognitoIdentityProviderClient.builder().credentialsProvider(getCredentials()).build();
    }

    public UserPoolType createUserPool(String poolName) throws CognitoIdentityProviderException {
        // attribute
        List<UsernameAttributeType> usernameAttributes = new ArrayList<>();
        usernameAttributes.add(UsernameAttributeType.EMAIL);

        // password
        PasswordPolicyType passwordPolicyType = PasswordPolicyType.builder()
                .temporaryPasswordValidityDays(TEMPORARY_PASSWORD_VALIDITY_DAYS).minimumLength(MINIMUM_LENGTH).build();

        UserPoolPolicyType policyType = UserPoolPolicyType.builder().passwordPolicy(passwordPolicyType).build();

//        // lambda
//        LambdaConfigType lambdaConfig = LambdaConfigType.builder().customMessage("CustomMessage_ForgotPassword")
//                .build();

        CreateUserPoolRequest createUserPoolRequest = CreateUserPoolRequest.builder().poolName(poolName)
                .usernameAttributes(usernameAttributes).policies(policyType).build();

        CreateUserPoolResponse poolResponse = cognitoIdentityProviderClient.createUserPool(createUserPoolRequest);
        return poolResponse.userPool();
    }

    public AdminCreateUserResponse createAccount(String email, String password) {
        List<AttributeType> userAttributes = new ArrayList<>();
        userAttributes.add(AttributeType.builder().name(EMAIL).value(email).build());
        userAttributes.add(AttributeType.builder().name(EMAIL_VERIFIED).value("true").build());

        AdminCreateUserRequest cognitoRequest = AdminCreateUserRequest.builder().userPoolId(USER_POOL_ID)
                .username(email).userAttributes(userAttributes).temporaryPassword(password).build();

        AdminCreateUserResponse adminCreateUserResponse = cognitoIdentityProviderClient.adminCreateUser(cognitoRequest);

        List<AttributeType> userAttributesCreated = adminCreateUserResponse.user().attributes();
        for (AttributeType attributeType : userAttributesCreated) {
            if (attributeType.name().equals("sub")) {
                System.out.println(attributeType.value());
            }
        }
        return adminCreateUserResponse;
    }

    public boolean login(String email, String password) {
        try {
            HashMap<String, String> authParams = new HashMap<>();
            authParams.put("USERNAME", email);
            authParams.put("PASSWORD", password);
            AdminInitiateAuthRequest authRequest = AdminInitiateAuthRequest.builder()
                    .authFlow(AuthFlowType.ADMIN_NO_SRP_AUTH).userPoolId(USER_POOL_ID).clientId(CLIENT_ID)
                    .authParameters(authParams).build();

            AdminInitiateAuthResponse authResult = cognitoIdentityProviderClient.adminInitiateAuth(authRequest);
            AuthenticationResultType authenticationResultType = authResult.authenticationResult();
            System.out.println(authenticationResultType.accessToken());
        } catch (NotAuthorizedException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String createUserPoolClient(String clientName, String userPoolId) {
        CreateUserPoolClientRequest createUserPoolClientRequest = CreateUserPoolClientRequest.builder()
                .userPoolId(userPoolId).clientName(clientName).build();

        CreateUserPoolClientResponse userPoolClient = cognitoIdentityProviderClient
                .createUserPoolClient(createUserPoolClientRequest);

        return (userPoolClient != null && userPoolClient.userPoolClient() != null)
                ? userPoolClient.userPoolClient().clientId()
                : null;
    }

    public boolean changePassword(String email, String oldPassword, String newPassword) {
        HashMap<String, String> authParams = new HashMap<>();
        authParams.put("USERNAME", email);
        authParams.put("PASSWORD", oldPassword);
        AdminInitiateAuthRequest authRequest = AdminInitiateAuthRequest.builder()
                .authFlow(AuthFlowType.ADMIN_NO_SRP_AUTH).userPoolId(USER_POOL_ID).clientId(CLIENT_ID)
                .authParameters(authParams).build();

        AdminInitiateAuthResponse authResult = cognitoIdentityProviderClient.adminInitiateAuth(authRequest);

        if (authResult.challengeName().equals(ChallengeNameType.NEW_PASSWORD_REQUIRED)) {
            AdminSetUserPasswordRequest adminSetUserPasswordRequest = AdminSetUserPasswordRequest.builder()
                    .password(newPassword).username(email).userPoolId(USER_POOL_ID).permanent(true).build();

            AdminSetUserPasswordResponse adminSetUserPasswordResponse = cognitoIdentityProviderClient
                    .adminSetUserPassword(adminSetUserPasswordRequest);
            System.out.println(adminSetUserPasswordResponse);
            return true;
        }
        return false;
    }

    public boolean forgotPassword(String email) {
        AdminGetUserRequest adminGetUserRequest = AdminGetUserRequest.builder().username(email).userPoolId(USER_POOL_ID)
                .build();
        AdminGetUserResponse userResponse = cognitoIdentityProviderClient.adminGetUser(adminGetUserRequest);
        if (userResponse != null && userResponse.enabled()) {
            List<AttributeType> userAttributes = new ArrayList<>();
            userAttributes
                    .add(AttributeType.builder().name(TEMPORARY_PASSWORD).value(UUID.randomUUID().toString()).build());
            Date date = new Date();
            userAttributes.add(AttributeType.builder().name(TIME_EXRIRED).value(date.getTime() + "").build());

            AdminUpdateUserAttributesRequest updateRequest = AdminUpdateUserAttributesRequest.builder().username(email)
                    .userPoolId(USER_POOL_ID).userAttributes(userAttributes).build();

            cognitoIdentityProviderClient.adminUpdateUserAttributes(updateRequest);
        }
        return true;
    }

    public boolean confirmChangePassword(String email, String resetCode, String newPassword) throws Exception {
        AdminGetUserRequest adminGetUserRequest = AdminGetUserRequest.builder().username(email).userPoolId(USER_POOL_ID)
                .build();

        AdminGetUserResponse userResponse = cognitoIdentityProviderClient.adminGetUser(adminGetUserRequest);
        if (userResponse != null && userResponse.enabled()) {
            List<AttributeType> attributeTypes = userResponse.userAttributes();
            AttributeType passwordExrired = attributeTypes.stream().filter(atr -> TIME_EXRIRED.equals(atr.name()))
                    .findAny().orElse(null);

            AttributeType passwordTemporary = attributeTypes.stream().filter(atr -> TEMPORARY_PASSWORD.equals(atr.name()))
                    .findAny().orElse(null);

            if (passwordTemporary.value().equals(resetCode)) {
                Date date = new Date();
                long now = date.getTime();
                long timeExpired = Long.parseLong(passwordExrired.value()) + 1 * 60 * 1000;
                if (timeExpired < now) {
                    AdminSetUserPasswordRequest adminSetUserPasswordRequest = AdminSetUserPasswordRequest.builder()
                            .password(newPassword).permanent(true).username(email).userPoolId(USER_POOL_ID).build();

                    cognitoIdentityProviderClient.adminSetUserPassword(adminSetUserPasswordRequest);
                } else {
                    throw new Exception("Wrong code");
                }
            } else {
                throw new Exception("Wrong code");
            }
        }
        return true;
    }
}
