function takeValue(questionId){
var rate_value = null;
if (document.getElementById('answer0').checked) {
	  rate_value = 0;
	}
if (document.getElementById('answer1').checked) {
	 rate_value = 1;
	}
if (document.getElementById('answer2').checked) {
	 rate_value = 2;
	}
if (document.getElementById('answer3').checked) {
	 rate_value = 3;
	}
if(rate_value == null){
	alert("Select a response...");
}
var object = {id:questionId,answerId:rate_value};
jQuery.ajax("quiz/question/",
{
    type:"GET",
    data: object,
    
    success: function(response) {
        $( "#divQuestion" ).html(response);
    },
    
}); 
};
