package cn.nicegoose.framework.task;



import cn.nicegoose.project.work.domain.WorkItopOpen;
import cn.nicegoose.project.work.service.IWorkItopOpenService;
import cn.nicegoose.project.work.service.IWorkItopService;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import javax.net.ssl.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.nicegoose.project.work.domain.WorkItop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: nippon-workspace
 * @description: 同步itop平台未清单据数据
 * @author: Riche_Gzc
 * @create: 2023-08-14 08:50
 **/

@Component("syncTask")
public class SyncTask {

	@Autowired
	private IWorkItopService workItopService;

	@Autowired
	private IWorkItopOpenService workItopOpenService;

	@Value("${nicegoose.payloadPath}")
	String payloadPath ;

	@Value("${nicegoose.payloadOpenPath}")
	String payloadOpenPath ;

	public  void loginItop() throws  KeyManagementException {

		long startTime = System.currentTimeMillis();
		String url = "https://itop.nipponpaint.com.cn/itop/pages/UI.php";
		//采用绕过验证的方式处理https请求
		SSLContext sslcontext = null;
		try {
			 sslcontext = createIgnoreVerifySSL();
		} catch (NoSuchAlgorithmException e) {
			System.out.println("e = " + e);
		}

		// 设置协议http和https对应的处理socket链接工厂的对象
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", new SSLConnectionSocketFactory(sslcontext))
				.build();
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		HttpClients.custom().setConnectionManager(connManager);
		Map<String, String> headers = new HashMap<>();
		headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
		headers.put("Accept-Encoding", "gzip, deflate, br");
		headers.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6");
		headers.put("Cache-Control", "max-age=0");
		headers.put("Connection", "keep-alive");
		//headers.put("Content-Length", "71");
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		headers.put("Cookie", "itop-ca16f7092e8cdb5a03863c23dd2dc070=vgrltgnssmsrp76jrc8ufd8c34");
		headers.put("Host", "itop.nipponpaint.com.cn");
		headers.put("Origin", "https://itop.nipponpaint.com.cn");
		headers.put("Referer", "https://itop.nipponpaint.com.cn/itop/sso/login.php?code=ST-86794-aeUh1UPoxrusGnCSGNgv");
		headers.put("sec-ch-ua", "\"Microsoft Edge\";v=\"107\", \"Chromium\";v=\"107\", \"Not=A?Brand\";v=\"24\"");
		headers.put("sec-ch-ua-mobile", "?0");
		headers.put("sec-ch-ua-platform", "\"Windows\"");
		headers.put("Sec-Fetch-Dest", "document");
		headers.put("Sec-Fetch-Mode", "navigate");
		headers.put("Sec-Fetch-Site", "same-origin");
		headers.put("Sec-Fetch-User", "?1");
		headers.put("Upgrade-Insecure-Requests", "1");
		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36 Edg/107.0.1418.56");

		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("auth_user", "gaozecheng"));
		params.add(new BasicNameValuePair("auth_pwd", "Abcd@1234"));
		params.add(new BasicNameValuePair("login_mode", "form"));
		params.add(new BasicNameValuePair("loginop", "login"));
		//创建自定义的httpclient对象
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connManager).build();
		HttpPost httpPost = new HttpPost(url);
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			httpPost.setHeader(entry.getKey(), entry.getValue());
		}
		httpPost.setEntity(new UrlEncodedFormEntity( params, StandardCharsets.UTF_8));
		CloseableHttpResponse response;
		try {
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String body = EntityUtils.toString(entity, "utf-8");
				//System.out.println(body);
			}
			// 获得响应中的cookies，对应python代码r.cookies.get_dict()
			for (Header header : response.getHeaders("Set-Cookie")) {
				String cookie = header.getValue();
				System.out.println(cookie);
			}
			long endTime = System.currentTimeMillis();
