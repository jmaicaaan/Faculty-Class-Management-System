(function(){
	angular.module("secretaryApp")
		.service("downloadProfilesService", downloadProfilesService);

	function downloadProfilesService($http){
		var self = this;
		self.view_Professors = view_Professors;

		function view_Professors(){
			var request = {
				url: "view_Professors.action",
				method: "post",
				headers: {
					"Content-Type": "application/json",
					"dataType": "json"
				}
			};

			return $http(request)
				.then(function(response){

					console.log(response);
					return response;
				})
				.catch(function(error){
					return error;
				});
		}
	}
}());