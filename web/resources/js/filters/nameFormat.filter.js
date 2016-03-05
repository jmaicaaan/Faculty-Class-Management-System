(function(){
	angular.module("attendanceModule")
		.filter("nameFormat", function(){
			return function(input){
				var l = input.length;
				var res = "";

				if(input.indexOf(",") <= -1){
					res = nameFormatHelper(input);
				}else{
					var name = input.split(","),
						lastName = name[0],
						firstName = name[1].split(" ");
					var joinedName = [];
					joinedName.push(nameFormatHelper(lastName) + ",");
					for(var i = 0; i <= firstName.length - 1; i++){
						joinedName.push(nameFormatHelper(firstName[i]));
					}
					res = joinedName.join(" ");
				}

				
				return res;
			}

			function nameFormatHelper(input){
				var inputLength = input.length;
				var result = "";

				for(var i = 0; i <= inputLength - 1; i++){
					if(i == 0){
						result += input.charAt(i).toUpperCase();
						continue;
					}
					result += input.charAt(i).toLowerCase();
				}
				return result;
			}
		});
}());