//			System.out.println("耗时：" + (endTime - startTime) + "毫秒");


		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	//获取itop未处理数据
	@Transactional
	public void getItopOepn() throws NoSuchAlgorithmException, IOException, KeyManagementException {
		String url = "https://itop.nipponpaint.com.cn/itop/pages/ajax.render.php";
		SSLContext sslcontext = createIgnoreVerifySSL();
		// 设置协议http和https对应的处理socket链接工厂的对象
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", new SSLConnectionSocketFactory(sslcontext))
				.build();
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		HttpClients.custom().setConnectionManager(connManager);
		Map<String, String> headers = new HashMap<>();
		headers.put("Accept", "application/json, text/javascript, */*; q=0.01");
		headers.put("Accept-Encoding", "gzip, deflate, br");
		headers.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,or;q=0.7,zh-TW;q=0.6");
		headers.put("Connection", "keep-alive");

		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		headers.put("Cookie", "itop-ca16f7092e8cdb5a03863c23dd2dc070=vgrltgnssmsrp76jrc8ufd8c34");
		headers.put("Host", "itop.nipponpaint.com.cn");
		headers.put("Origin", "https://itop.nipponpaint.com.cn");
		headers.put("Referer", "https://itop.nipponpaint.com.cn/itop/pages/UI.php?c[menu]=MyShortcuts_69");
		headers.put("Sec-Fetch-Dest", "empty");
		headers.put("Sec-Fetch-Mode", "cors");
		headers.put("Sec-Fetch-Site", "same-origin");
		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");
		headers.put("X-Combodo-Ajax", "true");
		headers.put("X-Requested-With", "XMLHttpRequest");
		headers.put("sec-ch-ua", "\"Not.A/Brand\";v=\"8\", \"Chromium\";v=\"114\", \"Google Chrome\";v=\"114\"");
		headers.put("sec-ch-ua-mobile", "?0");
		headers.put("sec-ch-ua-platform", "\"Windows\"");

		File file = new File(payloadOpenPath);

		FileInputStream inputStream = new FileInputStream(file);
		int length = inputStream.available();
		byte bytes[] = new byte[length];
		inputStream.read(bytes);
		inputStream.close();
		String payload =new String(bytes, StandardCharsets.UTF_8);

		//System.out.println("payload = " + payload);
		//创建自定义的httpclient对象
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connManager).build();

		HttpPost httpPost = new HttpPost(url);

		for (Map.Entry<String, String> entry : headers.entrySet()) {
			httpPost.setHeader(entry.getKey(), entry.getValue());
		}


		httpPost.setEntity(new StringEntity(payload, StandardCharsets.UTF_8));


		try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
			HttpEntity entity = response.getEntity();
			//System.out.println("entity = " + entity);
			if (entity != null) {
				String responseJson = EntityUtils.toString(entity, StandardCharsets.UTF_8);
				//使用fastjson解析json字符串
				JSONObject jsonObject = JSONObject.parseObject(responseJson);
				JSONArray jsonData = jsonObject.getJSONArray("data");
				//
				Iterator<Object> iterator = jsonData.iterator();
				List<WorkItopOpen> itopModelList = new ArrayList<>();

				while (iterator.hasNext()){

					JSONObject next = (JSONObject) iterator.next();
					WorkItopOpen itopModel = new WorkItopOpen();
					itopModel.setUserRequestId(Long.valueOf(next.getString("UserRequest/_key_")));
					//next.getString("UserRequest/hyperlink")用正则表达式将href="https://itop.nipponpaint.com.cn/itop/pages/UI.php?operation=details&class=UserRequest&id=239690&"提取
					// 定义正则表达式模式
					String pattern = "href=\"([^\"]*)\"";
					// 将正则表达式编译为Pattern类的实例
					Pattern r = Pattern.compile(pattern);
					// 创建匹配器
					Matcher m = r.matcher(next.getString("UserRequest/hyperlink"));
					// 使用find()方法查找第一个匹配的对象
					if (m.find()) {
						itopModel.setHyperlink(m.group(1));
					} else {
						itopModel.setHyperlink("");

					}

					itopModel.setReportDate(next.getString("UserRequest/report_date"));
					itopModel.setAgentName(next.getString("UserRequest/agent_name"));
					itopModel.setServicefamilyName(next.getString("UserRequest/servicefamily_name"));
					//如果next.getString("UserRequest/service_name")包含&amp;则替换为&
					if (next.getString("UserRequest/service_name").contains("&amp;")){
						itopModel.setServiceName(next.getString("UserRequest/service_name").replace("&amp;","&"));
					}else{
						itopModel.setServiceName(next.getString("UserRequest/service_name"));
					}

					itopModel.setTitle(next.getString("UserRequest/title"));
					itopModel.setRegion(next.getString("UserRequest/region"));
					//将数据存入list
					itopModelList.add(itopModel);

				}
				workItopOpenService.remove(null);
				workItopOpenService.saveBatch(itopModelList);

			}
		}
	}


	//获取itop待接单数据
	@Transactional
	public void getItop() throws NoSuchAlgorithmException, KeyManagementException, IOException {
		String url = "https://itop.nipponpaint.com.cn/itop/pages/ajax.render.php";
		SSLContext sslcontext = createIgnoreVerifySSL();
		// 设置协议http和https对应的处理socket链接工厂的对象
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", new SSLConnectionSocketFactory(sslcontext))
				.build();
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		HttpClients.custom().setConnectionManager(connManager);
		Map<String, String> headers = new HashMap<>();
		headers.put("Accept", "application/json, text/javascript, */*; q=0.01");
		headers.put("Accept-Encoding", "gzip, deflate, br");
		headers.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,or;q=0.7,zh-TW;q=0.6");
		headers.put("Connection", "keep-alive");

		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		headers.put("Cookie", "itop-ca16f7092e8cdb5a03863c23dd2dc070=vgrltgnssmsrp76jrc8ufd8c34");
		headers.put("Host", "itop.nipponpaint.com.cn");
		headers.put("Origin", "https://itop.nipponpaint.com.cn");
		headers.put("Referer", "https://itop.nipponpaint.com.cn/itop/pages/UI.php?c[menu]=MyShortcuts_69");
		headers.put("Sec-Fetch-Dest", "empty");
		headers.put("Sec-Fetch-Mode", "cors");
		headers.put("Sec-Fetch-Site", "same-origin");
		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");
		headers.put("X-Combodo-Ajax", "true");
		headers.put("X-Requested-With", "XMLHttpRequest");
		headers.put("sec-ch-ua", "\"Not.A/Brand\";v=\"8\", \"Chromium\";v=\"114\", \"Google Chrome\";v=\"114\"");
		headers.put("sec-ch-ua-mobile", "?0");
		headers.put("sec-ch-ua-platform", "\"Windows\"");

		File file = new File(payloadPath);

		FileInputStream inputStream = new FileInputStream(file);
		int length = inputStream.available();
		byte bytes[] = new byte[length];
		inputStream.read(bytes);
		inputStream.close();
		String payload =new String(bytes, StandardCharsets.UTF_8);

		//System.out.println("payload = " + payload);
		//创建自定义的httpclient对象
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connManager).build();

		HttpPost httpPost = new HttpPost(url);

		for (Map.Entry<String, String> entry : headers.entrySet()) {
			httpPost.setHeader(entry.getKey(), entry.getValue());
		}


		httpPost.setEntity(new StringEntity(payload, StandardCharsets.UTF_8));


		try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
			HttpEntity entity = response.getEntity();
			//System.out.println("entity = " + entity);
			if (entity != null) {
				String responseJson = EntityUtils.toString(entity, StandardCharsets.UTF_8);
				//使用fastjson解析json字符串
				JSONObject jsonObject = JSONObject.parseObject(responseJson);
				JSONArray jsonData = jsonObject.getJSONArray("data");
				//
				Iterator<Object> iterator = jsonData.iterator();
				List<WorkItop> itopModelList = new ArrayList<>();

				while (iterator.hasNext()){

					JSONObject next = (JSONObject) iterator.next();
					WorkItop itopModel = new WorkItop();
					itopModel.setUserRequestId(Long.valueOf(next.getString("UserRequest/_key_")));
					//next.getString("UserRequest/hyperlink")用正则表达式将href="https://itop.nipponpaint.com.cn/itop/pages/UI.php?operation=details&class=UserRequest&id=239690&"提取
					// 定义正则表达式模式
					String pattern = "href=\"([^\"]*)\"";
					// 将正则表达式编译为Pattern类的实例
					Pattern r = Pattern.compile(pattern);
					// 创建匹配器
					Matcher m = r.matcher(next.getString("UserRequest/hyperlink"));
					// 使用find()方法查找第一个匹配的对象
					if (m.find()) {
						itopModel.setHyperlink(m.group(1));
					} else {
						itopModel.setHyperlink("");

					}

					itopModel.setReportDate(next.getString("UserRequest/report_date"));
					itopModel.setCallerName(next.getString("UserRequest/caller_name"));
					itopModel.setServicefamilyName(next.getString("UserRequest/servicefamily_name"));
					//如果next.getString("UserRequest/service_name")包含&amp;则替换为&
					if (next.getString("UserRequest/service_name").contains("&amp;")){
						itopModel.setServiceName(next.getString("UserRequest/service_name").replace("&amp;","&"));
					}else{
						itopModel.setServiceName(next.getString("UserRequest/service_name"));
					}

					itopModel.setTitle(next.getString("UserRequest/title"));
					itopModel.setRegion(next.getString("UserRequest/region"));
					//将数据存入list
					itopModelList.add(itopModel);

				}
				workItopService.remove(null);
				workItopService.saveBatch(itopModelList);

			}
		}
	}

	public static SSLContext createIgnoreVerifySSL() throws  NoSuchAlgorithmException, KeyManagementException {
		SSLContext sc = null;// 创建套接字对象
		try {
			sc = SSLContext.getInstance("TLS");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();

		}
		// 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
		X509TrustManager trustManager = new X509TrustManager() {
			@Override
			public void checkClientTrusted(
					java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(
					java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		try {
			sc.init(null, new TrustManager[]{trustManager}, null);
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
		return sc;
	}

}
