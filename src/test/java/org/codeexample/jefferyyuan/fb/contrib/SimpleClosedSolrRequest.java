package org.codeexample.jefferyyuan.fb.contrib;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.core.SolrCore;
import org.apache.solr.request.LocalSolrQueryRequest;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.response.SolrQueryResponse;

public class SimpleClosedSolrRequest {
	public void simpleClosedRequest(SolrQueryRequest req, SolrQueryResponse rsp)
			throws Exception {
		SolrCore core = null;
		SolrQuery query = null;
		SolrQueryRequest facetReq = new LocalSolrQueryRequest(core, query);
		facetReq.close();
	}
}