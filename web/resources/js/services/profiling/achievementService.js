(function(){
	angular.module("profileModule")
		.service("achievementService", achievementService);

	function achievementService($http, $q){
		var self = this;

		self.listFile = [];
		self.uploadFile = uploadFile;
		self.addFile = addFile;
		self.get_Achievements_Certifications = get_Achievements_Certifications;
		self.delete_Achievements_Certifications = delete_Achievements_Certifications;

		function uploadFile(achievementFile){

			var achievementFormDataObj = new FormData();
			achievementFormDataObj.append("file", achievementFile.file);
			achievementFormDataObj.append("achievement_Certificate_Name", achievementFile.achievementName);
			
			var request = {
					method: "post",
					url: "upload_Achievement_Certificate.action",
					data: achievementFormDataObj,
					headers: {
						"Content-Type": undefined
					},
					transformRequest: angular.identity
			};
			
			
			return $http(request)
				.then(function(response){
					console.log(response);
				
					// addFile(response.data.achievement_Certificate_Name, 
					// 	response.data.achievement_Certificate_Url);
					get_Achievements_Certifications();
				
					return response;
				})
				.catch(function(error){
					console.log(error);
					return error;
				});
		}

		function addFile(name, url){

			var obj = {
				achievement_Certificate_Name: name,
				achievement_Certificate_Url: url
			};
			self.listFile.push(obj);
		}

		function get_Achievements_Certifications(){
			var request = {
				url: "View_Achievements_Certifications.action",
				method: "post",
				headers: {
					"Content-Type": "application/json"
				}
			};

			return $http(request)
				.then(function(response){
					console.log(response);
					var list = response.data.aSet;
					// if(list.length > 0){
					// 	self.listFile = list;
					// }
					self.listFile = list;
					return response;
				})
				.catch(function(error){
					return error;
				});
		}

		function delete_Achievements_Certifications(achievementFile){

			var achievementObj = {
				"aModel": {
					"aID" : achievementFile.aID
				}
			};

			var request = {
				url: "Delete_Achievements_Certifications.action",
				method: "post",
				data: achievementObj,
				headers:{
					"Content-Type": "application/json"
				}
			};

			return $http(request)
				.then(function(response){
					get_Achievements_Certifications();
					return response;
				})
				.catch(function(error){
					return error;
				});
		}
	}
}());