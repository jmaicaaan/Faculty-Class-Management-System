(function(){
	angular.module("attendanceModule")
		.controller("attendanceRptCtrl", attendanceRptCtrl);

	function attendanceRptCtrl($stateParams, $timeout, attendanceRptService){
		var self = this;
		self.class = {
			"schedObj": {
				"section": $stateParams.s,
				"subjects": {
					"courseCode": $stateParams.c
				},
				"day": $stateParams.d
			}
		};
		self.view_highCharts = view_highCharts;
		self.downloadChart = downloadChart;
		self.chartConfig = {
	        options: {
	            chart: {
	                type: 'column'
	            }
	        },
	        series: [],
	        title: {
	            text: [self.class.subject, self.class.section].join(" ")
	        },
			loading: false,
		    yAxis: {
			  currentMin: 0,
			  currentMax: 5.5,
			}
		};
		view_highCharts();

		function downloadChart(){
			var chart = this.chartConfig.getHighcharts();
			chart.print();
		}

		function view_highCharts(){
			var schedObj = self.class;
			attendanceRptService.view_highCharts(schedObj).then(function(response){
				console.log(response);
				pushAttendance(response.data.aList);
			});
		}

		function pushAttendance(attendanceList){
			var len = attendanceList.length;
			for(var i = 0; i <= len -1; i++){
				var obj = {
					name: [attendanceList[i].classlist.users.firstName, attendanceList[i].classlist.users.lastName].join(" "),
					data: [attendanceList[i].noOfLives]
				};
				self.chartConfig.series.push(obj);
			}
		}
	}
})();