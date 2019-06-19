var BitUtils = {
		// Turns the nth bit of the number x ( note that max value of n = 31 i.e. bit no 32
		SET_BIT : function( x, n ) 
		{		
			x |= (1<<(n));
			return x;
		},
		// resets the nth bit of the number x
		RESET_BIT : function (  x, n ) 
		{		
			x &= (~(1<<(n)));
			return x;
		},
		// tests whether a bit n is set to 1 or not. if it is set returns true, else false	
		IS_BIT_SET : function (  x, n ) 
		{		
			return ((((x)>>(n)) & 1) == 1);
		}	
}; 