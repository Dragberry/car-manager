<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
    	<meta charset="utf-8">
    	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
        <base href='<c:url value="/"/>'>
	    <link href='<c:url value="/theme/css/bootstrap.min.css"/>' rel="stylesheet"/>
	    <link href='<c:url value="/theme/css/sticky-footer.css"/>' rel="stylesheet"/>
        <link href="http://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css" rel="stylesheet" media="all">
        <script src='<c:url value="/node_modules/zone.js/dist/zone.js"/>'></script>
        <script src='<c:url value="/node_modules/reflect-metadata/Reflect.js"/>'></script>
        <script src='<c:url value="/node_modules/systemjs/dist/system.js"/>'></script>
        <script>
            System.config({
               defaultJSExtensions: true,
                map: {
                    '@angular/core': 'node_modules/@angular/core/bundles/core.umd.js',
                    '@angular/common': 'node_modules/@angular/common/bundles/common.umd.js',
                    '@angular/compiler': 'node_modules/@angular/compiler/bundles/compiler.umd.js',
                    '@angular/forms': 'node_modules/@angular/forms/bundles/forms.umd.js',
                    '@angular/http': 'node_modules/@angular/http/bundles/http.umd.js',
                    '@angular/platform-browser': 'node_modules/@angular/platform-browser/bundles/platform-browser.umd.js',
                    '@angular/platform-browser-dynamic': 'node_modules/@angular/platform-browser-dynamic/bundles/platform-browser-dynamic.umd.js',
                    '@angular/router': 'node_modules/@angular/router/bundles/router.umd.js',
                    'rxjs': 'node_modules/rxjs',
                    'moment': 'node_modules/moment/moment.js',
                    'angular2-cookie': 'node_modules/angular2-cookie',
                    'ng2-file-upload': 'node_modules/ng2-file-upload/ng2-file-upload.js',
                }
            });
            System.import('<c:url value="/main"/>'); 
        </script>
    </head>
    <body>
	    <div class="container">
			<cm-app>
	            Car Manager Application
	        <cm-app>
		</div>
		<footer class="footer">
			<div class="container">
				<p class="text-muted">&copy; 2016 Car Manager</p>
			</div>
		</footer>
        <script src='<c:url value="/theme/js/jquery-3.1.1.min.js"/>'></script>
	    <script src='<c:url value="/theme/js/bootstrap.min.js"/>'></script>
    </body>
</html>