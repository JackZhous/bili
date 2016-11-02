package com.jack.zhou.bili.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.widget.Toast;

import com.jack.zhou.bili.adapter.ListViewAdapter;
import com.jack.zhou.bili.bean.Country;
import com.jack.zhou.bili.ui.RegisterActivity;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**读取xml的工具类，xml必须放在assets目录下才行
 * Created by "jackzhous" on 2016/7/7.
 */
public class XMLUtil extends DefaultHandler{

    private Context context;
    private static XMLUtil instance;
    private ArrayList<Country> array;
    private Country country;
    private String CtagName;                        //当前节点名字

    public XMLUtil(Context context){
        super();
        this.context = context;
        ListViewAdapter.init(context);
    }


    public static XMLUtil getInstance(Context context){
        if(null == instance){
            instance = new XMLUtil(context);
        }

        return instance;
    }


    public void init(){
        AssetManager manager = context.getAssets();
        InputStream stream = null;
        try {
            stream = manager.open("country.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(stream, this);
        } catch (IOException e) {
            Toast.makeText(context, "无法找到country.xml文件", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }finally {
            if(null != stream){
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        JLog.default_print("endDocument");

        for(Country c : array){
            JLog.default_print(c.toString());
        }
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        JLog.default_print("startDocument");
        array = new ArrayList<Country>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        JLog.default_print("startElement " + localName + " " + qName);
        if("country".equals(localName)){
            country = new Country();
        }
        CtagName = qName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        JLog.default_print("endElement");
        if("country".equals(localName)){
            array.add(country);
        }
        CtagName = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        JLog.default_print("characters");
        String value = new String(ch, start, length);
        if("name".equals(CtagName)){
            country.setCountry_name(value);
        }else if("phone".equals(CtagName)){
            country.setCountry_phone(value);
        }
    }

    public ArrayList<Country> getCountryData(){
        return array;
    }

}
