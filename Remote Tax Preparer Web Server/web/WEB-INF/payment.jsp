<%--
  Created by IntelliJ IDEA.
  User: Kerk Day
  Date: 2020-03-30
  Time: 12:08 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:directive.include file="../template/head.jsp" />

<div class="container">
	<div class="row">
		<div class="col-6">
			<div id="paypal-button-container"></div>
			<script
				src="https://www.paypal.com/sdk/js?intent=capture&currency=CAD&client-id=AdKunkE584jUYp7FJSMWj3JL3pRi0hxt63F9LvEl_zgB47GGi4xamMyNRv0sYUvPQ_2_9hJxvFmyODyl"></script>
			<script>
				/*
				var myOrder = {
					intent : 'CAPTURE',
					payer : {
						name : {
							given_name : '${user.fName}',
							surname : '${user.lName}'
						},
						address : {
							address_line_1 : '${user.streetAddress}',
							address_line_2 : '${user.streetAddress2}',
							admin_area_2 : '${user.city}',
							admin_area_1 : '${user.province}',
							postal_code : '${user.postalCode}',
							country_code : '${user.country}'
						},
						email_address : '${user.email}',
						phone : {
							phone_type : "MOBILE",
							phone_number : {
								national_number : '${user.phone}'
							}
						}
					},
					purchase_units : [ {
						amount : {
							value : '${amount}',
							currency_code : 'CAD'
						},
						shipping : {
							address : {
								address_line_1 : '${user.streetAddress}',
								address_line_2 : '${user.streetAddress2}',
								admin_area_2 : '${user.city}',
								admin_area_1 : '${user.province}',
								postal_code : '${user.postalCode}',
								country_code : '${user.country}'
							}
						},
					} ]
				};
				*/
				
				// var myDetails;
				
				var myOrder = {
						//https://developer.paypal.com/docs/api/orders/v2/#orders-create-request-body
						payer : {
							name : {
								given_name : '${user.fName}',
								surname : '${user.lName}'
							},
							email_address : '${user.email}'
						},
						purchase_units : [{
							//https://developer.paypal.com/docs/api/orders/v2/#definition-purchase_unit_request
							amount : {
								value: ${amount},
								currency_code: 'CAD'
							}
						}]
				};
				
				paypal.Buttons({
					createOrder : function(data, actions) {
						// This function sets up the details of the transaction, including the amount and line item details.
						return actions.order.create(myOrder);
					},
					onApprove : function(data, actions) {
						// This function captures the funds from the transaction.
						return actions.order.capture().then(function(details) {
							//https://developer.paypal.com/docs/api/orders/v1/?mark=capture#definition-details
							
							document.getElementById("payment-id").value = details.purchase_units[0].payments.captures[0].id;
							document.getElementById("payment-amount").value = details.purchase_units[0].payments.captures[0].amount.value;
							document.getElementById("payment-date-time").value = details.purchase_units[0].payments.captures[0].create_time;
							//console.log(details.purchase_units[0].payments.captures[0].create_time);
							document.getElementById("payment-status").value = details.purchase_units[0].payments.captures[0].status;
									
							document.getElementById('payment-results').submit();
						
							//myDetails = details;
						});
					}
				}).render('#paypal-button-container');
			</script>
		</div>
	</div>
</div>
<form id="payment-results" action="/payments" method="POST" hidden>
	<input id="payment-id" type="hidden" name="id">
	<input id="payment-type" type="hidden" name="type" value="paypal"/>
	<input id="payment-amount" type="hidden" name="amount"/>
	<input id="payment-date-time" type="hidden" name="dateTime"/>
	<input id="payment-year" type="hidden" name="taxYear" value="${year}"/>
	<input id="payment-status" type="hidden" name="status"/>
</form>

<jsp:directive.include file="../template/foot.jsp" />
