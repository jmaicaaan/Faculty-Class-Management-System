(function(){
	angular.module("facultyApp")
		.factory("developerService", developerService);

	function developerService($http){
		var service = {
			uploadProfessors: uploadProfessors,
			loadProfessors: loadProfessors,
			addAccountType: addAccountType,
			uploadSubjects: uploadSubjects,
			loadSubjects: loadSubjects
		};

		return service;

		function uploadProfessors(){
			return $http.post("resources/extras/professors.json")
				.then(function(response){
					return saveProfessors(response.data);
				});
		}

		function saveProfessors(data){

			JSON.stringify(data);
			var request = {
				method: "post",
				url: "saveProfessors.action",
				data: data,
				headers: {
					"Content-Type": "application/json",
					"dataType": "json"
				}
			};
			
			return $http(request)
				.then(function(response){
					console.log(response);
					return response;
				});
		}

		function loadProfessors(){
			var request = {
				method: "post",
				url: "viewProfessors.action",
				headers: {
					"Content-Type": "application/json",
					"dataType": "json"
				}
			};

			return $http(request)
				.then(function(response){
					console.log(response);
					return response;
				});
		}

		function addAccountType(usersObjList){
			
			var usersObj = {
				"users": []
			};
			
			for(var ids in usersObjList){
				//Get all the ids of the selectedUsers
				var obj = {
					"userID": ids,
					"accountType": [{
						"accountType": usersObjList[ids]
					}]
				};
				usersObj.users.push(obj);
			}
			var request = {
					method: "post",
					url: "addAccountType.action",
					data: usersObj,
					headers: {
						"Content-Type": "application/json",
						"dataType": "json"
					}
			};		
			return $http(request)
				.then(function(response){
					console.log(response);
					return response;
				});
		}

		function getSubjects(){
			var request = {
				url: "https://raw.githubusercontent.com/jmaicaaan/FCMS/gh-pages/ISSubjects.json",
				method: "post"
			};
			return $http(request)
				.then(function(response){
					return response.data;
				})
				.catch(function(error){
					return error;
				});
		}

		function uploadSubjects(){


			return getSubjects().then(function(response){
				var responseObj = response;
				
				var request = {
					url: "addSubjects.action",
					method: "post",
					data: responseObj
				};
				
				return $http(request)
					.then(function(response){
						console.log(response);
						return response.data;
					})
					.catch(function(error){
						return error;
					});
			});

		}

		function loadSubjects(){
			var request = {
				url: "loadSubjects.action",
				method: "post"
			};

			return $http(request)
				.then(function(response){
					console.log(response);
					return response.data;
				})
				.catch(function(error){
					return error;
				});
		}
	}
}());