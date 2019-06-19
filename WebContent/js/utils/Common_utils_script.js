/*All common utility fuctions*/


/**
 * open url in new tab
 * 
 * **/
function openUrlInNewTab(url) {
	var win = window.open(url, '_blank');
	win.focus();
	return false;
}

/*------------------------------GET WEEK DAY AS ALPHABET ---------------------------------
 * 		EG: 127=SMTWTFS,
 */
function getWDBitToString(WD_bits) {
	if (isNaN(WD_bits)) return false;
	WD_bits = parseInt(WD_bits, 10);
	var WD_String = null;
	WD_String = IS_BIT_SET(WD_bits, 0) ? "S" : "_";
	WD_String += IS_BIT_SET(WD_bits, 1) ? "M" : "_";
	WD_String += IS_BIT_SET(WD_bits, 2) ? "T" : "_";
	WD_String += IS_BIT_SET(WD_bits, 3) ? "W" : "_";
	WD_String += IS_BIT_SET(WD_bits, 4) ? "T" : "_";
	WD_String += IS_BIT_SET(WD_bits, 5) ? "F" : "_";
	WD_String += IS_BIT_SET(WD_bits, 6) ? "S" : "_";

	return WD_String;
}
/*------------------------------GET WEEK DAY AS Bits ---------------------------------*/
//EG: SMTWTFS = 127

function getWDStringToBit(dow) {
	//console.trace(); console.log(dow);
	var days_of_week_val = dow.toLowerCase().replace(/\s/g, "").replace(/-/g, "_");

	if (days_of_week_val.length == 0 || days_of_week_val.length !== 7) {
		//console.log("invalid input"); 
		return 0;
	}

	var days_of_week_arr = days_of_week_val.split(""); //console.log(days_of_week_arr);
	var dow_bit = 0;

	dow_bit = days_of_week_arr[0] == "s" ? SET_BIT(dow_bit, 0) : dow_bit;
	dow_bit = days_of_week_arr[1] == "m" ? SET_BIT(dow_bit, 1) : dow_bit;
	dow_bit = days_of_week_arr[2] == "t" ? SET_BIT(dow_bit, 2) : dow_bit;
	dow_bit = days_of_week_arr[3] == "w" ? SET_BIT(dow_bit, 3) : dow_bit;
	dow_bit = days_of_week_arr[4] == "t" ? SET_BIT(dow_bit, 4) : dow_bit;
	dow_bit = days_of_week_arr[5] == "f" ? SET_BIT(dow_bit, 5) : dow_bit;
	dow_bit = days_of_week_arr[6] == "s" ? SET_BIT(dow_bit, 6) : dow_bit;
	//console.log("day of week"+dow_bit);
	return dow_bit;
}

/*-----------------------------------------------*/

/*Some array utils:*/

/**  
 * @param Array: 1-Dimensional array
 * @returns Array:unique array
 */
function uniqueArray(Arraylist) {
	if (!Arraylist)
		return;
	var result = [];
	$.each(Arraylist, function (i, e) {
		if ($.inArray(e, result) == -1) result.push(e);
	});
	return result;
}

//compare two array for particular value at given index position
/**
 * @param option{object}: {
 * 							index: [],  //array of  index
 * 							arr_1: [], //data array (1D)
 * 							arr_2: [] //data array  (1D)
 * 							}
 * @returns
 */
function indexBasedArrayCompare(option) {
	if (option.index == undefined || option.arr_1 == undefined || option.arr_1 == undefined)
		return null;
	var index_arr = option.index;
	var arr_1 = option.arr_1;
	var arr_2 = option.arr_2;
	var result = true;
	$.each(index_arr, function (index, value) {
		if (arr_1[value - 1] !== arr_2[value - 1])
			result = false;
	});
	return result;
}


/**
 * get 2d array with data  from another 2d array from given Columns Indexes(2D array)
 * @param option{object}:{ index : array of required column index, data_arr: array of data }
 * @returns array : array containing columns given in column index   
 */
function getArrayFromArray(option) { //options: index[], data[], 
	if (option.index == undefined || option.data_arr == undefined)
		return null;
	var column_index_arr = option.index;
	var _2d_arr = option.data_arr;
	var new_arr = [], temp_arr = [];

	$.each(_2d_arr, function (index, value) {
		var temp_new_arr = [];
		temp_arr = value;
		temp_index = index;
		$.each(column_index_arr, function (index, value) {
			//			if( value != undefined )
			//				temp_new_arr.push(temp_arr[index-1]); 		
			temp_new_arr.push(temp_arr[value - 1]);
		});
		new_arr.push(temp_new_arr);
	});
	return new_arr;
}


