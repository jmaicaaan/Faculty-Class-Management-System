(function(){
	angular.module("profileModule")
		.service("projectService", projectService);
	
	function projectService($http, $q, $timeout){
	
		var self = this;
		self.listFile = [];
		self.uploadProject = uploadProject;
		self.get_Projects = get_Projects;
		self.delete_Project = delete_Project;
		
		
		function uploadProject(projectFile){
			
			var projectName = projectFile.name;
			
			var d = new Date(projectFile.date),
				projectDate = d.toLocaleDateString();
			
			
			var projectObj = {
				"pModel": {
					projectName : projectName,
					projectDate : projectDate
				}
			};
			
			var request = {
				method: "post",
				url: "uploadProject.action",
				data: projectObj,
				headers: {
					"Content-Type": "application/json",
					"dataType": "json"
				}
					
			};
			
			return $http(request)
				.then(function(response){
					console.log(response);
					addFile(response.data.pModel.projectName, response.data.pModel.projectDate);
					get_Projects();
					return response;
				})
				.catch(function(error){
					return error;
				});
		}
		
		function addFile(projectName, projectDate){
			var obj = {
				projectName: projectName,
				projectDate: projectDate
			};
			self.listFile.push(obj);
		}

		function get_Projects(){
			var request = {
				url: "View_Projects.action",
				method: "post",
				headers:{
					"Content-Type": "application/json"
				}
			};

			return $http(request)
				.then(function(response){
					console.log(response);
					var list = response.data.pSet;
					self.listFile = list;
					return response;
				})
				.catch(function(error){
					return error;
				});
		}

		function delete_Project(projectFile){

			var projectObj = {
				"pModel": {
					"prID": projectFile.prID
				}
			};

			var request = {
				url: "Delete_Project.action",
				method: "post",
				data: projectObj,
				headers:{
					"Content-Type": "application/json"
				}
			};

			return $http(request)
				.then(function(response){

					console.log(response);
					get_Projects();
					return response;
				})
				.catch(function(error){
					return error;
				});
		}

		
	}
}());