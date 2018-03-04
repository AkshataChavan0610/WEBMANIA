/**
 * JobService
 */

app.factory('JobService',function($http){
	var BASE_URL="http://localhost:8081/proj2middleware"
	var jobService={}
	
	jobService.saveJob=function(job){
		console.log('addjobmethod')
		return $http.post(BASE_URL + "/addjob",job);
	}
	
	
	
	jobService.getJobs=function(){
		console.log('getjobmethod')
		return $http.get(BASE_URL + "/alljobs");
	}
	return jobService;
})