/*------------------------------------Get data array using id-------------------------------------------------*/
//
/**
 * returns an array with rows having id == data[id_index],  
 * @param id_index:  column index of ID (starts with 1)
 * @param id : id 
 * @param data_arr : array of data (2d)
 * @returns {Array} : rows
 */
function getDataArray(id_index, id, data_arr) { //id_index: real index(starts with 1) 
	var return_arr = [];
	if (undefined == data_arr) {
		console.trace(data_arr);
		return return_arr;
	}

	id_index = id_index - 1; //real index

	$.each(data_arr, function (inx, val) {
		if (val[id_index] != null && '' + val[id_index].trim() == id) {
			return_arr.push(val);
		}
	});

	return return_arr;
}

/*-----------------------------------------------*/
/**
 * Get Min, Max, Avg, Sum of a column from a 2d array.
 * @param column{number}: column number(1->n)
 * @param d_array{2d array}: the array of data 
 * @param type{string}: operation type{'min', 'max', 'avg', 'sum'}
 * @returns {Number}
 */
function getAggregateFromArray(column, d_array, type) { //column : 1->n. d_array->2d array, type->string.
	//add valid

	//get array
	var rq_arr = [];
	$.each(d_array, function (index, value) {
		rq_arr[index] = parseFloat(value[column - 1]);
	});

	type = $.trim(type).toLowerCase();
	var result = 0.0;
	var sum = 0.0;
	switch (type) {
		case 'min':
			rq_arr.sort(function (a, b) { return a - b });
			result = rq_arr[0];
			break;
		case 'max':
			rq_arr.sort(function (a, b) { return a - b });
			result = rq_arr[(rq_arr.length - 1)];
			break;
		case 'avg':
			$.each(rq_arr, function (index, value) {
				sum += value;
			});
			result = sum / rq_arr.length;
			break;
		case 'sum':
			$.each(rq_arr, function (index, value) {
				sum += value;
			});
			result = sum;
			break;
		default:
			log('getAggregateFromArray()', 'invalid option');
	}
	return result;
}


//TODO: update (use binary search)
function compareTwoArray(arr1, arr2, dim) { // dim: 1->1 1D,2 ->2D (dimension),
	//add valid

	var result = true;
	if (arr1.length != arr2.length) {
		return false;
	}

	var maxCnt = arr1.length;
	for (var j = 0; j < maxCnt; j++) {
		if (dim == 1) {
			if (arr1[j] != arr2[j]) {
				result = false;
				break;
			}
		}

		if (dim == 2) {
			var arrTmp = arr1[j];
			var maxCnt2 = arrTmp.length;
			for (var j1 = 0; j1 < maxCnt2; j1++) {
				if (arrTmp[j1] != arr2[j][j1]) {
					result = false;
					break;
				}
			}
		}

	}

	return result;
}


/*----------------Time processsing------------------*/

/**
 * format time to display in form function
 * @param time: time as (100*HH + MM)
 * eg: 100*HH+MM to HH:MM AM/Pm	eg: '1200' to '12:00 PM' 
 * @returns
 */
function getFormatedTime(time) {	//time=hh*100 + mm
	if (isNaN(time)) {
		alert("Time is invalid..!"); return null;
	}
	if (0 == time)
		time = 000;

	var hr = parseInt(time / 100, 10), min = parseInt(time % 100, 10), md = "AM";

	md = (hr > 11 && hr < 24) ? "PM" : md;
	hr = hr == 0 ? (hr + 12) : hr;
	hr = hr > 12 ? (hr - 12) : hr;

	hr = hr < 10 ? ("0" + hr) : hr;
	min = min < 10 ? ("0" + min) : min;

	return "" + hr + ":" + min + " " + md + "";
}


/**
 * reformat time HH:MM AM/Pm to 100*HH+MM
 * @param time: time as- HH:MM AM/PM
 * @returns 100*HH+MM
 */
function reFormatTime(time) {	//time=HH:MM AM/Pm
	//console.log("reFormatting "+time);
	time = time.replace(/\s/g, "");
	if ("" == time) return 0;
	var h = parseInt(time.substring(0, 2), 10),
		m = parseInt(time.substring(3, 5), 10),
		md_s = time.substring(5, 7).toLowerCase();
	md = md_s == "am" ? 0 : 12;
	if (12 == h) h = 0; //12am=00hr
	//console.log("returning "+h+" "+m+" "+md);
	return ((h + md) * 100 + m);
}

