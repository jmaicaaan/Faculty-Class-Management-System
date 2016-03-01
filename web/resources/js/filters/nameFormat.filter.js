(function(){
	angular.module("attendanceModule")
		.filter("nameFormat", function(){
			return function(input){
				var l = input.length;
				var res = "";
				for(var i = 0; i <= l - 1; i++){
					if(i == 0){
						res += input.charAt(i).toUpperCase();
						continue;
					}
					res += input.charAt(i).toLowerCase();
				}
				return res;
			}
		});
}());