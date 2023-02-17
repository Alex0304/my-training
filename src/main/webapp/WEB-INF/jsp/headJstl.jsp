<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<% String basePath = request.getScheme()+"://"+request.getHeader("HOST")+request.getContextPath(); %>
<%
    String staticVersionNum = "1";
%>
<!DOCTYPE html>
