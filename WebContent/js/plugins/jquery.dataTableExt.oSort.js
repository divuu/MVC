jQuery.extend( jQuery.fn.dataTableExt.oSort, {
	/*For date format dd . mm[ . YYYY]*/
    "date-eu-pre": function ( date ) {
        var date = date.replace(" ", "");
          
        if (date.indexOf('.') > 0) {
            /*date a, format dd.mn.(yyyy) ; (year is optional)*/
            var eu_date = date.split('.');
        } else {
            /*date a, format dd/mn/(yyyy) ; (year is optional)*/
            var eu_date = date.split('/');
        }
          
        /*year (optional)*/
        if (eu_date[2]) {
            var year = eu_date[2];
        } else {
            var year = 0;
        }
          
        /*month*/
        
        if (eu_date[1]) {
        	var month = eu_date[1];
        } else {
        	var month = 0;
        }
        
        if (month.length == 1) {
            month = 0+month;
        }
          
        /*day*/
        if (eu_date[0]) {
        	var day = eu_date[0];
        } else {
        	var day = 0;
        }
        if (day.length == 1) {
            day = 0+day;
        }
          
        return (year + month + day) * 1;
    },
 
    "date-eu-asc": function ( a, b ) {
        return ((a < b) ? -1 : ((a > b) ? 1 : 0));
    },
 
    "date-eu-desc": function ( a, b ) {
        return ((a < b) ? 1 : ((a > b) ? -1 : 0));
    },

    /* End */
    
    /*For date format dd/mm/YYY hh:ii am/pm*/
    "date-euro-pre": function ( a ) {
    	var x = 0;
        if ($.trim(a) != '') {
            var frDatea = $.trim(a).split(' ');
            var frTimea = frDatea[1].split(':');
            if(frDatea[2]=='PM' || frDatea[2]=='pm' ){
            	if( !(isNaN(frTimea[0])) && parseInt(frTimea[0],10)!=12 ){
            		frTimea[0] = ''+(parseInt(frTimea[0],10)+12);
            	}
            }else{
            	if( !(isNaN(frTimea[0])) && parseInt(frTimea[0],10)==12 ){
            		frTimea[0] = '00';
            	}
            }
            var frDatea2 = frDatea[0].split('/');
            x = (frDatea2[2] + frDatea2[1] + frDatea2[0] + frTimea[0] + frTimea[1] ) * 1;
            
        } else {
            x = 10000000000000; // = l'an 1000 ...
        }
         
        return x;
    },
 
    "date-euro-asc": function ( a, b ) {
        return a - b;
    },
 
    "date-euro-desc": function ( a, b ) {
        return b - a;
    },
    
 /*For Time format hh:ii AM/PM*/
    "time-euro-pre": function ( a ) {
    	var x = 0;
        if ($.trim(a) != '') 
        {
            var frTimea = $.trim(a).split(' ');
            var frTime = frTimea[0].split(':');
            if(frTimea[1]=='PM' || frTimea[1]=='pm' )
            {
            	if( !(isNaN(frTime[0])) && parseInt(frTime[0],10)!=12 ){
            		frTime[0] = ''+(parseInt(frTime[0],10)+12);
            	}
            }else{
            	if( !(isNaN(frTime[0])) && parseInt(frTime[0],10)==12 ){
            		frTime[0] = '00';
            	}
            }
            
            x = ( frTime[0] + frTime[1] ) * 1;
            //console.log(x);
            
        } else {
            x = 10000000000000; // = l'an 1000 ...
        }
         
        return x;
    },
 
    "time-euro-asc": function ( a, b ) {
        return a - b;
    },
 
    "time-euro-desc": function ( a, b ) {
        return b - a;
    }
    
    /*For  checkbox
     ,  
    "dom-checkbox-pre": function ( a ) {
    	a1 = a.replace(/\"/g,"'");
    	b1 = jQuery(a1);
    	x = b1.is(':checked') == false ? 1 : 0; 
    	return x;
    },
 
    "dom-checkbox-asc": function ( a, b ) {
        return a - b;
    },
 
    "dom-checkbox-desc": function ( a, b ) {
        return b - a;
    }*/
    
} );


//sort checkbox

$.fn.dataTableExt.afnSortData['dom-checkbox'] = function ( oSettings, iColumn )
{
    var aData = [];
    $( 'td:eq('+iColumn+') input', oSettings.oApi._fnGetTrNodes(oSettings) ).each( function () {
        aData.push( this.checked==false ? "1" : "0" );
    } );
    return aData;
};


