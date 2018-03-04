/**
 * JobController
 */
app.controller('JobController',function($scope,JobService,$location){
	$scope.saveJob=function(){
		
		JobService.saveJob($scope.job).then(function(response){
			alert("Job details posted successfully") 
		} , function(response){
			if(response.status == 401){
				alert("error while posting")
				if(response.data.code == 6){
					alert('Access Denied')
					$location.path('/home')
			}
			}
				else{
					$scope.error=response.data
					$location.path('/login')
			
				}

			if(response.status == 500){
				$scope.error=response.data
				$location.path('/addjob')
			}
			
		})
	}
	$scope.getJob=function(){
		
		JobService.getJobs().then(function(response){
			alert("getlistofjobs")
			$scope.jobs=response.data
		})
	}
})
	