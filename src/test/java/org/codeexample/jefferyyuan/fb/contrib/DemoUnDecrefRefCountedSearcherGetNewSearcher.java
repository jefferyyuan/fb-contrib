package org.codeexample.jefferyyuan.fb.contrib;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.solr.handler.RequestHandlerBase;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.response.SolrQueryResponse;
import org.apache.solr.search.QParser;
import org.apache.solr.search.SolrIndexSearcher;
import org.apache.solr.util.RefCounted;

public class DemoUnDecrefRefCountedSearcherGetNewSearcher extends
		RequestHandlerBase {

	@Override
	public void handleRequestBody(SolrQueryRequest req, SolrQueryResponse rsp)
			throws Exception {

		RefCounted<SolrIndexSearcher> refCounted = req.getCore()
				.getNewestSearcher(true);
		SolrIndexSearcher searcher = refCounted.get();
		String qstr = "datatype:4";
		QParser qparser = QParser.getParser(qstr, "lucene", req);
		Query query = qparser.getQuery();

		int topn = 1;
		TopDocs topDocs = searcher.search(query, topn);
		for (int i = 0; i < topDocs.totalHits; i++) {
			ScoreDoc match = topDocs.scoreDocs[i];
			Document doc = searcher.doc(match.doc);
			System.out.println(doc.get("contentid"));
		}

		// here it reports another UN_DECREF_REFCOUNTED_SEARCHER
		refCounted = req.getCore().getNewestSearcher(true);
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
