package com.desjardins.xwikirest2;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.HttpHost;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.HttpHeaders;

public class Adduser {

	public static void main(String[] args) {

		String host = "localhost";
		int port = 8082;
		String url = "http://localhost:8082/xwiki/rest/wikis/xwiki/spaces/XWiki/pages/JohnDoe/objects";
		String user = "Admin";
		String password = "admin";
				
		
		HttpHost target = new HttpHost(host, port, "http");
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(
		   new AuthScope(target.getHostName(), target.getPort()),
		   new UsernamePasswordCredentials(user, password));
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
		try {
		
		  // Create AuthCache instance
		  AuthCache authCache = new BasicAuthCache();
		
		  // Generate BASIC scheme object and add it to the local auth cache
		  BasicScheme basicAuth = new BasicScheme();
		  authCache.put(target, basicAuth);
		
		  // Add AuthCache to the execution context
		  HttpClientContext localContext = HttpClientContext.create();
		  localContext.setAuthCache(authCache);
		  localContext.setAttribute("preemptive-auth", basicAuth);
		
		  HttpPost httpPost = new HttpPost(url);
		
		  List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		  nvps.add(new BasicNameValuePair("className", "XWiki.XWikiUsers"));
		  nvps.add(new BasicNameValuePair("property#first_name", "John"));
		  nvps.add(new BasicNameValuePair("property#last_name", "Doe"));
		
		  httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
		  httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
		  try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		  CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(target, httpPost, localContext);
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		  try {
			// Request status
			int requestStatus = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
		
			// do something useful with the response body
		
			// Request String response
			try {
				String requestResponse = EntityUtils.toString(entity);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				EntityUtils.consume(entity);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  } finally {
			try {
				response.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
		} finally {
		  
		    try {
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		}

	}






