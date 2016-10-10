<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'measurement.label', default: 'Measurement')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
        <script src="https://cdn.plot.ly/plotly-1.2.0.min.js"></script>
    </head>
    <body>
        <a href="#list-measurement" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-measurement" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <f:table collection="${measurementList}" />

            <div class="pagination">
                <g:paginate total="${measurementCount ?: 0}" />
            </div>
        </div>

        <div id="tester" style="width:1000px;height:600px;"></div>
        <script>
            <g:set var="measurementChartData" value="${de.alferink.beelogger.Measurement.list()}" />
            var data = [
                {
                    x: [
                        <g:each in="${measurementChartData}" >
                        '${it.date}',
                        </g:each>
                    ],
                    y: [
                        <g:each in="${measurementChartData}" >
                        ${it.value},
                        </g:each>
                    ],
                    name: 'Temperatur',
                    type: 'scatter'
                },
                {
                    x: [
                        <g:each in="${measurementChartData}" >
                        '${it.date}',
                        </g:each>
                    ],
                    y: [
                        <g:each in="${measurementChartData}" >
                        ${19},
                        </g:each>
                    ],
                    yaxis: 'y2',
                    name: 'Luftfeuchte',
                    type: 'scatter'
                }
            ];

            var layout = {
                title: 'Logger',
                yaxis: {title: '°C'},
                yaxis2: {
                    title: '%',
                    titlefont: {color: 'rgb(148, 103, 189)'},
                    tickfont: {color: 'rgb(148, 103, 189)'},
                    overlaying: 'y',
                    side: 'right'
                }
            };

            Plotly.newPlot('tester', data, layout);

            //        TESTER = document.getElementById('tester');
            //        Plotly.plot( TESTER, [{
            //            x: [1, 2, 3, 4, 5],
            //            y: [1, 2, 4, 8, 16] }], {
            //            margin: { t: 0 } } );
        </script>
    </body>
</html>