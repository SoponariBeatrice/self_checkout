package com.example.selfcheckout;

public class ApiParserFactory {

    public IApiParser getApiParser(String apiType)
    {
         IApiParser apiParser = null;
         if(apiType.equals("json"))
         {
             apiParser = new JsonParser();
         }
         else if(apiType.equals("xml"))
         {
             apiParser = new XmlParser();
         }
         return apiParser;
    }
}
