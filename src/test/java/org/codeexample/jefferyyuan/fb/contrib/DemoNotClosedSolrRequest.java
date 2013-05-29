package org.codeexample.jefferyyuan.fb.contrib;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.core.SolrCore;
import org.apache.solr.request.LocalSolrQueryRequest;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.response.SolrQueryResponse;

import edu.umd.cs.findbugs.annotations.ExpectWarning;

public class DemoNotClosedSolrRequest {
	// @ExpectWarning(NotClosedRequestDetector.NOT_CLOSE_SOLR_REQUEST)
	// public void handleRequestBody(SolrQueryRequest req, SolrQueryResponse
	// rsp)
	// throws Exception {
	// SolrCore core = req.getCore();
	// SolrQuery query = new SolrQuery();
	// query.setQuery("datatype:4").setFacet(true).addFacetField("szkb")
	// .setFacetMinCount(2);
	// SolrQueryRequest facetReq = new LocalSolrQueryRequest(core, query);
	// System.out.println(facetReq);
	//
	// facetReq = new LocalSolrQueryRequest(core, query);
	// System.out.println(facetReq);
	// // facetReq.close();
	// // SolrRequestHandler handler = core.getRequestHandler("/select");
	// // handler.handleRequest(facetReq, new SolrQueryResponse());
	// }

	// public void closedRequest(SolrQueryRequest req, SolrQueryResponse rsp)
	// throws Exception {
	// SolrCore core = req.getCore();
	// SolrQuery query = new SolrQuery();
	// query.setQuery("datatype:4").setFacet(true).addFacetField("szkb")
	// .setFacetMinCount(2);
	// SolrQueryRequest facetReq = new LocalSolrQueryRequest(core, query);
	// System.out.println(facetReq);
	//
	// facetReq = new LocalSolrQueryRequest(core, query);
	// facetReq.close();
	// System.out.println(facetReq);
	// // facetReq.close();
	// // SolrRequestHandler handler = core.getRequestHandler("/select");
	// // handler.handleRequest(facetReq, new SolrQueryResponse());
	// }

	public void simpleClosedRequest(SolrQueryRequest req, SolrQueryResponse rsp)
			throws Exception {
		SolrCore core = null;
		SolrQuery query = null;
		SolrQueryRequest facetReq = new LocalSolrQueryRequest(core, query);
		facetReq = new LocalSolrQueryRequest(core, query);
		facetReq.close();
	}
}