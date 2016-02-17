(function(){
	angular.module("profileModule")
		.service("researchService", researchService);
	
	function researchService($http){

		var self = this;
		self.listFile = [];
		self.uploadResearch = uploadResearch;
		self.get_Researches = get_Researches;
		self.delete_Research = delete_Research;

		function addFile(researchName, researchDate){
			var obj = {
				researchName: researchName,
				researchDate: researchDate
			};
			self.listFile.push(obj);
		}
		
		function uploadResearch(researchFile){
			
			var researchName = researchFile.name;

			var d = new Date(researchFile.date),
				researchDate = d.toLocaleDateString();
			
			var researchObj = {
				"rModel": {
					researchName: researchName,
					researchDate: researchDate
				}
			};

			var request = {
				method: "post",
				url: "uploadResearch.action",
				data: researchObj,
				headers: {
					"Content-Type" : "application/json",
					"dataType" : "json"
				}
			};
			
			return $http(request)
				.then(function(response){
					console.log(response);
					
					// addFile(response.data.rModel.researchName, 
					// 		response.data.rModel.researchDate);
					get_Researches();
					
					return response;
				})
				.catch(function(error){
					console.log(error);
					return error;
				});
		}
		
		function get_Researches(researchFile){
			var request = {
				url: "View_Researches.action",
				method: "post",
				data: researchFile,
				headers: {
					"Content-Type": "application/json"
				}
			};

			return $http(request)
				.then(function(response){
					console.log(response);
					var list = response.data.rSet;
					// if(list.length > 0){
					self.listFile = list;
					// }else{
					// 	self.listFile = [];
					// }
					return response;
				})
				.catch(function(error){
					return error;
				});
		}

		function delete_Research(researchFile){

			var researchObj = {
				"rModel":{
					"rID": researchFile.rID
				}
			};

			var request = {
				url: "Delete_Research.action",
				method: "post",
				data: researchObj,
				headers:{
					"Content-Type": "application/json"
				}
			};

			return $http(request)
				.then(function(response){

					console.log(response);
					get_Researches();
					return response;
				})
				.catch(function(error){
					return error;
				});
		}
	}
}());