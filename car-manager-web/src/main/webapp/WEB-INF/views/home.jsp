<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
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
                    '@angular/platform-browser': 'node_modules/@angular/platform-browser/bundles/platform-browser.umd.js',
                    '@angular/platform-browser-dynamic': 'node_modules/@angular/platform-browser-dynamic/bundles/platform-browser-dynamic.umd.js',
                    'rxjs': 'node_modules/rxjs'
                }
            });
            System.import('<c:url value="/main"/>'); 
        </script>
    </head>
    <body>
        <cm-app>
            Car Manager Application
        <cm-app>
    </body>
</html>