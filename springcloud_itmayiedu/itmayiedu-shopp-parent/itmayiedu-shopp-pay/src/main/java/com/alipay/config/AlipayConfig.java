package com.alipay.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

	// ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

		// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
		public static String app_id = "2016092100564758";

		// 商户私钥，您的PKCS8格式RSA2私钥
		public static String merchant_private_key ="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCfsznw2Nosu0WdFY2oLCHC03Nydw7a6nvHVpM+/PktRl4f6wBONh89z2CLzHNAMXYBnxjb7nvPCqXM+K+DTsCwNINoQBKUgLOGI+eMV+zIwL8cNcol1dGtPOoP/D3wZakyjy88abd7HdQb+5V4kQlaa/EAUSs/TfHlDX5P37vTE01ON9xTMDuk5iBKIgj5283xhV9qhoBgaCXOha4Ve+D2Mc/LPccFjc8dnUnJqvU9F8nDn+6xh8r3YQY5r11kHk1z+wGRGMcATy80NfVKHGuod8dU5+PzOAUmiD0atZSer4l7pqVW4FMqbQzjYR/cCs9/fzbQAULXwBNig6a6J+urAgMBAAECggEAYFER8uZ2v1+7HoBvTSxAuGYbTL/tn66oNJNMf0LHbbzw68MqDgGqWfcGvYnzQJYUG+o+bfapiTdcxACAqmsG377sOiXajuIIZDCnaAdncbR+Dp+KacNJkHgamsWA/cnyf+HHlWIVHRXiZXYYsfyXbYeIdK5/rNBvwbufoMDJYQVcZGwyGnwehu25dqwCajQNeiriAVqe5QMfMR8LGQlcahcbff0jA/9m/s9wf6Uz+ksR39bs2KyOnzqWvY4YEh1OPCM+c+YhcThwUdctZ0Esqu0yD6vJWW0m4rV1VkQXx5mfvf17Jj8nUVB20V5IPZi1H0Ciz0HhMpPX1ssuKa5a0QKBgQD26q4ck24w5Lkt6z2rVZEDtKvSU76+4AwdKj20bOVLjjQ63I+4xjrBK9dgx3Kr8Zh9ycxF4Ii+0bsXqwS0Zgsg8CgRfXq9fizwf1dOugzB3c1ZSdpBpXYZ8UlGIJmXh+fN8eF/BLv4hMut7yB9QdNrfUc1oblZGgmQr6PHjAmQDwKBgQClkzCwt/ViE4486l2kcbf6I2gkYmLEzpKXstbgdqZGG8Mmt4UTxWqVpNbljlb9ERvqnIsg2+uBU2ewxKbDd9QxFANDc2465CqnnXdMsEXV1TlGTyaaC2S6srLIHO6Zae69ZJyPGn3nigch+6rvIek8z8eHayhrjt3IaQvAhs7OpQKBgQCNSdqZdW9fQPIJqYtfSBYz1aVgNBV46YFlCu/GF23ZnySZ7e27oIqsUKbstRQT+an1iIyAC6yT7DUvpOeLRrzleyaiY3fhiJq0f1l5LhLwuDex+QWP/Nsomx3/p9XblKP6/4a2BQJ+gZZ/D886fXk6D7S4prMFnlWM2kRQgfxS1wKBgEOyIWcvX2FhE3eubujsIcdgsJSnsTA8MU1armYJvMPPWAO9ZuiFf6V9RuuGPeSsGRjcNjoSEIDIxA1O6cvjc1s5jngGetwTK6lqRTuSmorp6cfoVqUG4YE/zL7qrVcGK7k9qJENyMpg8begOOwalkzpuu+QDW+kcuMcp9RoiaeNAoGBALIird2PJdLFXMYQ1TVCeIcxEJ4GbuGEyY4vFqzEzndBgWVoGLHkFLpi1LJZQiBfXdnbfYsQqYKAKydYM6vW6Peep3Am6d+xDZf9emtuBbV2YSHIsVPfw2hFVR8uwY4gzUugCz5otFazfeka/Q83f0TRpK05IbjsVvGBDwpXQaIA";
		// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm
		// 对应APPID下的支付宝公钥。
		public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1U7j3Bl5QtyF6ZGouhkUFivy4lA10SQlZFP4CBY9uigwMDljVnhhQ/8yfRCHokJ6ZYaHfRivNWxm0zjtmKCNILN8HNnPtEx7lSChPSLkbwAvCtwhgPgtQFXYDlRvRACDfLw9uetUZXP7z0sd1RH8X7LVJ0d/+KN3ODlPLCcjCWXR08uHW7ys4Yrtv2AePnntvSFNNUtGOLG3mbv+sWW14I/++10KwejhnkBaMiBkx8FLcOWvT4bjArLH8TynT4Gd66GJjbCXY1jHELvKuLguBRWL6Ya22iN1udq99GQY6l8Z5trtQhOoCCj0MiAp3V8DRlwVTvg9Hn2raTENpCiJNQIDAQAB";
		// 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
		public static String notify_url = "http://itmayiedu.s1.natapp.cc/alibaba/callBack/notifyUrl";
		
		// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
		public static String return_url = "http://itmayiedu.s1.natapp.cc/alibaba/callBack/returnUrl";

		// 签名方式
		public static String sign_type = "RSA2";

		// 字符编码格式
		public static String charset = "utf-8";

		// 支付宝网关
		public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

		// 支付宝网关
		public static String log_path = "C:\\";

		// ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

		/**
		 * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
		 * 
		 * @param sWord
		 *            要写入日志里的文本内容
		 */
		public static void logResult(String sWord) {
			FileWriter writer = null;
			try {
				writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
				writer.write(sWord);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (writer != null) {
					try {
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
}
