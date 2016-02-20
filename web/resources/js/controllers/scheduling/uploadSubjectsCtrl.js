(function(){
	angular.module("schedulingModule")
		.controller("uploadSubjectsCtrl", uploadSubjectsCtrl);

	function uploadSubjectsCtrl(uploadSubjectsService, $scope){
		var self = this;
		self.message = "Hello!";
		self.filedata = {};
		self.data = {};

		var sample_ = [];
		var subj_, sched_ = "";

		$scope.$watch(function(){
			return self.filedata;
		}, function(newValue){
			// self.data = newValue.response;
			// self.data = [];
			self.data = newValue.response;
			// self.data.push(newValue.response);
			var hasSched = false;
			for(subjects in self.data){
				// console.log(subjects);
				var keyObj = subjects;
				for(var i = 0; i <= self.data[keyObj].length - 1; i++){
					var inner = self.data[keyObj][i];
					// console.log(inner);
					for(var exp = 0; exp <= inner.expertise.length - 1; exp++){
						var subject = inner.expertise[exp].subjects;

						console.log("***Subject: " + subject.courseCode);
						hasSched = subject.schedule.length > 0 ? true :false;
						if(hasSched){
							for(var sched = 0; sched <= subject.schedule.length - 1; sched++){
								var schedules = subject.schedule[sched];

								sched_ = schedules;
								subj_ = subject;
								console.log("Schedule: " + schedules.time + " " + schedules.section);
							}
						}
						
					}
					if(hasSched){
						
						var obj = {
							"subjects":[
								{"subj": subj_}, 
								{"sched" : sched_},
								{"user" : inner.users.username}
							]
						};

						// for(var i = 0; i <= sample_.length - 1; i++){
						// 	if(sample_[i].indexOf() )
						// }

						sample_.push(obj);
						// console.log("Expertise by user: " + inner.users.username);	
					}
					
					// for(var users = 0; users <= inner.users)
				}
			}

			console.log(sample_);
			// console.log(newValue);
			// console.log(self.data);
		});


	}
}());