//format time(long) to display date. eg: time=975609000000(mili sec), to dd/mm/yyyy.  

function timeToDisplayDate(time) {

	if (0 == time || "" == time || isNaN(time))
		return "";

	var inputDate = new Date(parseInt($.trim(time), 10));
	var day = inputDate.getDate();
	var month = inputDate.getMonth() + 1;
	var year = inputDate.getFullYear();
	day = day <= 9 ? "0" + day : day;
	month = month <= 9 ? "0" + month : month;

	// var d = day +"/"+ month+"/"+year ;
	// var d = day +"-"+ month+"-"+year ;
	var d = year + "-" + month + "-" + day;
	return d;

}

//get only time from date-time(milliseconds).
function timeToDisplayTime(datetime) {
	if (0 == datetime || "" == datetime || isNaN(datetime)) return "";

	var inputDate = new Date(parseInt($.trim(datetime), 10));
	var hr = inputDate.getHours();
	var min = inputDate.getMinutes();

	var time = ((parseInt(hr, 10)) * 100) + (parseInt(min, 10));
	//console.log(time);
	var return_time = getFormatedTime(time);

	return return_time;
}

//get Date time from date time milis
function timeToDisplayDateTime(datetime) {
	return timeToDisplayDate(datetime) + ' ' + timeToDisplayTime(datetime);
}

function timeToDisplayShortDate(datetime) {
	if (!datetime || datetime == 0) {
		return '';
	}

	var date = timeToDisplayDate(datetime);
	var dateArr = date.split('/');
	var retDate = dateArr[0];

	retDate += ' ' + getMonthStr(dateArr[1]);
	//retDate += ' '+dateArr[2].substring(2);
	retDate += ' ' + dateArr[2];

	return retDate;
}


function timeToDisplayShortDateTime(datetime) {
	if (!datetime || datetime == 0) {
		return '';
	}

	var date = timeToDisplayDate(datetime);
	var dateArr = date.split('/');
	var retDate = dateArr[0];

	retDate += '' + getMonthStr(dateArr[1]);
	retDate += '' + dateArr[2].substring(2);

	var time = timeToDisplayTime(datetime);

	return retDate + ' ' + time;
}

function getMonthStr(month) {
	var m = '';
	switch (parseInt(month, 10)) {
		case 1: m = 'Jan'; break;
		case 2: m = 'Feb'; break;
		case 3: m = 'Mar'; break;
		case 4: m = 'Apr'; break;
		case 5: m = 'May'; break;
		case 6: m = 'Jun'; break;
		case 7: m = 'Jul'; break;
		case 8: m = 'Aug'; break;
		case 9: m = 'Sep'; break;
		case 10: m = 'Oct'; break;
		case 11: m = 'Nov'; break;
		case 12: m = 'Dec'; break;
		default: m = '';
	}
	return m;
}

//date to time in milisecond
function DateToSaveTime(dateStr) {
	var date = null;
	var time = 0;
	dateStr = dateStr.replace(/\s/g, "");
	if (dateStr == "" || dateStr == null)
		return 0;

	dateStr = dateStr.replace(/(\d{2})\/(\d{2})\/(\d{4})/, "$2/$1/$3");
	date = new Date(dateStr);

	if ("Invalid Date" == date) {
		console.log("invalid date" + dateStr);
		return 0;
	}
	time = date.getTime();
	return time;
}


//date and time to time in milisecond( 11/25/2000 + 11:30 pm -> ) 
/**
 * @param dateStr : date string eg. 11/25/2000
 * @param timeStr : time string eg. 11:30 pm
 * @returns time milis(number)
 */
function DateTimeToSaveTime(dateStr, timeStr) {
	//console.log(dateStr+" "+timeStr);
	var time = DateToSaveTime(dateStr);
	var timeInmili = 0;
	if (!timeStr) {
		console.log('time is empty ');
	} else {
		timeStr = timeStr.replace(/\s/g, "");
		if ("" != timeStr && time != 0) {
			timeInmili = reFormatTime(timeStr);
			timeInmili = (timeInmili - Math.floor(timeInmili / 100) * 40) * 60 * 1000;
		}
	}

	time += timeInmili;
	return time;
}

/*-----------------------------------------*/




