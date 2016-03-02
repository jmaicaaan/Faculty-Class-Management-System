(function(){
	angular.module("events", [])
		.constant("USER_ROLES",{
			student: "student",
			professor: "professor",
			acadAdviser: "academic adviser",
			chairperson: "chairperson",
			secretary: "secretary",
			developer: "developer"
		});
}());