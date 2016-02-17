(function(){
	angular.module("facultyApp")
		.service("genSetService", genSetService);

	function genSetService($http){
		var self = this;
		
		self.updateUserProfile = updateUserProfile;
		self.updateUserPassword = updateUserPassword;
		self.get_Expertise = get_Expertise;
		self.delete_subject = delete_subject;
		self.generatePDF = generatePDF;
		self.expertiseList = [];
	

		function updateUserProfile(userObj, subjectObj){

			var object = {
				"uModel": {
					"userID": userObj.userID,
					"idNo": userObj.idNo,
					"lastName": userObj.lastName,
					"firstName": userObj.firstName
				},
				"subjects": []
			};
			subjectObj.forEach(function(elem, index){
				var subj = {
					"courseCode": elem.courseCode,
					"description": elem.description,
					"subjID": elem.subjID,
					"units": elem.units
				}
				object.subjects.push(subj);
			});

			var request = {
				url: "updateUserProfile.action",
				method: "post",
				data: object,
				headers: {
					"Content-Type": "application/json"
				}
			};

			return $http(request)
				.then(function(response){
					console.log(response);
					
					get_Expertise();

					return response.data;
				})
				.catch(function(error){
					return error;
				});
		}

		function updateUserPassword(userObj){

			var request = {
				url: "updateUserPassword.action",
				method: "post",
				data: userObj,
				headers: {
					"Content-Type": "application/json",
					"dataType": "json"
				}
			};

			console.log(userObj);

			return $http(request)
				.then(function(response){
					console.log(response);
					return response.data;
				})
				.catch(function(error){
					return error;
				});
		}

		function get_Expertise(){
			var request = {
				url: "View_Expertise.action",
				method: "post",
				headers: {
					"Content-Type": "application/json"
				}
			};

			return $http(request)
				.then(function(response){
					console.log(response);
					var list = response.data.eSet;
					self.expertiseList = [];
					list.forEach(function(elem, index){
						self.expertiseList.push(list[index].subjects);
					});

					return response;
				})
				.catch(function(error){
					return error;
				});
		}

		function delete_subject(subjectChip){

			var subjectObj = {
				"subject": subjectChip
			};

			var request = {
				url: "Delete_Subject.action",
				method: "post",
				data: subjectObj,
				headers: {
					"Content-Type": "application/json"
				}
			};

			return $http(request)
				.then(function(response){
					return response;
				})
				.catch(function(error){
					return error;
				});
		}

		function generatePDF(){
			var request = {
				url: "generatePDF.action",
				method: "post",
				headers: {
					"Content-Type": "application/octet-stream"
				}
			};

			return $http(request)
				.then(function(response){
					console.log(response);
					return response;
				});
		}
	}
}());