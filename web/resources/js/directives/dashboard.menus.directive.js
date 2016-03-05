(function(){
	
	angular.module("facultyApp")
		.directive("dashboardMenus", dashboardMenus);

	function dashboardMenus(userService, $q, $state){
		return {
			restrict: "E",
			scope: false,
			link: function(scope, elem, attrs){

				//Not the same as $scope
				if($state.$current.self.url == "/dashboard"){
					scope.templateUrlLink = templateUrlLink;
					$q.when(userService.userInfo.userRole).then(function(response){
						scope.userType = response;
					});
				}

				function templateUrlLink(){
					
					const TEMP_LOC = "resources/templates/";
					
					var userAccountType = userService.getAccountType();
					switch(userAccountType.toLowerCase()){
						case "professor":
							return TEMP_LOC + "professor/professorMenu.html";
						case "academic adviser":
							return TEMP_LOC + "acadAdviser/acadAdviserMenu.html";
						case "chairperson":
							return TEMP_LOC + "chairProfessor/chairProfessorMenu.html";
						case "student":
							return TEMP_LOC + "student/studentMenu.html";
						case "secretary": 
							return TEMP_LOC + "secretary/secretaryMenu.html";
						case "developer": 
							return TEMP_LOC + "developer/developer.html";
						default:
							return TEMP_LOC + "errorPage/error404.html";
					}	
				}
			},
			template: function(elem, attrs){
				return "<div ng-include='templateUrlLink()'></div>";
			}
		};
	}	

}());