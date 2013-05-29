package org.codeexample.jefferyyuan.fb.contrib;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.core.SolrCore;
import org.apache.solr.handler.RequestHandlerBase;
import org.apache.solr.request.LocalSolrQueryRequest;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.request.SolrRequestHandler;
import org.apache.solr.response.SolrQueryResponse;

public class DemoClosedSolrRequest extends RequestHandlerBase {

	@Override
	public void handleRequestBody(SolrQueryRequest req, SolrQueryResponse rsp)
			throws Exception {
		SolrCore core = req.getCore();
		SolrQuery query = new SolrQuery();
		query.setQuery("datatype:4").setFacet(true).addFacetField("szkb")
				.setFacetMinCount(2);
		SolrQueryRequest facetReq = new LocalSolrQueryRequest(core, query);
		try {
			SolrRequestHandler handler = core.getRequestHandler("/select");
			handler.handleRequest(facetReq, new SolrQueryResponse());
		} finally {
			facetReq.close();
		}
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public String getSource() {
		return null;
	}

}
