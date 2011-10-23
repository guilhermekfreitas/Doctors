package br.com.doctors.util.converter;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import br.com.caelum.vraptor.interceptor.TypeNameExtractor;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.serialization.JSONSerialization;
import br.com.caelum.vraptor.serialization.ProxyInitializer;
import br.com.caelum.vraptor.serialization.xstream.XStreamBuilder;
import br.com.caelum.vraptor.serialization.xstream.XStreamJSONSerialization;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

@Component
public class JSONMapSerialization extends XStreamJSONSerialization {  

    public JSONMapSerialization(HttpServletResponse response,
			TypeNameExtractor extractor, ProxyInitializer initializer,
			XStreamBuilder builder) {
		super(response, extractor, initializer, builder);
	}

	@Override
    protected XStream getXStream() {
    	XStream xStream = super.getXStream();
    	xStream.registerConverter(new JSONMapConverter());
    	return xStream;
    }
    
    public boolean canConvert(Class type) {  
        return Map.class.isAssignableFrom(type);  
    }  
    
   /* public void marshal(Object obj, HierarchicalStreamWriter writer, MarshallingContext context) {  
        writer.startNode("map");  
        Map<?,?> map = (Map<?,?>) obj;  
        for (Entry<?,?> entry : map.entrySet()) {  
            writer.addAttribute(entry.getKey().toString(), entry.getValue().toString());  
            System.out.printf("%s, %s\n", entry.getKey().toString(), entry.getValue().toString());
        }  
        writer.endNode();  
    }  */
  
	public Object unmarshal(HierarchicalStreamReader arg0,
			UnmarshallingContext arg1) {
		return null; // não precisa
	}

} 