(function(){
	angular.module("profileModule")
		.service("resumeService", resumeService);

	function resumeService($http){
		var self = this;
		self.listFile = {};
		self.addFile = addFile;
		self.uploadResume = uploadResume;
		self.get_Resume = get_Resume;
		self.delete_Resume = delete_Resume;
		
		function addFile(resumeObj){
			var len = Object.keys(resumeObj).length;
			self.listFile = resumeObj;
		}

		function uploadResume(resumeFile){

			var resumeObj = new FormData();
			resumeObj.append("file", resumeFile);

			var request = {
				url: "uploadResume.action",
				method: "post",
				data: resumeObj,
				headers:{
					"Content-Type": undefined
				},
				transformRequest: angular.identity
			};

			return $http(request)
				.then(function(response){
					// addFile(response.data);
					get_Resume();
					return response;
				})
				.catch(function(error){
					return error;
				});
		}

		function get_Resume(){
			var request = {
				url: "View_Resume.action",
				method: "post",
				headers: {
					"Content-Type": "application/json"
				}
			};

			return $http(request)
				.then(function(response){
					var list = response.data.rSet;
					// if(list.length > 0){
					// 	self.listFile = list;
					// }
					self.listFile = list;
					console.log(response);
					return response;
				})
				.catch(function(error){
					return error;
				});
		}

		function delete_Resume(resumeFile){
			var resumeObj = {
				"rModel":{
					"RID": resumeFile.RID
				}
			};

			var request = {
				url: "Delete_Resume.action",
				method: "post",
				data: resumeObj,
				headers:{
					"Content-Type": "application/json"
				}
			};

			return $http(request)
				.then(function(response){
					console.log(response);
					get_Resume();
					return response;
				})
				.catch(function(error){

					return error;
				});
		}


		
	}
})();