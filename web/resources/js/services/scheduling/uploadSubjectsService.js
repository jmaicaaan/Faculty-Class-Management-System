(function(){
	angular.module("schedulingModule")
		.service("uploadSubjectsService", uploadSubjectsService);

	function uploadSubjectsService($http, $q){
		var self = this;
		self.saveChanges = saveChanges;
		self.parseList = parseList;
		self.data = [];

		function saveChanges(selectedList, dataList){

			var fModelObj = {
				"fModel": []
			};
			//Prepare the data to be submit..
			for(hashKey in selectedList){
				var obj = {
					"professorProfile": {
						"users": {
							"username": selectedList[hashKey]	
						}
					},
					"schedule": {}
				};
				for(list in dataList){
					if(dataList[list].$$hashKey == hashKey){
						obj.schedule.cID = dataList[list].sched.cID;
					}
				}
				fModelObj.fModel.push(obj);
			}

			var request = {
				url: "assign_faculty.action",
				method: "post",
				data: fModelObj,
				headers:{
					"Content-Type": "application/json",
					"dataType": "json"
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


		function parseList(newValue){
			var deferred = $q.defer();

			for(obj in newValue.response){
				var subjects = newValue.response[obj].subjects;
				var users = newValue.response[obj].professorProfile.users;

				for(sched in subjects.schedule){

					var dataObj = {
						"subject" : subjects,
						"sched" : subjects.schedule[sched],
						"users": []
					};

					if(self.data.length <= 0){
						dataObj.users.push(users);
						self.data.push(dataObj);
					}else{
						var hasDuplicateCC = false;
						for(dObj in self.data){
							if(self.data[dObj].subject.courseCode == subjects.courseCode 
									|| self.data[dObj].subject.courseCode === undefined){
								hasDuplicateCC = true;
								break;
							}
						}

						if(hasDuplicateCC){
							var hasDuplicateClass = false;
							for(dObj in self.data){
								if (self.data[dObj].sched.section == subjects.schedule[sched].section 
										&& self.data[dObj].subject.courseCode == subjects.courseCode) {
									hasDuplicateClass = true;

									var hasDuplicateUser = false;
									for(uObj in self.data[dObj].users){
										if (self.data[dObj].users[uObj].username == users.username){
											hasDuplicateUser = true;
											break;
										}
									}
									if( !hasDuplicateUser ){
										self.data[dObj].users.push(users);
									}
								}
							}
							if( !hasDuplicateClass ){
								dataObj.users.push(users);
								self.data.push(dataObj);
							}
						}else{
							dataObj.users.push(users);
							self.data.push(dataObj);
						}
					}
				}
			}
			deferred.resolve(self.data);
			return deferred.promise;
		}
	}
}());