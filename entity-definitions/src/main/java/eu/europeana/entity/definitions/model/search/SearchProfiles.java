package eu.europeana.entity.definitions.model.search;

public enum SearchProfiles {
	
	FACET, STANDARD;

	public static boolean contains(String value) {

	    for (SearchProfiles c : SearchProfiles.values()) {
	        if(c.name().equals(value) || c.name().toLowerCase().equals(value))
	        	return true;
	    }

	    return false;
	}	

}