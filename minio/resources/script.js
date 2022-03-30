(function() {
    var elt
    var items = document.querySelectorAll('div');
    Array.from(items).filter((e) => e.id.includes("dataset")).forEach(element => 
    {
        elt = element;
        var options = { 
            expressionsCollapsed: true,
            settingsMenu : false,
            keypad : false,
            expressions :true,
            showLabel: true
         };

        var calculator = Desmos.GraphingCalculator(elt,options);
        var g = elt.getAttribute('g');
        var k = elt.getAttribute('k');
        
        
        var interestCoeffiient = elt.getAttribute('interestCoeff');

        calculator.setExpression({ id: 'graph1', latex: 'y='+interestCoeffiient+'*{'+g+'*'+k+'}/{x}+1-'+interestCoeffiient+' \\left\\{x>='+k+'\\right\\}', color: Desmos.Colors.BLUE }); //curva
        calculator.setExpression({ id: 'graph2', latex: 'y='+interestCoeffiient+'*('+g+'-1)/{'+k+'} * x + 1*'+interestCoeffiient+'+1-'+interestCoeffiient+' \\left\\{0<=x<='+k+'\\right\\}', color: Desmos.Colors.BLUE}); //retta
        var i = 3;
        Array.from(element.children).filter((c) => c.id.includes("calculator")).forEach(e =>
        {
            
            var reductionName = e.getAttribute('red');
            var v = e.getAttribute('v');
            var d = e.getAttribute('d');

            var qNew= v-interestCoeffiient*(g-1)/k*d;
            var casualColor = "#" + ((1<<24)*Math.random() | 0).toString(16);
            
            calculator.setExpression({ id: 'graph'+i, latex: 'y='+interestCoeffiient+'*('+g+'-1)/{'+k+'} * x +'+ qNew +' \\left\\{'+d+'<=x<='+k+'+'+d+'\\right\\}', color: casualColor }); //retta
            calculator.setExpression({id: 'point'+i , latex: '('+d+','+v+')', label: reductionName, showLabel: true, color: casualColor})
            
            var adjustment = parseFloat(d)+parseFloat(k);
            adjustment= adjustment*interestCoeffiient*(g-1)/k+qNew-(interestCoeffiient*g);

            calculator.setExpression({ id: 'graph'+i+1, latex: 'y='+interestCoeffiient+'*{'+g+'*'+k+'}/{(x-'+d+')}+'+adjustment+' \\left\\{x>='+k+'+'+d+'\\right\\}' , color: casualColor}); // curva
            i=i+2;
        });
        var state = calculator.getState();
        
        state.graph.viewport = {"xmin":-3,"ymin":-0.3,"xmax":170,"ymax": 1.5};
        state.graph.squareAxes = false;
        state.graph.xAxisLabel= "t";
        state.graph.yAxisLabel = "Total Value";
        // Set the state normally.
        calculator.setState(state);
        // Set the default state so that it can be reset at any time.
        calculator.setDefaultState(state);
    });

})();