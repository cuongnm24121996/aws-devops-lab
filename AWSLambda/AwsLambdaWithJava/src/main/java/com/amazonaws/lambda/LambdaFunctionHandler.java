package com.amazonaws.lambda;

import com.amazonaws.lambda.dto.CognitoCustomMessageEvent;
import com.amazonaws.lambda.dto.Response;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class LambdaFunctionHandler {
	public Object handleRequest(CognitoCustomMessageEvent event, Context context) {
		LambdaLogger logger = context.getLogger();
		logger.log("REQUEST: " + event.toString());

		Response response = new Response();
		if (event.getTriggerSource().equals("CustomMessage_ForgotPassword")) {
			response.setEmailSubject("MAIL FORGOT PASSWORD");
			StringBuilder mailContent = new StringBuilder();
			mailContent.append("さん" + event.getUserName() + "\n");
			mailContent.append("「eSalesManager Remix MS」をご利用いただきありがとうございます。\n");
			mailContent.append("パスワードをリセットするためのURLが発行されました。\n");
			mailContent.append("以下のURLをクリックしてパスワードを再設定してください。\n");
			mailContent.append("■パスワード再設定URL\n");
			mailContent.append("────────────────────────────────────\n");
			mailContent.append("https://remixms.softbrain.co.jp/msQ1j1XjTgog/esales-pc?code="
					+ event.getRequest().getCodeParameter() + "\n");
			mailContent.append("※パスワード変更は60分間有効です。 60分以内にパスワードを変更してください。\n");
			mailContent.append("※60分以上経過した場合は、お手数ですが再度パスワード再発行手続きを行ってください。\n");
			mailContent.append("────────────────────────────────────\n");
			mailContent.append("※このメールはシステムによって送られているので、直接返信はしないで下さい。\n");
			response.setEmailMessage(mailContent.toString());
			event.setResponse(response);
		}

		logger.log("DONE FORGET PASSWORD");
		return event;
	}
}