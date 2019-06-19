/* Day of week : data picker*/
//add css also (now in style_body.css)
function populateDowDataPicker(option){
			var elem = option.elem;
			if( elem.parent(':first').is('.ssclev'))
				return;
			
			elem.attr('autocomplete', 'off');
			elem.addClass('day_of_week');
			
			var elem_parent = $(elem.parent(':first'));
			var form_appnd = $('<div class="ssclev" style="position: relative;"></div>').appendTo( elem_parent );
				elem.appendTo( $('.ssclev', elem_parent));
				
			//data picker day of week
			var dow_list = $(
					' <div  class="dropmenu_datapick" style=" width: 205px; height: 145px;">'
					+' <div>'
						+' <table class="days_of_week_header">'
							+' <thead>'
								+' <tr><th colspan="4" >Select Days of Week:</th></tr>'
							+' </thead>'
							+' <tbody>'
								+' <tr><td><input type="checkbox" name="day0" value="" /></td><td>Sun</td> <td><input type="checkbox" name="day1" value="" /></td><td>Mon</td> <td><input type="checkbox" name="day2" value="" /></td><td>Tue</td> </tr>'
								+' <tr><td><input type="checkbox" name="day3" value="" /></td><td>Wed</td> <td><input type="checkbox" name="day4" value="" /></td><td>Thr</td> <td><input type="checkbox" name="day5" value="" /></td><td>Fri</td> </tr>'
								+' <tr><td><input type="checkbox" name="day6" value="" /></td><td>Sat</td><td></td><td></td><td></td><td></td></tr>'
								+' <tr><td align="left" colspan="2" ><a href="#" class="button dow_data_picker" ><span class="icon icon3 icon44"></span></a></td><td></td><td></td> <td align="right"  colspan="2"><a href="#" class="button dow_data_picker_close" ><span class="icon icon3 icon56"></span></a></td></tr>'
							+' </tbody>'
						+' </table>'
					+' </div>'
				+' </div>'
			);
		
			//append to day of week input
			dow_list.appendTo($('.ssclev', elem_parent ));
			
			var _len = $(".dropmenu_datapick",$(elem_parent.parents('form:first'))).length; 
			 
			var new_id = 'dropmenu_datapick_id'+_len;
			$(".dropmenu_datapick",elem_parent).attr('id', new_id);
			
			var pos_top = 'auto', pos_right = 'auto';
			if(option.top !== undefined)
				pos_top = option.top;
			if(option.right !== undefined)
				pos_right = option.right;
			
			$(".day_of_week", elem_parent).dropmenu({
				top: pos_top,
				right: pos_right,
				menuDivId: new_id
			});
			
			/*  -------------------------------- get & set days of week information ------------------------------  */
			
			//get days of week from data picker
			dow_list.find(".dow_data_picker").on('click', function() {
				var dow_val=null;
				var context=$( $(this).parents(".ssclev:first"));
				var days_of_week = 0;
				for(var i=0; i<=6; i++){
					if ($(" input[name=day"+i+"]",context).is(':checked'))
						days_of_week = SET_BIT( days_of_week, i );
				}
				//get char form
					dow_val = getWDBitToString(days_of_week);
				$("input[name=days_of_week]", context).val(dow_val);
				$(".dropmenu_datapick").hide();
			});
			
			//close button
			dow_list.find(".dow_data_picker_close").on('click', function() {
				var context =  $(this).parents('.ssclev:first');
				$(".dropmenu_datapick", context).hide();
			});
			
			//set days of week in data picker
			elem.on('click focus',function(){
				
				var days_of_week_val = $(this).val().toLowerCase().replace(/\s/g,"").replace(/-/g,"_");
				
				if(days_of_week_val.length == 0 || days_of_week_val.length !== 7){ this.focus(); return;}
				
				var days_of_week_arr = days_of_week_val.split("");
				var context = $(this).next();
				for(var i=0; i<=6; i++){
					$(" input[name=day"+i+"]",context).prop('checked',(days_of_week_arr[i]!=="_"));
				}
			
			});
			
	
}
