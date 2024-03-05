class RaindropConverter {
	String convert(int number) {
    	StringBuffer buffer = new StringBuffer();

		if(number % 3 == 0)
    		buffer.append("Pling");
    	if(number % 5 == 0)
    		buffer.append("Plang");
    	if(number % 7 == 0)
    		buffer.append("Plong");
    	if(buffer.length() == 0)
    		buffer.append(number);
    	
    	return buffer.toString();
    }
}