//--------------------Bit operations-----------------------------------//TODO remove, moved to bitutils.js
// Turns the nth bit of the number x ( note that max value of n = 31 i.e. bit no 32
function SET_BIT(x, n) {
	x |= (1 << (n));
	return x;
}
// resets the nth bit of the number x
function RESET_BIT(x, n) {
	x &= (~(1 << (n)));
	return x;
}
// tests whether a bit n is set to 1 or not. if it is set returns true, else false	
function IS_BIT_SET(x, n) {
	return ((((x) >> (n)) & 1) == 1);
}

/*----------------------------------------------*/



/*--------------------------  Populate Function ---------------------------------*/

function populateCombobox(elem, values, id_index, name_index, filterId) {
	$("option:first", elem).nextAll().remove();
	if (values == null || values.length < 1)
		return;
	id_index = id_index - 1;
	name_index = name_index - 1;

	if (filterId == undefined)
		filterId = 0;

	var rows = values.length;	//console.log(rows);
	if (null != values) {
		for (var row = 0; row < rows; row++) {
			var column = 0, name, id;

			if (null != values[row][column]) {
				id = $.trim(values[row][id_index]);

				// incase of lookup. we look at the next column & compare
				if (filterId > 0) {
					var myId = $.trim(values[row][id_index + 1]);
					if (myId != filterId)
						continue;
				}

				name = $.trim(values[row][name_index]);
				elem.append("<option value=\"" + id + "\">" + name
					+ "</option>");
			}
		}
	} else {
		alert("populateCombobox: Local data store empty..!");
	}
	return true;
}
/*----------------------------------------------------------------------------------------*/
function truncateString(str, length = 100, ending = '...') {
    if (str.length > length) {
      return str.substring(0, length - ending.length) + ending;
    } else {
      return str;
    }
  };
/*----------------------------------------------------------------------------------------*/

/**
 * 
 * @param {Number} id Id of the of the object to get the value of
 * @param {Object} object The object itself: instance
 * @param {Number} nameIndex Index of the Name: optional
 */
function getValueById(id, object, nameIndex) {
	var str = "Unknown : " + id;

	if (object == undefined)
		return "Unknown value";

	var storageData = object.getStorageData();


	if (storageData == undefined) {

		//						console.log( "_data is undefined. calling fetchData" );
		object.fetchData({
			callback: function (status, response) {

				//								console.log("Callback returned: status=" + status + " data:" + response.values );
				if (status && response.status_code >= 0 && response.data.values) {

					console.log("Setting storage data.....");
					object.setStorageData(response);
				}
				else {
					console.log(object.REL + ' data is not available...1');
				}
			}
		});
	}

	storageData = object.getStorageData();

	var indexOfId = object.getIdIdx();
	var indexOfName = nameIndex != undefined? nameIndex: object.getNameIdx();


	var values = [];
	if (null !== storageData) {
		values = storageData.data.values;

		//						console.log("Checking values: ", values );

		if (values == undefined) {

			return str;
		}

		$.each(values, function (i, value) {

			if (id == value[indexOfId - 1]) {		// index of the element to be searched for 
				str = value[indexOfName - 1];				// the corresponding value
				//								console.log("1) --->Found element " + str);
				return str;
			}
		});
	}
	else {
		object.fetchData({
			callback: function (status, data) {
				if (status && data.values) {

					values = data.values;

					$.each(values, function (i, value) {

						if (id == value[indexOfId - 1]) {		// index of the element to be searched for 
							str = value[indexOfName - 1];				// the corresponding value
							//											console.log("2) --->Found element " + str);
							return str;
						}
					});
				}
				else {
					console.log(object.REL + ' data is not available...2');
				}
			}
		});
	}

	//					console.log("3) --->Found element " + str);
	return str;
}

