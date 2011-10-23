package br.com.doctors.util.converter;

import java.util.Map;
import java.util.Map.Entry;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class JSONMapConverter implements Converter {

	@Override
	public boolean canConvert(Class type) {
		return Map.class.isAssignableFrom(type);  
	}

	@Override
	public void marshal(Object obj, HierarchicalStreamWriter writer,
			MarshallingContext context) {
        Map<?,?> map = (Map<?,?>) obj;  
        for (Entry<?,?> entry : map.entrySet()) {  
        	writer.startNode(entry.getKey().toString());  
            writer.setValue(entry.getValue().toString());  
            writer.endNode(); 
        }  
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader arg0,
			UnmarshallingContext arg1) {
		return null;
	}

}
