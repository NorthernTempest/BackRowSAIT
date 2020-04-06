<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:directive.include file="../template/head.jsp" />

<div class="col-12">
	<h4>
		Payment Reports
	</h4>
	<ul>
		<li><a href="/birt-viewer/frameset?__report=report/PaymentsSummary.rptdesign" target="targetframe">Payments Summary</a></li>
		<li><a href="/birt-viewer/frameset?__report=report/PaymentsDetail.rptdesign" target="targetframe">Payments Detail</a></li>
	</ul>
	<h4>
		Tax Return Reports
	</h4>
	<ul>
		<li><a href="/birt-viewer/frameset?__report=report/TaxReturnsSummary.rptdesign" target="targetframe">Tax Returns Summary</a></li>
		<li><a href="/birt-viewer/frameset?__report=report/TaxReturnsDetail.rptdesign" target="targetframe">Tax Returns Detail</a></li>
	</ul>
	<h4>
		Log Reports
	</h4>
	<ul>
		<li><a href="/birt-viewer/frameset?__report=report/LogsSummary.rptdesign" target="targetframe">Logs Summary</a></li>
	</ul>
	<h4>
		Customer Information Reports
	</h4>
	<ul>
		<li><a href="/birt-viewer/frameset?__report=report/CustomerInformationSummary.rptdesign" target="targetframe">Customer Information Summary</a></li>
		<li><a href="/birt-viewer/frameset?__report=report/CustomerInformationDetail.rptdesign" target="targetframe">Customer Information Detail</a></li>
	</ul>
	<br>
	<iframe id="iframe" name="targetframe" width="100%" height="700px" style="background-color:white;"></iframe>
</div>


<jsp:directive.include file="../template/foot.jsp" />