/*
 * Copyright 2007-2012 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 * 
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under
 *  the Licence.
 */

package eu.europeana.entity.definitions.model.search.result;

import java.util.List;
import java.util.Map;

import eu.europeana.entity.definitions.model.search.Query;

/**
 * taken from the corelib-definitions/corelib-search and eliminated explicit solr dependencies
 * @author Sergiu Gordea @ait
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public class ResultSet<T> {

	/**
	 * The request query object
	 */
	private Query query;

	/**
	 * The language to be used by search handler
	 */
	private String language;

	/**
	 * The list of result objects
	 */
	private List<T> results;

	/**
	 * The list of facets
	 */
	private List<FacetFieldView> facetFields;

	/**
	 * The query facets response
	 */
	private Map<String, Integer> queryFacets;

	// statistics

	/**
	 * The total number of results
	 */
	private long resultSize;

	/**
	 * The time in millisecond how long the search has been taken
	 */
	private long searchTime;

	/**
	 * GETTERS & SETTTERS
	 */

	public List<T> getResults() {
		return results;
	}

	public ResultSet<T> setResults(List<T> list) {
		this.results = list;
		return this;
	}

	public Query getQuery() {
		return query;
	}

	public ResultSet<T> setQuery(Query query) {
		this.query = query;
		return this;
	}

	public List<FacetFieldView> getFacetFields() {
		return facetFields;
	}

	public ResultSet<T> setFacetFields(List<FacetFieldView> facetFields) {
		this.facetFields = facetFields;
		return this;
	}

	/**
	 * Gets the total number of results
	 * @return
	 */
	public long getResultSize() {
		return resultSize;
	}

	public ResultSet<T> setResultSize(long resultSize) {
		this.resultSize = resultSize;
		return this;
	}

	public long getSearchTime() {
		return searchTime;
	}

	public ResultSet<T> setSearchTime(long l) {
		this.searchTime = l;
		return this;
	}

	public Map<String, Integer> getQueryFacets() {
		return queryFacets;
	}

	public ResultSet<T> setQueryFacets(Map<String, Integer> queryFacets) {
		this.queryFacets = queryFacets;
		return this;
	}

	@Override
	public String toString() {
		return "ResultSet [query=" + query + ", results=" + results
				+ ", facetFields=" + facetFields 
				+ ", resultSize=" + resultSize + ", searchTime=" + searchTime
				+ "]";
	}

	public boolean isEmpty(){
		return getResults() == null || getResults().isEmpty();
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
}
