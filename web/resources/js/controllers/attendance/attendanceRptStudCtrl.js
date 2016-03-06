(function(){
	angular.module("attendanceModule")
		.controller("attendanceRptStudCtrl", attendanceRptStudCtrl);

	function attendanceRptStudCtrl(attendanceRptStudService){
		var self = this;

		view_highCharts();
		self.chartConfig = {
	        options: {
	            chart: {
	                type: 'column'
	            }
	        },
	        series: [],
	        title: {
	            text: "Attendance Reports"
	        },
			loading: false,
		    yAxis: {
			  currentMin: 0,
			  currentMax: 5.5,
			}
		};

		function view_highCharts(){
			attendanceRptStudService.view_HighCharts().then(function(response){
				var ar = response.data.aList;
				var len = ar.length;
				for(var i = 0; i <= len - 1; i++){
					var obj = {
						name: [ar[i].classlist.facultyAssign.schedule.subjects.courseCode, 
								ar[i].classlist.facultyAssign.schedule.section].join(" - "),
						data: [ar[i].noOfAbsences, ar[i].noOfLates, ar[i].noOfLives]
					};
					self.chartConfig.series.push(obj);
				}
			});
		}	
		
	}
})();