package com;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetKB {
	//encoding the part of url
	public static String encode(String url) {
		try {
			String encodeURL = URLEncoder.encode(url, "UTF-8");
			return encodeURL;
		} catch (UnsupportedEncodingException e) {
			return "Issue while encoding" + e.getMessage();
		}
	}
	
	//generate similarity of two string
	public static float isTextSimilar(String str11,String str22) {  
    	String str1 = str11.toLowerCase();
    	String str2 = str22.toLowerCase();
        int len1 = str1.length();  
        int len2 = str2.length();  
        int[][] dif = new int[len1 + 1][len2 + 1];  
        for (int a = 0; a <= len1; a++) {  
            dif[a][0] = a;  
        }  
        for (int a = 0; a <= len2; a++) {  
            dif[0][a] = a;  
        }
        int temp;  
        for (int i = 1; i <= len1; i++) {  
            for (int j = 1; j <= len2; j++) {  
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {  
                    temp = 0;  
                } else {  
                    temp = 1;  
                }  
                dif[i][j] = Math.min(dif[i - 1][j - 1] + temp, Math.min(dif[i][j - 1] + 1,  dif[i - 1][j] + 1));  
            }  
        }  
        float similarity = 1 - (float) dif[len1][len2] / Math.max(str1.length(), str2.length());   
        return similarity ;
    }
	
	public static ArticleDetail getKBContentWithQuery(String queryString){
		float checkPercentage = 0.1f; 
		String encodeURL = encode(queryString);
		ArticleDetail articleDetail = new ArticleDetail();
		//generate final url
		String URL = "https://www.google.com/search?q=support.citrix.com+"+encodeURL+"&oq=support.citrix.com+"+encodeURL+"&aqs=chrome..69i57.21703j0j7&sourceid=chrome&ie=UTF-8";
		try {
    		Connection.Response conn = Jsoup.connect(URL)
	                .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
	                .timeout(10000)
	                .execute();
    		if(conn.statusCode() == 200){
	        	Document doc = conn.parse();
	        	Element body = doc.body();
	        	URL = body.getElementsByClass("g").get(0).getElementsByClass("_Rm").get(0).text();		        			        			        
	        }
        } catch (IOException e) {
        	System.out.println("Outter Page 404.");
        }
		System.out.println("Checking URL:"+URL);
		//check url
		if(URL.indexOf("https://support.citrix.com/article/CTX") != -1){
			try {
	    		//get connection with url
	    		Connection.Response conn = Jsoup.connect(URL)
		                .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
		                .timeout(10000)
		                .execute();
	        	Document doc = conn.parse();
	        	Element body = doc.body();
	        	
	        	//check Similarity and element
	        	String subject = body.getElementById("titleCont").select("h1").text();
	        	System.out.println("Similarity Percentage:"+isTextSimilar(queryString,subject));
	        	System.out.println("Element check:"+(body.getElementById("SymptomsorError") != null));
	        	if((isTextSimilar(queryString,subject) >= checkPercentage) && (body.getElementById("SymptomsorError") != null)){
	        		//set ArticleName and Subject
	        		articleDetail.setArticleName(URL.substring(URL.indexOf("CTX"), URL.indexOf("CTX")+9));
	        		articleDetail.setSubject(subject);
	        		//set Symptom
	        		Elements section = body.getElementsByClass("sectionItem section");
			        for(Element e : section){
			        	if(e.select("h2").text().equals("Symptoms or Error")){
			        		articleDetail.setSymptom(e.text());
			        		break;
			        	}
			        }
	        	}
	         } catch (IOException e) {
	            System.out.println("Inner Page 404.");
	         }
		}
		return articleDetail;
	}

	public static void main(String[] args) {
		
//		System.out.println(getKBContentWithQuery("テクニカル サポートの対応範囲および Q&A"));		
//		System.out.println(getKBContentWithQuery("KCS Product Documentation (eDoc) Placeholder Article"));
//		System.out.println(getKBContentWithQuery("Technical Primer - Machine Creation Services (MCS)"));
		System.out.println(getKBContentWithQuery("Recommended Hotfixes for XenApp 6.x on Windows Server 2008 R2"));
		System.out.println(getKBContentWithQuery("Profile Management 5.x Advanced Troubleshooting Guide"));
		System.out.println(getKBContentWithQuery("KCS Hotfix Placeholder Article"));
		System.out.println(getKBContentWithQuery("Technical Primer - Licensing"));
		System.out.println(getKBContentWithQuery("How to Migrate XenDesktop Database to New SQL Server"));
		System.out.println(getKBContentWithQuery("User Profile Best Practices for XenApp"));
		System.out.println(getKBContentWithQuery("Technical Primer - Printing in Citrix Environments"));
		System.out.println(getKBContentWithQuery("XenApp 6.5 Event Log Messages"));
		System.out.println(getKBContentWithQuery("Troubleshooting IMA Service Failure To Start"));
		System.out.println(getKBContentWithQuery("Graceful Logoff from a Published Application Renders the Session in Active State"));
		System.out.println(getKBContentWithQuery("XenDesktop 7.x - Event Log Messages"));
		System.out.println(getKBContentWithQuery("Seamless Configuration Settings"));
		System.out.println(getKBContentWithQuery("How to Troubleshoot Citrix ICA Printer Autocreation"));
		System.out.println(getKBContentWithQuery("XenApp Migration Guide"));
		System.out.println(getKBContentWithQuery("Xenapp 6.5 - Randomly applications do not launch"));
		System.out.println(getKBContentWithQuery("Recommended Hotfixes for XenApp 7.x"));
		System.out.println(getKBContentWithQuery("How to Disable Citrix API Hooks on a Per-application Basis"));
		System.out.println(getKBContentWithQuery("Published Applications or Desktops not Launching or Disappear during Launch"));
		System.out.println(getKBContentWithQuery("How to Manually Install and Configure Citrix Receiver for Pass-Through Authentication"));
		System.out.println(getKBContentWithQuery("How to Troubleshoot Client Drive Mapping"));
		System.out.println(getKBContentWithQuery("Communication Ports Used by Citrix Technologies"));
		System.out.println(getKBContentWithQuery("Citrix Director 7.6 Failure Reasons Troubleshooting Guide"));
		System.out.println(getKBContentWithQuery("Technical Primer - StoreFront"));
		System.out.println(getKBContentWithQuery("Explorer.exe Fails to Launch"));
		System.out.println(getKBContentWithQuery("Published Application Fails to Launch Outside of Desktop Session"));
		System.out.println(getKBContentWithQuery("Virtual Delivery Agent (VDA) Registration Troubleshooting Tips"));
		System.out.println(getKBContentWithQuery("Troubleshooting Process for Printing Issues with Auto-creation Failure and Citrix Print Manager Crashing"));
		System.out.println(getKBContentWithQuery("Troubleshooting Virtual Desktop Agent Registration with Controllers in XenDesktop"));
	}
}
