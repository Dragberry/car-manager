<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>
<html>
<body>
	<table border="1">
		<tr>
			<th>Index</th>
			<th>Date</th>
			<th>Description</th>
			<th>Cost</th>
			<th>Currency</th>
		</tr>
		<c:forEach items="${transactionList}"  var="tnx">
			<tr>
				<td>${tnx.transactionKey}</td>
				<td>${tnx.executionDate}</td>
				<td>${tnx.description}</td>
				<td>${tnx.amount}</td>
				<td>${tnx.currency}</td>
			</tr>
		</c:forEach>
	</table>

	<p>
		<c:out value="This is index.jsp page" />
	</p>
</body>
</html>