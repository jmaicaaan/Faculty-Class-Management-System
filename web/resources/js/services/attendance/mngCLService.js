(function(){
	angular.module("attendanceModule")
		.service("mngCLService", mngCLService);

	function mngCLService($http){
		var self = this;
		self.viewClassList = viewClassList;
		self.addStudent = addStudent;
		self.deleteStudent = deleteStudent;


		function viewClassList(schedObj){
			var request = {
				url: "view_Classlist.action",
				method: "post",
				data: schedObj,
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

		function addStudent(studentObj, classlistObj){
			console.log(classlistObj);
			console.log(studentObj);

			var schedObj = {
				"section": classlistObj.section,
				"subjects": {
					"courseCode": classlistObj.subject
				}
			};

			var userObj = studentObj.users;

			var request = {
				url: "add_Student.action",
				method: "post",
				data:{
					"schedObj": schedObj, 
					"users": userObj
				},
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
					console.log(error);
					return error;
				});
		}

		function deleteStudent(studentObj){

			var obj = {
				"clObj": studentObj
			};

			var request = {
				url: "delete_Student.action",
				method: "post",
				data: obj,
				headers:{
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
						console.log(error);
						return error;
					});
		}

	}
})();