/*-------------------------------------------------------------------------------------------*/
function getObjectById(id, object) {
	var str = "Unknown : " + id;
	var foundObject = {
		success: false
	};
	if (object == undefined)
		return "Unknown value";

	var storageData = object.getStorageData();

	//console.log( "getValueById: " + id );

	if (storageData == null) {

		object.fetchData({
			callback: function (status, response) {
				console.log('TCL: getObjectById -> response', response)
				console.log('TCL: getObjectById -> status', status)

				if (response.status_code >= 0 && response.data.values) {

					object.setStorageData(response);
					storageData = object.getStorageData();
					var indexOfId = object.getIdIdx();
					var values = [];
					if (null !== storageData) {
						values = storageData.data.values;
						if (values == undefined) {
							console.log(object.REL + ' data is not available');
							return "Unable to fetch Object";
						}
						foundObject.values = values.find(function (elem) {
							if (elem[indexOfId - 1] == id) {
								foundObject.success = true;
								return elem;
							} else {
								return "Error 404";
							}
						});
						if (foundObject.success == true && (storageData.data.headings != null)) {
							foundObject.headings = storageData.data.headings;
							return foundObject;
						}

					}
				}
				else {
					console.log(object.REL + ' data is not available...1');
				}
			}
		});
	} else {
		storageData = object.getStorageData();
		var indexOfId = object.getIdIdx();
		var values = [];
		if (null !== storageData) {
			values = storageData.data.values;

			if (values == undefined) {
				console.log(object.REL + ' data is not available');
				return "Unable to fetch Object";
			}


			foundObject.values = values.find(function (elem) {
				if (elem[indexOfId - 1] == id) {
					foundObject.success = true;
					return elem;
				} else {
					return;
				}
			});
			if (foundObject.success == true && (storageData.data.headings != null)) {
				foundObject.headings = storageData.data.headings;
				return foundObject;
			}

		}
	}
	return foundObject;
}


/*----------------------------------------------------------------------------------------*/

/* logging */
function log(arg1, arg2) {
	if (console) {
		console.log(PrintTimeStamp() + ' : ' + arg1 + ' - ' + arg2);
	}

}

/* Time stamp*/
function PrintTimeStamp() {
	var currentTime = new Date();
	return (currentTime.getHours() + ":" + currentTime.getMinutes() + ":" + currentTime.getSeconds() + ":" + currentTime.getMilliseconds());
}


/**
 * @param c : single character
 * @returns {Boolean}
 */
function isDigit(c) {
	if (c.length < 1) return false;

	if (c >= '0' && c <= '9') {
		// it is a number
		return true;
	} else {
		//not a number
		return false;
	}
}

/**
 * @param n : a decimal number
 * @returns {Boolean}
 */
function isNumber(n) {
	return !isNaN(parseFloat(n)) && isFinite(n);
}




/**
 * @param number : the decimal number need to trim
 * @param numberOfDig: number of decimal points
 */
function trimRHSDecimalPoint(number, numberOfDig) {

	var numberArr = (number + '').split('.');
	var result = number;
	if (numberArr.length == 2 && numberArr[1].length > numberOfDig) {
		numberArr[1] = numberArr[1].slice(0, numberOfDig);
		result = parseFloat(numberArr.join('.'));
	}
	return result;
}

function getID(typeOfIndividual, state_id, district_id, taluk_id) {
	var individualId = typeOfIndividual.toString() + state_id.toString() + district_id.toString() + taluk_id.toString();

	return individualId;

}

function roundNumber(number, decimals) {
	var newString;// The new rounded number
	decimals = Number(decimals);
	if (decimals < 1) {
		newString = (Math.round(number)).toString();
	} else {
		var numString = number.toString();
		if (numString.lastIndexOf(".") == -1) {// If there is no decimal point
			numString += ".";// give it one at the end
		}
		var cutoff = numString.lastIndexOf(".") + decimals;// The point at which to truncate the number
		var d1 = Number(numString.substring(cutoff, cutoff + 1));// The value of the last decimal place that we'll end up with
		var d2 = Number(numString.substring(cutoff + 1, cutoff + 2));// The next decimal, after the last one we want
		if (d2 >= 5) {// Do we need to round up at all? If not, the string will just be truncated
			if (d1 == 9 && cutoff > 0) {// If the last digit is 9, find a new cutoff point
				while (cutoff > 0 && (d1 == 9 || isNaN(d1))) {
					if (d1 != ".") {
						cutoff -= 1;
						d1 = Number(numString.substring(cutoff, cutoff + 1));
					} else {
						cutoff -= 1;
					}
				}
			}
			d1 += 1;
		}
		if (d1 == 10) {
			numString = numString.substring(0, numString.lastIndexOf("."));
			var roundedNum = Number(numString) + 1;
			newString = roundedNum.toString() + '.';
		} else {
			newString = numString.substring(0, cutoff) + d1.toString();
		}
	}
	if (newString.lastIndexOf(".") == -1) {// Do this again, to the new string
		newString += ".";
	}
	var decs = (newString.substring(newString.lastIndexOf(".") + 1)).length;
	for (var i = 0; i < decimals - decs; i++) newString += "0";
	//var newNumber = Number(newString);// make it a number if you like
	return Number(newString); // Output the result to the form field (change for your purposes)
}
