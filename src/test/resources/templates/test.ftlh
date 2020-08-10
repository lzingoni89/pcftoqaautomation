<html>
<head>
    <title>Welcome!</title>
</head>
<body>
<h1>${pcf.pcfName}</h1>
<h2>${container.widget}</h2>
<h2>${container.widgetID}</h2>
<#list container.children as x>
    <li>${x.widget} - ${x.renderID}</li>
</#list>
</body>
</html>