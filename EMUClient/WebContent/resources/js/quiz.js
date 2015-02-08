function takeValue(questionId , answerId){
var rate_value = answerId;
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
